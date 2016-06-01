var yaml = require('js-yaml');
var fs   = require('fs');
var eh = require('./err');

var mftFile = './manifest.yml';

var reload = function () {
  if (!fs.existsSync(mftFile)) {
    eh.error(new Error("Manifest not found. Are you inside a project dir?"));
  }
  var doc = yaml.safeLoad(fs.readFileSync(mftFile, 'utf8'));
  var charset = doc['source-charset'];
  if (charset !== 'utf8') {
    eh.error(new Error('Unsupported encoding specified: ' + charset));
  }
  exports.mf = doc;
  // Easy access to common use properties:
  exports.charset = doc['source-charset'];
  exports.type = doc['pkg-type'];
  exports.pfVersion = doc['platform-version'];
  exports.pages = doc['pages'];
}

exports.reload = reload;

// Load the manifest the first time:
reload();
