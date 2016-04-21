var fs = require('fs-extra');
var archiver = require('archiver');
var zip = archiver('zip');

var eh = require(__dirname + "/err");
var buildFile = require(__dirname + "/build-file-handler");
var digestFile = require(__dirname + "/digest-file-handler");
var cwd = process.cwd();

exports.run = function() {
  var dest = cwd + '/dist/myapp.plg';
  fs.ensureFile(dest, function(err) {
    if (err) {
      eh.error(new Error("Error while creating " + dest), 1);
    }
    var output = fs.createWriteStream(dest);
    buildFile.genBuildFile(function() {
      digestFile.genDigestFile(function() {
        function cleanFiles(callback) {
          buildFile.delBuildDir();
          digestFile.delDigestFile();
          callback();
        }
        output.on('close', function () {
          cleanFiles(function() {
            if(global.verbose) {
              console.log("Packaged to dist/myapp.plg");
            }
          });
        });

        zip.on('error', function(err){
          eh.error(new Error("Error while generating package."), 1);
        });

        zip.pipe(output);
        zip.bulk([
          { expand: true,
            cwd: process.cwd(),
            src: ['./app/*', './assets/*', './build/*', './iparam/*', './digest.md5', './manifest.yml']}
        ]);
        zip.finalize();
      });
    });
  
  });
}
