var fs = require('fs');
var _ = require('underscore');
var junk = require('junk');
var validationConst = require('../constants').validationContants;

exports.validate = function(callback) {
  var validFiles = ['app.js', 'style.scss', 'template.html'];
  var appFolderPath = process.cwd() + '/app/';
  fs.readdir(appFolderPath,function(err,files){
    files = files.filter(junk.not);
    var extraFileCheck = _.difference(files, validFiles);
    var mandatoryFileCheck = _.difference(validFiles, files);

    // To check if any other files are present in app folder other than valid files
    if(extraFileCheck.length > 0){
      return callback(new Error('App directory has invalid files.'));
    }

    //To check if if mandatory files are present
    if(mandatoryFileCheck.length > 0){
      return callback(new Error('Mandatory files missing.'));
    }
    return callback(null);
  });
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];

