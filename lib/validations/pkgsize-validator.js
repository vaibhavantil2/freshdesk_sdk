var nsUtil = require('../ns-resolver');
var validationConst = require('../constants').validationContants;
var fileUtil = require('../file-util');

var MAX_PKG_SIZE = 5000000;
var plgDir = process.cwd() + `/dist/${nsUtil.getRootFolder()}${nsUtil.pkgExt}`;

module.exports = {

  validate: function() {
    var errMsgs = [];
    var fileStat = fileUtil.statFile(plgDir);
    if (fileStat.size > MAX_PKG_SIZE) {
      errMsgs.push(new Error("Maximum package size exceeded."));
    }
    return errMsgs;
  },

  validationType: [validationConst.POST_PKG_VALIDATION]
};