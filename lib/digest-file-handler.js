var rmdir = require('rimraf');
var crypto = require('crypto');
var eh = require(__dirname + '/err');
var cwd = process.cwd();
var digestFile = cwd + "/digest.md5";
var htmlFile = cwd + "/app/template.html";
var scssFile = cwd + "/app/style.scss";
var jsFile = cwd + "/app/app.js";
var buildFile = cwd + "/build/index.html";
var fileUtil = require(__dirname + '/file-util');
var digestErrorMsg = "Error while generating digest file.";
var digestDeleteMsg = "Error while deleting digest file.";

exports.genDigestFile = function(callback) {
  try {
    var htmlContent = fileUtil.readFile(htmlFile);
    var scssContent = fileUtil.readFile(scssFile);
    var jsContent = fileUtil.readFile(jsFile);
    var buildContent = fileUtil.readFile(buildFile);
    var combinedContent = htmlContent + scssContent + jsContent + buildContent;
    var digest = crypto.createHash('md5').update(combinedContent).digest("hex");
    fileUtil.writeFile(digestFile, digest);
  }
  catch (err){
    eh.error(new Error(digestErrorMsg));
  }
}

exports.delDigestFile = function() {
  fileUtil.deleteFile(digestFile);
}
