/* 

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is 
bundled with this source code.

*/

'use strict';

var validationConst = require('./constants').validationContants;
var manifest = require('../manifest');
var _ = require('underscore');

var VALID_PAGES = ['ticket', 'contact'];

var invalidPages = function() {
  return _.difference(manifest.pages, VALID_PAGES);
}

function validatePage() {
  if (!(manifest.pages === undefined || _.isEmpty(manifest.pages))) {
    var invldPages = invalidPages();
    if (invldPages.length > 0) {
      return `Invalid page(s) mentioned in manifest.yml: ${invldPages}.`;
    }
  }
  else {
    return "pages not specified in manifest.yml.";
  }
}

module.exports = {
  validate: function() {
    var errMsgs = [];
    var err = validatePage();
    if (!(err === undefined)) {
      errMsgs.push(err);
    }
    return errMsgs;
  },

  invalidPages: invalidPages,

  validationType: [validationConst.PRE_PKG_VALIDATION]
};
