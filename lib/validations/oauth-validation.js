/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var yaml = require('js-yaml');
var mf = require('../manifest');
var fs = require('fs');
var validationConst = require('./constants').validationContants;
var _ = require('underscore');
var fileUtil = require('../utils/file-util');
var OAUTH = "oauth";
var MANDATORY_KEYS = ['client_id', 'client_secret', 'authorize_url', 'token_url',
                      'token_type'];
var VALID_TOKEN_TYPES = ['account']

function validateMandatoryKeys() {
  var missingKeys = [];
  if (_.contains(mf.features, OAUTH)) {
    var oauthConfig = yaml.safeLoad(fs.readFileSync(
      './config/oauth_config.yml'));
    for (var key in oauthConfig) {
      if (oauthConfig[key] == null || oauthConfig[key] === "") {
        delete oauthConfig[key];
      }
    }
    missingKeys = _.difference(MANDATORY_KEYS, _.keys(oauthConfig))
    if (missingKeys.length > 0) {
      return `Mandatory key(s) missing in oauth_config.yml - ${missingKeys.join(', ')}.`;
    }
  }
}

function validateTokenType() {
  var oauthConfig = yaml.safeLoad(fs.readFileSync(
    './config/oauth_config.yml'));
  if (!(_.isNull(oauthConfig.token_type)) && !(_.isUndefined(oauthConfig.token_type))) {
    if (!(_.contains(VALID_TOKEN_TYPES, oauthConfig.token_type))) {
      return `Invalid token type mentioned in oauth_config.yml - ${oauthConfig.token_type}.`;
    }
  }
}

module.exports = {
  validate: function() {
    var errMsgs = [];
    if (_.contains(mf.features, OAUTH)) {
      if (fileUtil.fileExists('./config/oauth_config.yml')) {
        var mandatoryKeysErr = validateMandatoryKeys();
        var tokenTypeErr = validateTokenType();
        if (!(_.isUndefined(mandatoryKeysErr))) {
          errMsgs.push(mandatoryKeysErr);
        }
        if (!(_.isUndefined(tokenTypeErr))) {
          errMsgs.push(tokenTypeErr);
        }
      }
      else {
        errMsgs.push(`Mandatory file missing in config directory - oauth_config.yml.`)
      }
    }
    return errMsgs;
  },

  validationType: [validationConst.PRE_PKG_VALIDATION]
}
