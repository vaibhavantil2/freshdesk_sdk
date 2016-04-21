var validationConst = require('../constants').validationContants;
var fs = require('fs');
var path = require('path');
var _ = require('underscore');
var junk = require('junk');
var async = require('async');

exports.validate = function(callback){
  var assetsFolderPath = process.cwd() + '/assets/';
  fs.readdir(assetsFolderPath,function(err,files){
    if(err) return callback(new Error("Error in accessing files"));
    files = files.filter(junk.not);

    //Max asset count check
    if(files.length > validationConst.MAX_ASSET_COUNT) {
      return callback(new Error("Max asset count exceeded: " + files.length));
    }

    async.each(files, function(fileName, asyncCB){
      var filePath = assetsFolderPath +  fileName;
      var fileExtType = path.extname(fileName);
      fs.stat(filePath, function(err, stat) {

        // can read check:
        if(err && err.code == 'ENOENT') {
          return asyncCB(new Error("Cannot read: assets/" + fileName));
        }

        // Dir check:
        if(stat.isDirectory()){
          return asyncCB(new Error("Dir not allowed inside assets/': " + fileName));
        }

        //Extension type check
        if(!_.contains(validationConst.ALLOWED_EXTNS, fileExtType)){
          return asyncCB(new Error("Unsupported asset type: assets/" + fileName));
        }

        //Asset size check
        if(stat.size > validationConst.MAX_INDVL_ASSET_SIZE){
          return asyncCB(new Error("Asset exceeds threshold size: assets/" + fileName));
        }
        asyncCB();
      });
    },function(err){
        if( err )
          return callback(err);
        return callback();
      });
  });
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];