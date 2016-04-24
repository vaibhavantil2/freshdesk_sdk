var nsUtil = require('../ns-resolver');
const MAX_PKG_SIZE = 5000000;
var plgDir = process.cwd() + `/dist/${nsUtil.getRootFolder()}${nsUtil.pkgExt}`;
var validationConst = require('../constants').validationContants;
var fileUtil = require('../file-util').fileUtil;

exports.validate = function(callback) {
  err_msges = [];
  var fileStat = fileUtil.statFile(plgDir);
  if(fileStat.size > MAX_PKG_SIZE)
    err_msges.push(new Error("Maximum package size exceeded."));
  return err_msges;
}

exports.validationType = [validationConst.POST_PKG_VALIDATION];