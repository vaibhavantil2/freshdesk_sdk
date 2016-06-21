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
    if(_.contains(manifest.pages, params['page'])) {
      var liqType = require(`./${params['page']}`);
      return liqType.liquefy(params);
    }
    else{
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
