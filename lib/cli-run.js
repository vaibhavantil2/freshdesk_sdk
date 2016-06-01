'use strict';

var versions = require('./versions');
var manifest = require('./manifest');
var liquid = require('./liquid');
var iparam = require('./iparam-util');
var unifier = require('./unifier');
var _ = require('underscore');

var HTTP_PORT = 10001;
var EXTN_VERSION_HEADER = "FAExtnVersion";

exports.run = function() {
  if (global.verbose) {
    console.log('starting run...');
  }

  var express = require('express');
  var app = express();
  var ws = require('express-ws');
  var expressWs = ws(app);

  // Register middleware:
  var bodyParser = require('body-parser');
  app.use(bodyParser.json());

  // assets:
  app.use('/assets', express.static('./assets'));

  // version.json:
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

  // code / config change notification:
  var watcher = require('./watcher');
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

  // App path:
  var appExec = function(req, res) {
    // header validation:
    var extnVer = req.get(EXTN_VERSION_HEADER);
    if (extnVer) {
      if (!versions.isCompatible(global.pjson.version, extnVer)) {
        console.error(`Request from incompatible browser extn: ${extnVer}.`);
        res.status(400);
        res.send();
        return;
      }
    }
    else {
      console.error(`Version header '${EXTN_VERSION_HEADER}' not available in request.`);
      res.status(400);
      res.send();
      return;
    }

    if (global.verbose) {
      console.log('### App request:');
      console.log(req.body);
    }

    var params = req.body;
    var pageUtil = require('./page-util');
    if (!pageUtil.allowedPage(params)) {
      res.send();
      return;
    }

    var liquidParams = pageUtil.getParams(params);
    //add current_user object
    var curusrLiqObj = require('./user').liquefy(params['current_user']);
    var namespace = require('./ns-resolver').getNamespace();
    var iparams = {};
    iparams["iparam"] = iparam.getValuesForLocalTesting();
    var iParamKeys = iparam.getAllIparamKeys();
    if (!(_.isEqual(_.keys(iParamKeys[0]), _.keys(iparams["iparam"])))) {
      console.warn("There's a mismatch in keys in iparam files and iparam_test_data file.");
    }
    var renderParams = _.extend(liquidParams, curusrLiqObj, namespace, iparams);
    var unifiedData = unifier.unify();
      liquid.render(unifiedData, renderParams, function(result) {
        if (global.verbose) {
          console.log("\n\n### App response: \n");
          console.log(result);
        }
        res.send(result);
      });
  }
  var APP_PATH = '/app/*';
  app.get(APP_PATH, appExec);
  app.post(APP_PATH, appExec);

  // Finally, listen:
  app.listen(HTTP_PORT, function() {
    console.log('listening on *:' + HTTP_PORT);
  });

  process.on('uncaughtException', function(err) {
    if (err.code === 'EADDRINUSE') {
      console.error("Another instance of server is already running.");
    }
    else {
      console.error(err);
    }
    process.exit(1);
  });
}
