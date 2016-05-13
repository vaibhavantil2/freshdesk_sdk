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
  scssContent = fileUtil.readFile(scssFile);
  var found = scssContent.match(/{{(.*)}}/g);
  for(var match of found) {
    var arr = match.split("|");
    if(arr.length > 1) {
      var filter = arr[1].slice(0,-2).trim();
      checkFilter(filter);
    }
    else {
      eh.error(new Error("Unsupported Liquid " + match + " in style.scss"));
    }
  }
}

function checkFilter(filter) {
  if(!(_.contains(ALLOWED_FILTERS, filter))) {
    eh.error(new Error("Unsupported Liquid Filter " + filter + " in style.scss"));
  }
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];
