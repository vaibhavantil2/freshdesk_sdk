/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var _ = require('underscore');
var fileUtil = require('../utils/file-util');
var eh = require('../utils/err');

/*
  Validate SDK project.
*/

// Execute validation for the respective validation type
function executeValidation(validationType) {
  try {
    var validationPath = __dirname + '/../validations/';
    var validationFiles = fileUtil.readDir(validationPath);
    var errorMsges = [];
    for (var i in validationFiles) {
      var validator = require(validationPath + validationFiles[i]);
      if (_.contains(validator.validationType, validationType)) {
        var err = validator.validate();
        if (err) { errorMsges.push(err) };
      }
    }
    return errorMsges;
  }
  catch (err) {
    eh.error(err);
  }
}

module.exports = {
  run: function(validationType) {
    var errMsges = executeValidation(validationType);
    errMsges = _.compact(_.flatten(errMsges));
    if ( errMsges.length > 0 ) {
      console.log("Validation errors:")
      for (var i = 0; i < errMsges.length; i++) {
        console.log("  " + (i + 1) + ". " + errMsges[i]);
      }
      return false;
    }
    else {
      return true;
    }
  }
};
