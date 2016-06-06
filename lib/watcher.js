/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var fs = require('fs-extra');
var manifest = require('./manifest');

var files = ['./app', './assets', './config', './manifest.yml'];

module.exports = {
  watch: function(broadcast) {
    if (global.verbose) {
      console.log('starting watcher service...');
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
