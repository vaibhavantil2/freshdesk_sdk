var fs = require('fs-extra');
var archiver = require('archiver');
var zip = archiver('zip');
var eh = require(__dirname + "/err");
var buildFile = require(__dirname + "/build-file-handler");
var digestFile = require(__dirname + "/digest-file-handler");
var cwd = process.cwd();
var nsUtil = require(__dirname + '/ns-resolver');
var pkgName = `${nsUtil.getRootFolder()}${nsUtil.pkgExt}`;
var dest = cwd + `/dist/${pkgName}`;
var output;
var async = require('async');

exports.run = function() {
  async.waterfall([
    createPkgFile,
    buildFileGeneration,
    digestFileGeneration,
    zipPackage
  ],function (err, result) {
    if(err) console.log(err.message);
    return;
  });
}

function createPkgFile(callback){
  fs.ensureFile(dest, function(err) {
    if (err)
      eh.error(new Error("Error while creating " + dest));
    output = fs.createWriteStream(dest);
    callback();
  });
}

function buildFileGeneration(callback){
  buildFile.genBuildFile(function(){
    callback();
  });
}

function digestFileGeneration(callback){
  digestFile.genDigestFile(function(){
    callback();
  });
}

function zipPackage(callback){
  output.on('close', function () {
    cleanFiles();
  });
  zip.on('error', function(err){
    eh.error(new Error("Error while generating package."));
  });
  zip.pipe(output);
  zip.bulk([
    { expand: true,
      cwd: process.cwd(),
      src: ['./app/*', './assets/*', './build/*', './iparam/*', './digest.md5', './manifest.yml']}
  ]);
  zip.finalize();
  callback();
}

function cleanFiles() {
  buildFile.delBuildDir();
  digestFile.delDigestFile();
  if(global.verbose)
    console.log(`Packaged to dist/${pkgName}`);
}
