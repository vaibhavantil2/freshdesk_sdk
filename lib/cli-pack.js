var Zip = require('machinepack-zip');

exports.run = function() {
  var dest = 'myapp.plg';
  Zip.zip({
    sources: ['./app', './manifest.yml', './assets', './iparam'],
    destination: dest,
  }).exec({
    error: function() {
      // handle!
      console.error('`pack` error: ' + err);
    },
    success: function() {
      // handle!
      if(global.verbose) {
        console.log('successfully packed: %s', dest);
      }
    }
  });
}
