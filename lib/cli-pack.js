var fs = require('fs-extra');
var archiver = require('archiver');
var zip = archiver('zip');
var eh = require(__dirname + "/err");
var buildFile = require(__dirname + "/build-file-handler");
var digestFile = require(__dirname + "/digest-file-handler");
var cwd = process.cwd();
var nsUtil = require(__dirname + '/ns-resolver');
var fileUtil = require(__dirname + '/file-util');
var sourceFiles = ['./app/*', './assets/*', './build/*', './config/*', './digest.md5', './manifest.yml'];
var pkgErrorMsg = "Error while generating package.";
var validator = require(__dirname + '/cli-validate');
var validationConst = require( __dirname + '/constants').validationContants;
var pkgName = `${nsUtil.getRootFolder()}${nsUtil.pkgExt}`;

exports.run = function() {
  try {
    var prePkgValidation = validator.run(validationConst.PRE_PKG_VALIDATION);
    if(!prePkgValidation){
      eh.error(new Error("Package failed due to above errors"));
    }
    var dest = cwd + `/dist/${pkgName}`;
    fileUtil.ensureFile(dest);
    var output = fs.createWriteStream(dest);
    buildFile.genBuildFile();
    digestFile.genDigestFile();
    zipPackage(output);
  }
  catch (err){
    eh.error(err);
  }
}

function zipPackage(output){
  output.on('close', function (err) {
    if(err) {
      cleanFiles(true);
      eh.error(new Error("Error while creating package."));
    }

    var postPkgValidation = validator.run(validationConst.POST_PKG_VALIDATION);
    if(!postPkgValidation){
      cleanFiles(true);
      eh.error(new Error("Package failed due to above errors"));
    }
    if(global.verbose)
      console.log(`Packaged to dist/${pkgName}`);
    cleanFiles();
  });
  zip.on('error', function(err){
    eh.error(new Error(pkgErrorMsg));
  });
  zip.pipe(output);
  zip.bulk([
    { expand: true,
      cwd: process.cwd(),
      src: sourceFiles}
  ]);
  zip.finalize();
}

function cleanFiles(err) {
  buildFile.delBuildDir();
  digestFile.delDigestFile();
  if(err)
    fileUtil.deleteFile(cwd + "/dist");
}
