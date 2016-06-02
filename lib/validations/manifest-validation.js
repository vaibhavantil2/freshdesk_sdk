'use strict';

var validationConst = require('../constants').validationContants;
var mf = require('../manifest');
var _ = require('underscore');

var VALID_PAGES = ['ticket', 'contact'];

var invalidPages = function() {
  return _.difference(mf.pages, VALID_PAGES);
}

function validatePage() {
  if (!(mf.pages === undefined || _.isEmpty(mf.pages))) {
    var invldPages = invalidPages();
    if (invldPages.length > 0) {
      return `Invalid page(s) mentioned in manifest.yml: ${invldPages}.`;
    }
  }
  else {
    return "pages not specified in manifest.yml.";
  }
}

exports.validate = function() {
  
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
