var fs = require('fs-extra');
var rmdir = require('rimraf');
var crypto = require('crypto');
var eh = require(__dirname + '/err');

var cwd = process.cwd();
var digestFile = cwd + "/digest.md5";
var htmlFile = cwd + "/app/template.html";
var scssFile = cwd + "/app/style.scss";
var jsFile = cwd + "/app/app.js";
var buildFile = cwd + "/build/index.html";

exports.genDigestFile = function(manifest, callback) {
  fs.readFile(htmlFile, manifest.mf['source-charset'], function(err, htmlData) {
    if (err) {
      eh.error(new Error("Error while reading from " + htmlFile), 1);
    }
    fs.readFile(scssFile, manifest.mf['source-charset'], function(err, scssData) {
      if(err) {
        eh.error(new Error("Error while reading from " + scssFile), 1);
      }
      fs.readFile(jsFile, manifest.mf['source-charset'], function(err, jsData) {
        if (err) {
          eh.error(new Error("Error while reading from " + jsFile), 1);
        }
        fs.readFile(buildFile, manifest.mf['source-charset'], function(err, buildFileData) {
          if(err) {
            eh.error(new Error("Error while reading from " + buildFile), 1);
          }
          var digest = crypto.createHash('md5').update(htmlData + scssData + jsData + buildFileData).digest("hex");
          fs.writeFile(digestFile, digest, function(err) {
            if(err) {
              eh.error(new Error("Error while writing Digest File"), 1);
            }
            callback();
          })
        })
      });
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