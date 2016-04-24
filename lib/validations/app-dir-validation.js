var _ = require('underscore');
var junk = require('junk');
var validationConst = require('../constants').validationContants;
var fileUtil = require('../file-util');

exports.validate = function(callback) {
  var err_msges = [];
  var validFiles = ['app.js', 'style.scss', 'template.html'];
  var appFolderPath = process.cwd() + '/app/';
  var appFiles = fileUtil.readDir(appFolderPath);
  appFiles = appFiles.filter(junk.not);
  var extraFileCheck = _.difference(appFiles, validFiles);
  var mandatoryFileCheck = _.difference(validFiles, appFiles);
  // To check if any other files are present in app folder other than valid files
  if(extraFileCheck.length > 0){
    err_msges.push('App directory has invalid files.');
  }

  //To check if if mandatory files are present
  if(mandatoryFileCheck.length > 0){
    err_msges.push('Mandatory files missing.');
  }

  return err_msges;
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];
