var fs = require('fs-extra');
var manifest = require(__dirname + '/manifest');

exports.run = function() {
  console.log('# Project details:');
  console.log(`  Source encoding: ${manifest.charset}`);
  console.log(`  Platform version: v${manifest.pfVersion}`);
  console.log();
  console.log('# Running on:');
  console.log(`  Sdk version: v${global.pjson.version}`);
  console.log(`  Running on Node version: ${process.version}`);
}