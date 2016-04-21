var fs = require('fs-extra');
var rmdir = require('rimraf');

var cwd = process.cwd();
var buildDirPath = cwd + '/build';
var buildFilePath = cwd + "/build/index.html";

exports.genBuildFile = function genBuildFile(callback) {
  require(__dirname + '/unifier').unify(function(data) {
    fs.ensureFile(buildFilePath, function(err) {
      if(err) throw "Error while generating build file";
      fs.writeFile(buildFilePath, data, function() {
        callback();
      });
    });
  });
}

exports.delBuildDir = function() {
  rmdir(buildDirPath, function(err) {
    if (err) throw err;
  });
}
