/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var oauth2Util = require('../routes/oauth2');

module.exports = {
  refresh: function(req, res) {
    oauth2Util.refresh(req, res);
  }
}
