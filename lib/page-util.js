var ticket = require(__dirname + '/ticket');
var user = require(__dirname + "/user");
var eh = require(__dirname + "/err");

exports.getParams = function(params) {
  if(params['page'] == 'ticket') {
    var tktLiqParams = ticket.liquefy(params['pageParams'], params['url']);
    var rqstrLiqParams = user.liquefy(params['requester'], "requester");
    return JSON.parse((JSON.stringify(tktLiqParams) + JSON.stringify(rqstrLiqParams)).replace(/}{/g,","));
  }
  else if (params['page'] == 'contact') {
    return user.liquefy(params['pageParams']);
  }
  else {
    eh.error(new Error("Invalid page type"), 1);
  }
}