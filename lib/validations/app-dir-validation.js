var fs = require('fs-extra');

exports.run = function() {
  var that = this;
  var files = ['style.scss', 'app.js', 'template.html'];
  var arrayLength = files.length;
  for(var i =0 ; i< arrayLength; i++) {
    console.log(files[i]);
    fs.stat(process.cwd() + '/app/' + files[i], function(err, stat) {
      if(err != null && err.code == 'ENOENT') {
        console.log(files);
        console.log(i);
        throw 'file does not exist';
      }
    });
  }
}