var validationConst = require(__dirname + '/constants').validationContants;
var async = require("async");
var fs = require('fs');
var _ = require('underscore');

exports.run = function () {
  executeValidation(validationConst.ALL_VALIDATION,function(err) {
    if( err.length > 0 ){
      async.forEachOf(err, function(msg, index, callback){
        console.log("  " + (index + 1) + ". " + msg);
        callback();
      });
    }
    else{
      console.log("Validation succeded");
    }
  });
}


function executeValidation(validationType, cb){
  var validationPath = __dirname + '/validations/';
  fs.readdir(validationPath, function(err, files) {
    if(err) return cb(["Error in accessing files"]);
      var error_msges = [];
      async.each(files, function(filename, callback){
        var validator = require(validationPath + filename);
        if(validationType == validationConst.ALL_VALIDATION || _.contains(validator.validationType, validationType)) {
          validator.validate(function(err){
            if(err){
              error_msges.push(err.message);
            }
            return callback();
          });
        }
      },function (err) {
        if(err) cb([err.message]);
        cb(error_msges);
      });
  });
}


