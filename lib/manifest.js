var yaml = require('js-yaml');
var fs   = require('fs');

var mftFile = './manifest.yml';

(function() {
  var stats = fs.lstatSync(mftFile);
  if(!stats.isFile(mftFile)) {
    throw mftFile + " missing.";
  }
  if(!exports.mf) {
    var doc = yaml.safeLoad(fs.readFileSync(mftFile, 'utf8'));
    exports.mf = doc;
  }
})();
