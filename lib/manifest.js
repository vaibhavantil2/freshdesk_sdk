/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

var yaml = require('js-yaml');
var fs   = require('fs');
var eh = require('./err');

var manifestFile = './manifest.yml';

var reload = function () {
  if (!fs.existsSync(manifestFile)) {
    eh.error(new Error("Manifest not found. Are you inside a project dir?"));
  }
  var doc = yaml.safeLoad(fs.readFileSync(manifestFile, 'utf8'));
  exports.mf = doc;
  // Easy access to common use properties:
  exports.pfVersion = doc['platform-version'];
  exports.pages = doc['pages'];
}

exports.reload = reload;

// Load the manifest the first time:
reload();
