/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var cwd = process.cwd();
var fileUtil = require('../utils/file-util');
var manifest = require('../manifest');
var fs = require('fs-extra');
var manifest = require('../manifest');
var _ = require('underscore');

var printMessage = function(message) {
  if (global.verbose) {
    console.log(message);
  }
}

var removeFeature = function(features) {
  var featuresToRemove = [];
  for (var f of features) {
    switch (f) {
      case 'oauth':
        if (_.contains(manifest.features, 'oauth')) {
          fileUtil.deleteFile(`${cwd}/config/oauth_config.yml`);
          featuresToRemove.push(f)
        }
        else {
          printMessage(`Current app doesn't contain ${f} feature.`);
        }
        break;
      case 'db':
        if (_.contains(manifest.features, 'db')) {
          featuresToRemove.push(f)
        }
        else {
          printMessage(`Current app doesn't contain ${f} feature.`);
        }
        break;
      default:
        printMessage(`Invalid feature: ${f}.`);
        break;
    }
  }
  if (!(_.isEmpty(featuresToRemove))) {
    printMessage(`Removed feature(s) - ${featuresToRemove}.`);
  }
  manifest.removeFeatures(featuresToRemove);
}

var addFeature = function(features) {
  var featuresToAdd = [];
  for (var f of features) {
    switch (f) {
      case 'oauth':
        if (!(_.contains(manifest.features, 'oauth'))) {
          fs.copySync(__dirname + '/../../template/oauth/config/oauth_config.yml',
            `${cwd}/config/oauth_config.yml`);
          featuresToAdd.push(f);
        }
        else {
          printMessage(`Current app already contains ${f} feature.`);
        }
        break;
      case 'db':
        if (!(_.contains(manifest.features, 'db'))) {
          featuresToAdd.push(f);
        }
        else {
          printMessage(`Current app already contains ${f} feature.`);
        }
        break;
      default:
        printMessage(`Invalid feature: ${f}.`);
        break;
    }
  }
  if (!(_.isEmpty(featuresToAdd))) {
    printMessage(`Added feature(s) - ${featuresToAdd}.`);
  }
  manifest.addFeatures(featuresToAdd);
}

module.exports = {
  run: function(options) {
    if (!(_.isEmpty(options))) {
      for (var task in options) {
        switch (task) {
          case 'remove':
            removeFeature(options[task]);
            break;

          case 'add':
            addFeature(options[task]);
            break;
        }
      }
    }
    else {
      console.log("Please provide option for update command.")
    }
  }
};
