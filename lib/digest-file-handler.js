var fs = require('fs-extra');
var rmdir = require('rimraf');
var crypto = require('crypto');

var cwd = process.cwd();
var digestFile = cwd + "/digest.md5";
var htmlFile = cwd + "/app/template.html";
var scssFile = cwd + "/app/style.scss";
var jsFile = cwd + "/app/app.js";
var buildFile = cwd + "/build/index.html";

exports.genDigestFile = function(callback) {
  fs.readFile(htmlFile, function(err, htmlData) {
    if (err) throw "Error while reading from " + htmlFile;
    fs.readFile(scssFile, function(err, scssData) {
      if(err) throw "Error while reading from " + scssFile;
      fs.readFile(jsFile, function(err, jsData) {
        if (err) throw "Error while reading from " + jsFile;
        fs.readFile(buildFile, function(err, buildFileData) {
          if(err) throw "Error while reading from " + buildFile;;
          var digest = crypto.createHash('md5').update(htmlData + scssData + jsData + buildFileData).digest("hex");
          fs.writeFile(digestFile, digest, function(err) {
            if(err) throw "Error while writing Digest File";
            callback();
          })
        })
      });
    });
  });
}

exports.delDigestFile = function() {
  rmdir(digestFile, function(err) {
    if(err) throw "Error while deleting digest file.";
  });
}