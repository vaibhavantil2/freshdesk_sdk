var DP_ROUTER_PATH = "/dprouter";
var httpUtil = require("../utils/http-util");

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