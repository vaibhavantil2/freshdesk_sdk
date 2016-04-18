var program = require('commander');
var fs = require('fs-extra');
var emptyDir = require('empty-dir');

exports.run = function(type, dir) {
  if(type != "plug") {
    console.error('Project type not supported.');
    process.exit(1);
  }
  var prjDir = dir? dir: process.cwd();
  if(!fs.existsSync(prjDir)) {
    fs.mkdirSync(prjDir);
    if(global.verbose) {
      console.log("Created project dir: " + prjDir);
    }
  }
  if(!emptyDir.sync(prjDir)) {
    console.error('Project dir not empty');
    process.exit(1);
  }

  // Copy from project template:
  fs.copySync(__dirname + '/../template/plug', prjDir);
  if(global.verbose) {
    console.log('Inited project dir: ' + prjDir);
  }
}
