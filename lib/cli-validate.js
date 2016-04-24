var _ = require('underscore');
var fileUtil = require(__dirname + '/file-util').fileUtil;
var eh = require(__dirname + '/err');

exports.run = function (validationType) {
  var errMsges = executeValidation(validationType);
  errMsges = _.compact([].concat.apply([], errMsges));
  if( errMsges.length > 0 ){
    console.log("Validation errors:")
    for(var i = 0; i < errMsges.length; i++){
      console.log("  " + (i + 1) + ". " + errMsges[i]);
    }
    return false;
  }
  else{
    return true;
  }
}

function executeValidation(validationType){
  try {
    var validationPath = __dirname + '/validations/';
    var validationFiles = fileUtil.readDir(validationPath);
    var error_msges = [];
    for(var i in validationFiles){
      var validator = require(validationPath + validationFiles[i]);
      if(_.contains(validator.validationType, validationType)) {
        err = validator.validate();
        if(err)
          error_msges.push(err);
      }
    }
    return error_msges;
  }
  catch(err){
    eh.error(new Error(err.message), 1);
  }
}
