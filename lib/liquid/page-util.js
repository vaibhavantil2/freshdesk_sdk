/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var _ = require('underscore');
var ticket = require('./ticket');
var user = require("./user");
var manifest = require('../manifest');

module.exports = {
  getParams: function(params) {
    if (params['page'] === 'ticket' && _.contains(manifest.pages, 'ticket')) {
      var tktLiqParams = ticket.liquefy(params['pageParams'], params['url']);
      var rqstrLiqParams = user.liquefy(params['requester'], "requester");
      return _.extend(tktLiqParams, rqstrLiqParams);
    }
    else if (params['page'] === 'contact' && _.contains(manifest.pages, 'contact')) {
      return user.liquefy(params['pageParams']);
    }
    else {
      return;
    }
  },

  allowedPage: function(params) {
    if (_.contains(manifest.pages, params['page'])) {
      return true;
    }
    else {
      if (global.verbose) {
        console.log('Requested page is not added in manifest.yml');
      }
      return false;
    }
  }
};
