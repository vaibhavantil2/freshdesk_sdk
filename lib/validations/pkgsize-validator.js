/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var nsUtil = require('../utils/ns-resolver');
var validationConst = require('./constants').validationContants;
var fileUtil = require('../utils/file-util');

var MAX_PKG_SIZE = 5000000;
var plgDir = process.cwd() + `/dist/${nsUtil.getRootFolder()}${nsUtil.pkgExt}`;

/*
  Validate package size.
*/
module.exports = {
  validate: function() {
    var errMsgs = [];
    var fileStat = fileUtil.statFile(plgDir);
    if (fileStat.size > MAX_PKG_SIZE) {
      errMsgs.push("Package size exceeds the 5MB limit.");
    }
    return errMsgs;
  },

  validationType: [validationConst.POST_PKG_VALIDATION]
};