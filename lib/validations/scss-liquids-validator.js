var manifest = require('../manifest');
var cwd = process.cwd();
var scssFile = cwd + "/app/style.scss";
var unifier = require('../unifier');
var fileUtil = require('../file-util');
var validationConst = require('../constants').validationContants;
var _ = require('underscore');
var ALLOWED_FILTERS = ["asset_url"];
var eh = require('../err.js');

exports.validate = function() {
  var errs = [];
  if(!fileUtil.fileExists(scssFile))
    return;
  var scssContent = fileUtil.readFile(scssFile);
  var found = scssContent.match(/{{(.*)}}/g);
  for(var match of found) {
    var arr = match.split("|");
    if(arr.length > 1) {
      var filter = arr[1].slice(0, -2).trim();
      if(!(_.contains(ALLOWED_FILTERS, filter)))
        errs.push(filter);
    }
    else {
      errs.push(match);
    }
  }
  if(!(_.isEmpty(errs))) {
    return `Unsupported Liquid(s) ${errs.toString()} in style.scss`;
  }
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];
