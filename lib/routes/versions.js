/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var versions = require('../versions');
var manifest = require('../manifest');

module.exports = {
  run: function(app) {
    // Version Paths:
    app.get('/version.json', function(req, res) {
      res.json({
          "sdk-version": "" + global.pjson.version,
          "platform-version": manifest.pfVersion
        });
    });

    app.get('/version/compatible/:extnVer', function(req, res) {
      var extnVer = req.params.extnVer;
      if (global.verbose) {
        console.log(`connected to extn (v${extnVer}).`);
      }
      if (extnVer) {
        if (versions.isCompatible(global.pjson.version, extnVer)) {
          res.status(200);
          res.send();
          return;
        }
      }
      res.status(400);
      res.send();
    });
  }
};