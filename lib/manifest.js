var yaml = require('js-yaml');
var fs   = require('fs');
var eh = require(__dirname + '/err');

var mftFile = './manifest.yml';

(function() {
  if(!fs.existsSync(mftFile)) {
    eh.error(new Error("Command needs to be executed from project dir."));
  }
  if(!exports.mf) {
    var doc = yaml.safeLoad(fs.readFileSync(mftFile, 'utf8'));
    var charset = doc['source-charset'];
    if(charset != 'utf8') {
      eh.error(new Error('Unsupported encoding specified: ' + charset));
    }
    exports.mf = doc;
    // Easy access to common use properties:
    exports.charset = doc['source-charset'];
    exports.type = doc['pkg-type'];
    exports.pfVersion = doc['platform-version'];
  }
})();