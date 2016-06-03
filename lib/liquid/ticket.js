/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

'use strict';

var _ = require('underscore');
var dateFormat = require('dateformat');
var helper = require('./helper');

var FD_DATE_FORMAT = "mmmm dd yyyy 'at' h:mm TT";
var ticketDetails = {};

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

function getCustomFields() {
  return stripName(ticketDetails["custom_field"]);
}

function formatDate(input) {
  return dateFormat(new Date(input), FD_DATE_FORMAT);
}

module.exports = {
  liquefy: function(ticketObj, url) {
    ticketDetails = ticketObj['helpdesk_ticket'];

    var tktObj = {
      "id" : helper.getStringValue("display_id"),
      "description" : helper.getStringValue("description"),
      "status" : helper.getStringValue("status_name"),
      "priority" : helper.getStringValue("priority_value"),
      "source" : helper.getStringValue("source_name"),
      "subject" : helper.getStringValue("subject"),
      "ticket_type" : helper.getStringValue("ticket_type"),
      "due_by_time" : formatDate(helper.getStringValue("due_by")),
      "fr_due_by_time" : formatDate(helper.getStringValue("frDueBy")),
      "url" : url
    };

    var cfs = getCustomFields();

    var tktLiqObj= _.extend(tktObj, cfs);

    var reqLiqObj = {
      "id" : helper.getStringValue("requester_id"),
      "name" : helper.getStringValue("requester_name")
    };

    var agentLiqObj = {
      "name" : helper.getStringValue("responder_name")
    };

    var finalOutput = {
      "ticket" : tktLiqObj,
      "requester" : reqLiqObj,
      "agent" : agentLiqObj
    }
    return finalOutput;
  }
};
