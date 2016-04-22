var fs = require('fs-extra');
var rmdir = require('rimraf');
var eh = require(__dirname + '/err');
var cwd = process.cwd();
const cleanables = ['build', 'work', 'dist'];

exports.run = function clean() {
  cleanables.forEach(function (folder) {
    var dir = cwd + '/' + folder;
    fs.stat(dir, function(err, stat) {
      if(!err){
        rmdir(dir, function(err) {
          if(err) eh.error(new Error(`Error while cleaning ${folder}/ dir.`), 1);
          if(global.verbose) console.log(`Deleted ${folder}/ dir.`);
        });
      }
    });
  })
}