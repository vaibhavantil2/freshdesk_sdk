var fs = require('fs-extra');
var rmdir = require('rimraf');
var eh = require(__dirname + '/err');

var cwd = process.cwd();
const cleanables = ['build', 'work', 'dist'];

function test(callback) {
  callback();
}

exports.run = function clean() {
  var length = cleanables.length;
  for(var i = 0; i<length; i++) {
    (function() {
      var j = i;
      var dir = cwd + '/' + cleanables[j];
      fs.exists(dir, (exists) => {
        if(exists) {
          rmdir(dir, function(err) {
            if (err) {
              var errMsg = "Error while cleaning " + dir + "/ dir.";
              eh.error(new Error(errMsg), 1);
            }
            if(global.verbose) {
              console.log("Deleted " + cleanables[j] + "/ dir.");
            }
          });
        }
      });
    })();
  }
}
