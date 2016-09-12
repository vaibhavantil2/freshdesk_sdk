var DP_ROUTER_PATH = "/dprouter";

var dpRouterExec = function(req, res) {
  var dynamicRoute = require(`../api/${req.headers['mkp-route']}`);
  dynamicRoute[req.body.action](req, res);
}

var enableCORS = function(res) {
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'POST, GET, PUT, UPDATE, DELETE, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'X-CSRF-Token, X-Requested-With, content-type, MKP-EXTNID, MKP-VERSIONID, MKP-Route');
}

module.exports = {
  run: function(app) {
    app.use(function (req, res, next) {
      enableCORS(res);
      next();
    });
    app.post(DP_ROUTER_PATH, dpRouterExec);
  }
}