var fs = require('fs-extra');
var mf = require('./manifest');

var files = ['./app', './assets', './config', './manifest.yml'];

exports.watch = function(broadcast) {
  if (global.verbose) {
    console.log('starting watcher service...');
  }
  files.forEach(function(dir) {
    fs.watch(dir, {persistent: true, recursive: true}, function(event, filename) {
      // Reload cache:
      if ('manifest.yml' === filename) {
        mf.reload();
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
