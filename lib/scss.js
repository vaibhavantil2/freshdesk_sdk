var sass = require('node-sass');
var fs   = require('fs');

exports.compileStr = function(src) {
  var result = sass.renderSync({
    file: src
  });
  return result.css.toString('utf8');
}

exports.compile = function(src, dest) {
  var result = sass.renderSync({
    file: src
  });
  fs.writeFileSync(dest, result.css);
}
