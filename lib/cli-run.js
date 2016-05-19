"use strict";

var versions = require('./versions');
var manifest = require('./manifest');
var liquid = require('./liquid');
var iparam = require('./iparam-util');
var unifier = require('./unifier');
var _ = require('underscore');

var HTTP_PORT = 10001;
var EXTN_VERSION_HEADER = "FAExtnVersion";

exports.run = function() {
  if(global.verbose) {
    console.log('starting run...');
  }

  var express = require('express');
  var app = express();
  var http = require('http').Server(app);
  var WebSocketServer = require('ws').Server;
  var wss = new WebSocketServer({ server: http });

  // Register middleware:
  var bodyParser = require('body-parser');
  app.use(bodyParser.json());

  // assets:
  app.use('/assets', express.static('./assets'));

  // version.json:
  app.get('/version.json', function(req, res){
    res.json(
      {
        "sdk-version": "" + global.pjson.version,
        "platform-version": manifest.pfVersion
      }
    );
  });

  app.get('/version/compatible/:extnVer', function(req, res){
    var extnVer = req.params.extnVer;
    console.log('version: ' + extnVer);
    if(extnVer) {
      if(versions.isCompatible(global.pjson.version, extnVer)) {
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
  wss.on('connection', function(ws){
    var url = ws.upgradeReq.url;
    if(global.verbose) {
      console.log('[ws:/%s conn]', url);
    }
    ws.on('message', function incoming(message) {
      if(global.verbose) {
        console.log('[ws:/%s receive]: %s', url, message);
      }
    });
  });
  watcher.watch(function(data){
    wss.clients.forEach(function each(client) {
      client.send(data);
    });
  });

  // Plug path:
  var plugExec = function(req, res) {
    // header validation:
    var extnVer = req.get(EXTN_VERSION_HEADER);
    if(extnVer) {
      if(!versions.isCompatible(global.pjson.version, extnVer)) {
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

    if(global.verbose) {
      console.log('### Plug request:');
      console.log(req.body);
    }

    var params = req.body;
    var pageUtil = require('./page-util');
    if(!pageUtil.allowedPage(params)){
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
    if(!(_.isEmpty(_.compact(iParamKeys)))) {
      if(!(_.isEmpty(_.compact(iparams['iparam'])))) {
        if(!(_.isEqual(Object.keys(iParamKeys[0]), Object.keys(iparams["iparam"])))) {
          console.log("There's a mismatch in keys in iparam files and test_data file.");
        }
      }
      else {
        console.log("iparams for local testing is empty. It has to be configured in iparam/test_data.yml.");
      }
    }
    var renderParams = _.extend(liquidParams, curusrLiqObj, namespace, iparams);
    var unifiedData = unifier.unify();
      liquid.render(unifiedData, renderParams, function(result) {
        if(global.verbose) {
          console.log("\n\n### Plug response: \n");
          console.log(result);
        }
        res.send(result);
      })

  }
  var PLUG_PATH = '/plug/*';
  app.get(PLUG_PATH, plugExec);
  app.post(PLUG_PATH, plugExec);

  // Finally, listen:
  http.listen(HTTP_PORT, function(){
    console.log('listening on *:' + HTTP_PORT);
  });
}
