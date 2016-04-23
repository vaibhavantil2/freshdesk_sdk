var fs = require('fs-extra');
var manifest = require(__dirname + '/manifest');

exports.run = function() {
  console.log('# Project details:');
  console.log("  Project type: " + manifest.mf['pkg-type']);
  console.log("  Source encoding: " + manifest.charset);
  console.log("  Platform version: " + manifest.mf['platform-version']);
  console.log();
  console.log("# Running on:");
  console.log("  Sdk version: " + global.pjson.version);
}
