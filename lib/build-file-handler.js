var eh = require('./err');
var contentUnifier = require('./unifier');
var fileUtil = require('./file-util');

var cwd = process.cwd();
var buildDirPath = cwd + '/build';
var buildFilePath = cwd + "/build/index.html";
var buildErrorMsg = "Error while generating build file.";

exports.genBuildFile = function genBuildFile() {
  try {
    var unifiedData = contentUnifier.unify();
    fileUtil.ensureFile(buildFilePath);
    fileUtil.writeFile(buildFilePath, unifiedData);
    return;
  }
  catch (err) {
    eh.error(new Error(buildErrorMsg));
  }
}

exports.delBuildDir = function() {
    return fileUtil.deleteFile(buildDirPath);
}
