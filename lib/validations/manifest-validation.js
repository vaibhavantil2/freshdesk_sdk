"use strict";

var validationConst = require('../constants').validationContants;
var mf = require('../manifest');
var _ = require('underscore');

var VALID_PAGES = ['ticket', 'contact'];

var invalidPages = function() {
  return _.difference(mf.pages, VALID_PAGES);
}

function validatePage(){
  if(!(mf.pages == undefined || _.isEmpty(mf.pages))){
    var invalid_pages = invalidPages();
    if (invalid_pages.length > 0) {
      return `Invalid page(s) mentioned in manifest.yml: ${invalid_pages}`;
    }
  }
  else{
    return "pages not specified in manifest.yml";
  }
}

exports.validate = function () {
  var err_msges = [];
  var err = validatePage();
  if(!(err==undefined)) {
    err_msges.push(err);
  }
  return err_msges;
}

exports.invalidPages = invalidPages;
exports.validationType = [validationConst.PRE_PKG_VALIDATION];