var fs = require('fs');
var nsUtil = require('../ns-resolver');
const MAX_PKG_SIZE = 5000000;
var plgDir = process.cwd() + `/dist/${nsUtil.getRootFolder()}${nsUtil.pkgExt}`;
var validationConst = require('../constants').validationContants;

exports.validate = function(callback) {
  fs.stat(plgDir, function(err, stat) {
    if(err) return callback(err);
    if(stat.size > MAX_PKG_SIZE)
      return callback(new Error("Maximum package size exceeded."));
    return callback();
  });
}

exports.validationType = [validationConst.POST_PKG_VALIDATION];