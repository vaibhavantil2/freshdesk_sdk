var fs = require('fs-extra');
var rmdir = require('rimraf');
var eh = require(__dirname + '/err');
var manifest = require(__dirname + '/manifest');
var contentUnifier = require(__dirname + '/unifier');

var cwd = process.cwd();
var buildDirPath = cwd + '/build';
var buildFilePath = cwd + "/build/index.html";

exports.genBuildFile = function genBuildFile(callback) {
  contentUnifier.unify(manifest, function(data) {
    fs.ensureFile(buildFilePath, function(err) {
      if(err) {
        eh.error(new Error("Error while generating build file"));
      }
      fs.writeFile(buildFilePath, data, function(err) {
        if(err)
          eh.error(new Error("Error while generating build file"));
        callback();
      });
    });
  });
}

exports.delBuildDir = function() {
  rmdir(buildDirPath, function(err) {
    if (err) {
      eh.error(new Error("Error while deleting build/ dir."));
    }
  });
}
