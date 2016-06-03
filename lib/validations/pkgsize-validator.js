var nsUtil = require('../utils/ns-resolver');
var validationConst = require('./constants').validationContants;
var fileUtil = require('../utils/file-util');

var MAX_PKG_SIZE = 5000000;
var plgDir = process.cwd() + `/dist/${nsUtil.getRootFolder()}${nsUtil.pkgExt}`;

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