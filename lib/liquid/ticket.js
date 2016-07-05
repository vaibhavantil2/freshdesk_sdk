/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var _ = require('underscore');
var dateFormat = require('dateformat');
var user = require("./user");

var FD_DATE_FORMAT = "mmmm dd yyyy 'at' h:mm TT";

function stripName(customFields) {
  var res = {};
  for (var cf in customFields) {
    if (customFields.hasOwnProperty(cf)) {
      if (cf.indexOf("_") > -1) {
        res[cf.split("_")[0]] = customFields[cf];
      }
    }
  }
  return res;
}

function getCustomFields(ticketDetails) {
  return stripName(ticketDetails["custom_field"]);
}

function formatDate(input) {
  return dateFormat(new Date(input), FD_DATE_FORMAT);
}

module.exports = {
  liquefy: function(params) {
    var ticketDetails = params['pageParams']['helpdesk_ticket'];

    var tktObj = {
      "id" : ticketDetails["display_id"],
      "description" : ticketDetails["description"],
      "status" : ticketDetails["status_name"],
      "priority" : ticketDetails["priority_name"],
      "source" : ticketDetails["source_name"],
      "subject" : ticketDetails["subject"],
      "ticket_type" : ticketDetails["ticket_type"],
      "due_by_time" : ticketDetails["due_by"],
      "fr_due_by_time" : ticketDetails["frDueBy"],
      "url" : params['url']
    };

    var cfs = getCustomFields(ticketDetails);

    var tktLiqObj= _.extend(tktObj, cfs);

    var reqLiqObj = {
      "id" : ticketDetails["requester_id"],
      "name" : ticketDetails["requester_name"]
    };

    var agentLiqObj = {
      "name" : ticketDetails["responder_name"]
    };

    tktLiqObj["requester"] = reqLiqObj;
    tktLiqObj["agent"] = agentLiqObj;

    var finalOutput = {
      "ticket" : tktLiqObj,
      "requester" : reqLiqObj,
      "agent" : agentLiqObj
    }
    var rqstrLiqParams = user.liquefy(params['requester'], "requester");
    return _.extend(finalOutput, rqstrLiqParams);
  }
};
