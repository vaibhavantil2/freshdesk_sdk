'use strict';

var _ = require('underscore');
var fileUtil = require('../utils/file-util');
var validationConst = require('./constants').validationContants;

var scssFile = process.cwd() + "/app/style.scss";
var charset = 'utf8';

module.exports = {
  validate: function() {
    var errs = [];
    if (!fileUtil.fileExists(scssFile)) { return; }
    var scssContent = fileUtil.readFile(scssFile, charset);
    var found = scssContent.match(/{{(.*)}}/g);
    for (let match in found) {
      if (!found[match].match(/\s*('|").+('|")\s*\|\s*asset_url\s*/g)) {
        errs.push(found[match]);
      }
    }
    if (!(_.isEmpty(errs))) {
      return `Unsupported Liquid(s) ${errs} in style.scss.`;
    }
  },

  validationType: [validationConst.PRE_PKG_VALIDATION]
};
