/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var _ = require('underscore');
var dateFormat = require('dateformat');
var helper = require('./helper');
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
      "id" : helper.getStringValue(ticketDetails, "display_id"),
      "description" : helper.getStringValue(ticketDetails, "description"),
      "status" : helper.getStringValue(ticketDetails, "status_name"),
      "priority" : helper.getStringValue(ticketDetails, "priority_value"),
      "source" : helper.getStringValue(ticketDetails, "source_name"),
      "subject" : helper.getStringValue(ticketDetails, "subject"),
      "ticket_type" : helper.getStringValue(ticketDetails, "ticket_type"),
      "due_by_time" : formatDate(helper.getStringValue(ticketDetails, "due_by")),
      "fr_due_by_time" : formatDate(helper.getStringValue(ticketDetails, "frDueBy")),
      "url" : params['url']
    };

    var cfs = getCustomFields(ticketDetails);

    var tktLiqObj= _.extend(tktObj, cfs);

    var reqLiqObj = {
      "id" : helper.getStringValue(ticketDetails, "requester_id"),
      "name" : helper.getStringValue(ticketDetails, "requester_name")
    };

    var agentLiqObj = {
      "name" : helper.getStringValue(ticketDetails, "responder_name")
    };

    var finalOutput = {
      "ticket" : tktLiqObj,
      "requester" : reqLiqObj,
      "agent" : agentLiqObj
    }
    var rqstrLiqParams = user.liquefy(params['requester'], "requester");
    return _.extend(finalOutput, rqstrLiqParams);
  }
};
