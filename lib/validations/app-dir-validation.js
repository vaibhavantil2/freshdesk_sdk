/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var _ = require('underscore');
var validationConst = require('./constants').validationContants;
var fileUtil = require('../utils/file-util');

module.exports = {
  validate: function() {
    var errMsgs = [];
    var validFiles = ['app.js', 'style.scss', 'template.html'];
    var appFolderPath = process.cwd() + '/app/';
    var appFiles = fileUtil.readDir(appFolderPath);
    appFiles = appFiles.filter(fileUtil.removeJunkFiles);
    var extraFileCheck = _.difference(appFiles, validFiles);
    var mandatoryFileCheck = _.difference(validFiles, appFiles);
    // To check if any other files are present in app folder other than valid files
    if (extraFileCheck.length > 0) {
      errMsgs.push(`App directory has invalid file(s) - ${extraFileCheck}.`);
    }

    //To check if mandatory files are present
    if (mandatoryFileCheck.length > 0) {
      errMsgs.push(
        `Mandatory file(s) in app directory missing - ${mandatoryFileCheck}.`
      );
    }

    return errMsgs;
  },

  validationType: [validationConst.PRE_PKG_VALIDATION]
};
