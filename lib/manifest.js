/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var yaml = require('js-yaml');
var fs   = require('fs');
var eh = require('./utils/err');
var fileUtil = require('./utils/file-util');
var _ = require('underscore');
var manifestFile = './manifest.yml';

/*
  Library file to access manifest contents.
*/

var addKey = function(key, value) {
  var manifestData = yaml.safeLoad(fs.readFileSync(manifestFile, 'utf8'));
  if (_.isEmpty(manifestData[key]) && _.isUndefined(manifestData[key])) {
    manifestData[key] = value;
  }
  fileUtil.writeFile(manifestFile, yaml.dump(manifestData));
}

var removeKey = function(key) {
  var manifestData = yaml.safeLoad(fs.readFileSync(manifestFile, 'utf8'));
  delete manifestData[key];
  fileUtil.writeFile(manifestFile, yaml.dump(manifestData));
}

var addFeatures = function(features) {
  var manifestData = yaml.safeLoad(fs.readFileSync(manifestFile, 'utf8'));
  if (_.isUndefined(manifestData.features)) {
    manifestData['features'] = [];
  }
  manifestData['features'] = _.union(manifestData['features'], features)
  fileUtil.writeFile(manifestFile, yaml.dump(manifestData));
}

var removeFeatures = function(features) {
  var manifestData = yaml.safeLoad(fs.readFileSync(manifestFile, 'utf8'));
  manifestData['features'] = _.difference(manifestData['features'], features)
  fileUtil.writeFile(manifestFile, yaml.dump(manifestData));
}

var reload = function () {
  if (!fs.existsSync(manifestFile)) {
    eh.error(new Error("Manifest not found. Are you inside a project dir?"));
  }
  var doc = yaml.safeLoad(fs.readFileSync(manifestFile, 'utf8'));
  module.exports.manifest = doc;
  // Easy access to common use properties:
  module.exports.pfVersion = doc['platform-version'];
  module.exports.pages = doc['pages'];
  module.exports.features = doc['features'] || [];
  module.exports.whitelistedDomains = doc['whitelisted-domains'] || [];
}

module.exports = {
  addFeatures: addFeatures,
  removeFeatures: removeFeatures,
  addKey: addKey,
  removeKey: removeKey,
  reload: reload,
  manifest: null,
  pfVersion: null,
  pages: null,
  features: null,
  whitelistedDomains: null
};

// Load the manifest the first time:
reload();
