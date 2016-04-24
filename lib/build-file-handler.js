var eh = require(__dirname + '/err');
var manifest = require(__dirname + '/manifest');
var contentUnifier = require(__dirname + '/unifier');
var fileUtil = require(__dirname + '/file-util');
var cwd = process.cwd();
var buildDirPath = cwd + '/build';
var buildFilePath = cwd + "/build/index.html";
var buildErrorMsg = "Error while generating build file.";
var buildDeleteMsg = "Error while deleting build/ dir.";

exports.genBuildFile = function genBuildFile(callback) {
  try {
    var unifiedData = contentUnifier.unify();
    fileUtil.ensureFile(buildFilePath);
    fileUtil.writeFile(buildFilePath, unifiedData);
    return;
  }
  catch (err){
    eh.error(new Error(buildErrorMsg));
  }
}

exports.delBuildDir = function() {
    return fileUtil.deleteFile(buildDirPath);
}
