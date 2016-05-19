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
  var ignoredFiles = [];
  for( var i in extraFileCheck) {
    if(fileUtil.checkIgnoredFile(extraFileCheck[i])) {
      ignoredFiles.push(extraFileCheck[i]);
    }
  }
  var invalidFiles = _.difference(extraFileCheck, ignoredFiles);
  // To check if any other files are present in app folder other than valid files
  if(invalidFiles.length > 0){
    err_msges.push(`App directory has invalid file(s) - ${invalidFiles}`);
  }

  //To check if mandatory files are present
  if(mandatoryFileCheck.length > 0){
    err_msges.push(`Mandatory file(s) in app directory missing - ${mandatoryFileCheck}`);
  }

  return err_msges;
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];
