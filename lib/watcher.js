var fs = require('fs-extra');

var files = ['./app', './assets', './iparam', './manifest.yml'];

exports.watch = function(broadcast) {
  if(global.verbose) {
    console.log('starting watcher service...');
  }
  files.forEach(function(dir){
    fs.watch(dir, {persistent: true, recursive: true}, function(event, filename){
      var msg = {"kind": event, "path": filename};
      var msgStr = JSON.stringify(msg);
      if(global.verbose) {
        console.log('[ws://notify-change %s] %s', event, msgStr);
      }
      broadcast(msgStr);
    });
  });
}
