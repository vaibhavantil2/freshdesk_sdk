"use strict";

var _ = require('underscore');
var ticket = require('./ticket');
var user = require("./user");
var eh = require("./err");
var mf = require('./manifest');
var mfvalidate = require('./validations/manifest-validation');
var util = require('./util');

exports.getParams = function(params) {
  if(params['page'] == 'ticket' && _.contains(mf.pages, 'ticket')) {
    var tktLiqParams = ticket.liquefy(params['pageParams'], params['url']);
    var rqstrLiqParams = user.liquefy(params['requester'], "requester");
    return util.merge(tktLiqParams, rqstrLiqParams);
  }
  else if (params['page'] == 'contact' && _.contains(mf.pages, 'contact')) {
    return user.liquefy(params['pageParams']);
  }
  else {
    return;
  }
}

exports.allowedPage = function(params){
  if(_.contains(mf.pages, params['page'])){
    return true;
  }
  else {
    if(global.verbose) {
      console.log('Requested page is not added in manifest.yml');
    }
    return false;
  }
}
