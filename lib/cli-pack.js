var fs = require('fs-extra');
var archiver = require('archiver');
var zip = archiver('zip');
var eh = require(__dirname + "/err");
var buildFile = require(__dirname + "/build-file-handler");
var digestFile = require(__dirname + "/digest-file-handler");
var cwd = process.cwd();
var nsUtil = require(__dirname + '/ns-resolver');
var fileUtil = require(__dirname + '/file-util').fileUtil;
var sourceFiles = ['./app/*', './assets/*', './build/*', './iparam/*', './digest.md5', './manifest.yml'];
var pkgErrorMsg = "Error while generating package.";
var validator = require(__dirname + '/cli-validate');
var validationConst = require( __dirname + '/constants').validationContants;

exports.run = function() {
  try {
    var prePkgValidation = validator.run(validationConst.PRE_PKG_VALIDATION);
    if(!prePkgValidation){
      eh.error(new Error("Package failed due to above errors"));
    }
    var pkgName = `${nsUtil.getRootFolder()}${nsUtil.pkgExt}`;
    var dest = cwd + `/dist/${pkgName}`;
    fileUtil.ensureFile(dest);
    var output = fs.createWriteStream(dest);
    buildFile.genBuildFile();
    digestFile.genDigestFile();
    zipPackage(output);
    var postPkgValidation = validator.run(validationConst.PRE_PKG_VALIDATION);
    if(!postPkgValidation){
      cleanFiles();
      eh.error(new Error("Package failed due to above errors"));
    }
    if(global.verbose)
      console.log(`Packaged to dist/${pkgName}`);
  }
  catch (err){
    eh.error(new Error(pkgErrorMsg));
  }
}

function zipPackage(output){
  output.on('close', function () {
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

function cleanFiles() {
  buildFile.delBuildDir();
  digestFile.delDigestFile();
}
