/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var validationConst = require('./constants').validationContants;
var manifest = require('../manifest');
var _ = require('underscore');

var VALID_PAGES = ['ticket', 'contact'];
var VALID_FEATURES = ['db'];

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

function validateFeatures() {
  var invalidFeatures = _.difference(manifest.features, VALID_FEATURES);
  if (invalidFeatures.length > 0) {
    return `Invalid feature(s) mentioned in manifest.yml: ${invalidFeatures}.`;
  }
}

module.exports = {
  validate: function() {
    var errMsgs = [];
    var err = validatePage();
    var featureErr = validateFeatures();
    if (!(err === undefined)) {
      errMsgs.push(err);
    }
    if (!(featureErr === undefined)) {
      errMsgs.push(featureErr);
    }
    return errMsgs;
  },

  invalidPages: invalidPages,

  validationType: [validationConst.PRE_PKG_VALIDATION]
};
