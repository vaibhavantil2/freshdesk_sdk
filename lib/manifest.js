var yaml = require('js-yaml');
var fs   = require('fs');
var eh = require(__dirname + '/err');

var mftFile = './manifest.yml';

(function() {
  var stats = fs.lstatSync(mftFile);
  if(!stats.isFile(mftFile)) {
    eh.error(new Error(mftFile + ' missing.'));
  }
  if(!exports.mf) {
    var doc = yaml.safeLoad(fs.readFileSync(mftFile, 'utf8'));
    var charset = doc['source-charset'];
    if(charset != 'utf8') {
      eh.error(new Error('Unsupported encoding specified: ' + charset));
    }
    exports.mf = doc;
  }
})();
