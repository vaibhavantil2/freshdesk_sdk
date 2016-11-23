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
var VALID_FEATURES = ['db', 'oauth'];

var URL_REGEX = /^https\:\/\/([^\x00-\x7F]|\w)+\.([^\x00-\x7F]|\w)+(\.([^\x00-\x7F]|\w)+)?(\.([^\x00-\x7F]|\w)+)?$/
var URL_IPARAM_REGEX = /^https\:\/\/\<\%\=\s*iparam\.\w+\s*\%\>\.([^\x00-\x7F]|\w)+(\.([^\x00-\x7F]|\w)+)?(\.([^\x00-\x7F]|\w)+)?$/

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
  if (!(manifest.features === undefined || _.isEmpty(manifest.features))) {
    var invalidFeatures = _.difference(manifest.features, VALID_FEATURES);
    if (!(_.isEmpty(invalidFeatures))) {
      return `Invalid feature mentioned in manifest.yml: ${invalidFeatures}.`;
    }
  }
}

function validateDomain(domain) {
  var match = URL_REGEX.exec(domain);
  if (!_.isNull(match)) {
    if (!(!_.isUndefined(match[1]) && !_.isUndefined(match[2]))) {
      return false;
    }
  }
  else {
    var iparamUrlMatch = URL_IPARAM_REGEX.exec(domain);
    if (!_.isNull(iparamUrlMatch)) {
      if (!(!_.isUndefined(iparamUrlMatch[1]) && !_.isUndefined(iparamUrlMatch[2]))) {
        return false;
      }
    }
    else {
      return false;
    }
  }
}

function validateWhitelistedDomains() {
  var invalidDomains = [];
  for (var domain of manifest.whitelistedDomains) {
    if (validateDomain(domain) === false) {
      invalidDomains.push(domain);
    }
  }
  if (!(_.isEmpty(invalidDomains))) {
    return `Invalid domain(s) mentioned in manifest.yml: ${invalidDomains}`;
  }
}

module.exports = {
  validate: function() {
    var errMsgs = [];
    var pageErr = validatePage();
    var featureErr = validateFeatures();
    var whitelistDomainErr = validateWhitelistedDomains();
    if (!(_.isUndefined(pageErr))) {
      errMsgs.push(pageErr);
    }
    if (!(_.isUndefined(featureErr))) {
      errMsgs.push(featureErr);
    }
    if (!(_.isUndefined(whitelistDomainErr))) {
      errMsgs.push(whitelistDomainErr);
    }
    return errMsgs;
  },

  invalidPages: invalidPages,

  validationType: [validationConst.PRE_PKG_VALIDATION]
};
