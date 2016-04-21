var fs = require('fs-extra');

var cwd = process.cwd();

exports.getNamespace = function() {
  var n = cwd.lastIndexOf('/');
  return { "app_id" : cwd.substring(n + 1)}; // TBD: normalize
}
