/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var versions = require('../versions');
var liquid = require('../liquid/parser');
var iparam = require('../utils/iparam-util');
var unifier = require('../unifier');
var nsUtil = require('../utils/ns-resolver');
var _ = require('underscore');
var mf = require('../manifest');
var oauth = require('../routes/oauth2');
var fs = require('fs');

var APP_PATH = '/app/*';
var EXTN_VERSION_HEADER = "FAExtnVersion";
var RUN ="run";
var OAUTH = 'oauth';

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
  var pageUtil = require('../liquid/page-util');
  if (!pageUtil.allowedPage(params)) {
    res.json({});
    return;
  }

  if (_.contains(mf.features, OAUTH) && (!_.has(oauth.fetchCredentials(), 'access_token'))) {
    let data = fs.readFileSync(__dirname + '/../../config/local-testing-oauth-msg.html', 'utf-8');
    var payload = {
      'content' : data,
      'appName' : nsUtil.getNamespace()['app_id'],
      'features' : JSON.stringify(mf.features)
    };
    res.json(payload);
  }
  else {
    var liquidParams = pageUtil.getParams(params);
    //add current_user object
    var currUserLiq = require('../liquid/user').liquefy(params['current_user'], 'current_user');
    var namespace = require('../utils/ns-resolver').getNamespace();
    var iparams = {};
    iparams["iparam"] = iparam.getValuesForLocalTesting();
    var iParamKeys = iparam.getAllIparamKeys();
    if (!(_.isEqual(_.keys(iParamKeys[0]), _.keys(iparams["iparam"])))) {
      console.warn("There's a mismatch in keys in iparam files and iparam_test_data file.");
    }
    var renderParams = _.extend(liquidParams, currUserLiq, namespace, iparams);
    var unifiedData = unifier.unify(RUN);
    liquid.render(unifiedData, renderParams, function(result) {
      if (global.verbose) {
        console.log("\n\n### App response: \n");
        console.log(result);
      }
      var payload = {
        'content' : result,
        'appName' : nsUtil.getNamespace()['app_id'],
        'features' : JSON.stringify(mf.features)
      }
      res.json(payload);
    });
  }
}

module.exports = {
  run: function(app) {
    // App Paths:
    app.get(APP_PATH, appExec);
    app.post(APP_PATH, appExec);
  }
};
