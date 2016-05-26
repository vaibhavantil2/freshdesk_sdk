'use strict';

var manifest = require('../manifest');
var scssFile = process.cwd() + "/app/style.scss";
var unifier = require('../unifier');
var fileUtil = require('../file-util');
var validationConst = require('../constants').validationContants;
var _ = require('underscore');
var ALLOWED_FILTERS = ["asset_url"];
var eh = require('../err.js');

exports.validate = function() {
  var errs = [];
  if (!fileUtil.fileExists(scssFile))
    return;
  var scssContent = fileUtil.readFile(scssFile, manifest.charset);
  var found = scssContent.match(/{{(.*)}}/g);
  for (let match in found) {
    if (!found[match].match(/\s*('|").+('|")\s*\|\s*asset_url\s*/g)) {
      errs.push(found[match]);
    }
  }
  if (!(_.isEmpty(errs))) {
    return `Unsupported Liquid(s) ${errs} in style.scss.`;
  }
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];
