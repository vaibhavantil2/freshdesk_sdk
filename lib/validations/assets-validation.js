var validationConst = require('../constants').validationContants;
var path = require('path');
var _ = require('underscore');
var junk = require('junk');
var async = require('async');
var fileUtil = require('../file-util');
var MAX_ASSET_COUNT = 200;
var MAX_INDVL_ASSET_SIZE = 2000000; // size in bytes. 2000000 bytes = 2 MB.
var ALLOWED_EXTNS = [".jpg", ".jpeg", ".png", ".gif", ".css", ".js"];

exports.validate = function(callback){
  var assetsFolderPath = process.cwd() + '/assets/';
  var assetFiles = fileUtil.readDir(assetsFolderPath);
  assetFiles = assetFiles.filter(junk.not);
  var err_msges = []

  //Max asset count check
  if(assetFiles.length > MAX_ASSET_COUNT) {
    err_msges.push("Max asset count exceeded: " + assetFiles.length);
  }

  outer:
  for(var i in assetFiles) {
    var filePath = assetsFolderPath +  assetFiles[i];
    var fileExtType = path.extname(assetFiles[i]);
    var fileStat = fileUtil.statFile(filePath);
    if(fileUtil.checkIgnoredFile(assetFiles[i])) {
        continue outer;
    }

    // Dir check:
    if(fileStat.isDirectory()){
      err_msges.push("Dir not allowed inside assets/': " + assetFiles[i]);
    }

    //Extension type check
    if(!_.contains(ALLOWED_EXTNS, fileExtType.toLowerCase())){
      err_msges.push("Unsupported asset type: assets/" + assetFiles[i]);
    }

    //Asset size check
    if(fileStat.size > MAX_INDVL_ASSET_SIZE){
      err_msges.push("Asset exceeds threshold size: assets/" + assetFiles[i]);
    }
  }
  return err_msges;
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];
