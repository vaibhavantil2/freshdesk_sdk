var oauth2Util = require('../routes/oauth2');

module.exports = {
  refresh: function(req, res) {
    oauth2Util.refresh(req, res);
  }
}
