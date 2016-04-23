var fs = require('fs-extra');
var rmdir = require('rimraf');
var crypto = require('crypto');
var eh = require(__dirname + '/err');
var async = require('async');
var cwd = process.cwd();
var digestFile = cwd + "/digest.md5";
var htmlFile = cwd + "/app/template.html";
var scssFile = cwd + "/app/style.scss";
var jsFile = cwd + "/app/app.js";
var buildFile = cwd + "/build/index.html";
var manifest = require(__dirname + "/manifest");

exports.genDigestFile = function(callback) {
  async.waterfall([
    function (asyncCB) {
      readSDKFile(htmlFile, function(data){
        return asyncCB(null, data);
      })
    },

    function (combinedData, asyncCB) {
      readSDKFile(scssFile, function(data){
        return asyncCB(null, combinedData + data);
      })
    },

    function (combinedData, asyncCB) {
      readSDKFile(jsFile, function(data){
        return asyncCB(null, combinedData + data);
      })
    },

    function (combinedData, asyncCB) {
      readSDKFile(buildFile, function(data){
        return asyncCB(null, combinedData + data);
      })
    }

  ],function (err, result) {
    if(err) eh.error(new Error("Error while writing Digest File"), 1);
    var digest = crypto.createHash('md5').update(result).digest("hex");
    fs.writeFile(digestFile, digest, function(err) {
      if(err) eh.error(new Error("Error while writing Digest File"), 1);
      callback();
    });
  });
}

exports.delDigestFile = function() {
  rmdir(digestFile, function(err) {
    if(err) {
      eh.error(new Error("Error while deleting digest file."), 1);
    }
  });
}

function readSDKFile(filePath, callback){
  fs.readFile(filePath, manifest.charset, function(err, data) {
    if(err){
      eh.error(new Error("Error while reading from " + filePath), 1);
    }
    return callback(data);
  });
}
