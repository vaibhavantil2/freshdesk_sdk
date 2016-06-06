/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

require('../manifest');
var fs = require('fs-extra');
var rmdir = require('rimraf');
var eh = require('../utils/err');

var cwd = process.cwd();
const cleanables = ['build', 'work', 'dist'];

module.exports = {
  run: function() {
    cleanables.forEach(function (folder) {
      var dir = cwd + '/' + folder;
      fs.stat(dir, function(err) {
        if (!err) {
          rmdir(dir, function(err) {
            if (err) { eh.error(new Error(`Error while cleaning ${folder}/ dir.`)) };
            if (global.verbose) { console.log(`Deleted ${folder}/ dir.`) };
          });
        }
      });
    });
  }
};
