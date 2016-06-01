var fs = require('fs-extra');
var emptyDir = require('empty-dir');

var gitignore = ['dist', 'work', 'build'].join("\n");

exports.run = function(dir) {
  var prjDir = dir? dir: process.cwd();
  if (!fs.existsSync(prjDir)) {
    fs.mkdirSync(prjDir);
    if (global.verbose) {
      console.log("Created project dir: " + prjDir);
    }
  }
  if (!emptyDir.sync(prjDir)) {
    console.error('Project dir not empty.');
    process.exit(1);
  }

  // Copy from project template:
  fs.copySync('./../template/plug', prjDir);
  fs.writeFileSync(`${prjDir}/.gitignore`, gitignore + "\n");
  if (global.verbose) {
    console.log('Inited project dir: ' + prjDir);
  }
}
