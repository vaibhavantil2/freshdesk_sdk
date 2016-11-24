/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';
var DP_ROUTER_PATH = "/dprouter";
var httpUtil = require("../utils/http-util");

/*
  Proxy to use features.
*/

// Dynamically route to required feature api

var dpRouterExec = function(req, res) {
  var dynamicRoute = require(`../api/${req.headers['mkp-route']}`);
  dynamicRoute[req.body.action](req, res);
}

module.exports = {
  run: function(app) {
    app.use(function (req, res, next) {
      httpUtil.enableCORS(res);
      next();
    });
    app.post(DP_ROUTER_PATH, dpRouterExec);
  }
}