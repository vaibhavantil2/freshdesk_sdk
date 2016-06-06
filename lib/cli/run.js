/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var exec = require('../routes/exec');
var versions = require('../routes/versions');

var HTTP_PORT = 10001;

module.exports = {
  run: function() {
    var express = require('express');
    var app = express();
    var ws = require('express-ws');
    var expressWs = ws(app);

    // Register middleware:
    var bodyParser = require('body-parser');
    app.use(bodyParser.json());

    // assets:
    app.use('/assets', express.static('./assets'));

    // code / config change notification:
    var watcher = require('../watcher');
    app.ws('/notify-change', function(ws) {
      ws.on('message', function(msg) {
        if (global.verbose) {
          console.log('[ws:/%s receive]: %s', url, msg);
        }
      });
    });
    var exWss = expressWs.getWss('/notify-change');
    watcher.watch(function(data) {
      exWss.clients.forEach(function each(client) {
        client.send(data);
      });
    });

    exec.run(app);
    versions.run(app);


    // Finally, listen:
    app.listen(HTTP_PORT, function() {
      console.log(`Starting local testing server at http://*:${HTTP_PORT}/\n`
                  + `Quit the server with Control-C.`);
    });

    process.on('uncaughtException', function(err) {
      if (err.code === 'EADDRINUSE') {
        console.error("Another instance of server running? Port in use.");
      }
      else {
        console.error(err);
      }
      process.exit(1);
    });
  }
};
