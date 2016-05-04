var ticket = require(__dirname + '/ticket');
var user = require(__dirname + "/user");
var eh = require(__dirname + "/err");
var _ = require('underscore');
var mf = require(__dirname + '/manifest');
var mfvalidate = require(__dirname + '/validations/manifest-validation');

exports.getParams = function(params) {
  if(params['page'] == 'ticket' && _.contains(mf.pages, 'ticket')) {
    var tktLiqParams = ticket.liquefy(params['pageParams'], params['url']);
    var rqstrLiqParams = user.liquefy(params['requester'], "requester");
    return JSON.parse((JSON.stringify(tktLiqParams) + JSON.stringify(rqstrLiqParams)).replace(/}{/g,","));
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

