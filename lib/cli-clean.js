var fs = require('fs-extra');
var rmdir = require('rimraf');

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
            if (err) throw "Error while cleaning " + dir + "/ dir.";
            if(global.verbose) {
              console.log("Deleted " + cleanables[j] + "/ dir.");
            }
          });  
        }
      });
    })();
  }
}
