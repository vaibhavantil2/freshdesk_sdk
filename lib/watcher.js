/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var fs = require('fs-extra');
var manifest = require('./manifest');
var fileUtil = require('./utils/file-util');

var files = ['./app', './config', './manifest.yml'];

/*
  Watcher for change in SDK project files.
*/

module.exports = {
  watch: function(broadcast) {
    var assetsFolderPath = process.cwd() + '/assets/';
    if (fileUtil.fileExists(assetsFolderPath)) {
      files.push('./assets');
    }
    if (global.verbose) {
      console.log('Starting watcher service...');
    }
    files.forEach(function(dir) {
      fs.watch(dir, {persistent: true, recursive: true}, function(event, filename) {
        // Reload cache:
        if ('manifest.yml' === filename) {
          manifest.reload();
        }

        // Broadcast:
        var msg = {"kind": event, "path": filename};
        var msgStr = JSON.stringify(msg);
        if (global.verbose) {
          console.log('[ws://notify-change %s] %s', event, msgStr);
        }
        broadcast(msgStr);
      });
    });
  }
};
