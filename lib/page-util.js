var ticket = require(__dirname + '/ticket');
var user = require(__dirname + "/user");

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
    throw "Invalid page type";
  }
}