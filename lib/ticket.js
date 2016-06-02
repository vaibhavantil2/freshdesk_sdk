/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

'use strict';

var _ = require('underscore');
var dateFormat = require('dateformat');

var FD_DATE_FORMAT = "mmmm dd yyyy 'at' h:mm TT";
var ticketDetails = {};

function getStringValue(input) {
  var o = ticketDetails[input];
  if ( o == null ) {
    return null;
  }
  else if ( o instanceof String) {
    return o;
  }
  else {
    return o.toString();
  }
}

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
      "id" : getStringValue("display_id"),
      "description" : getStringValue("description"),
      "status" : getStringValue("status_name"),
      "priority" : getStringValue("priority_value"),
      "source" : getStringValue("source_name"),
      "subject" : getStringValue("subject"),
      "ticket_type" : getStringValue("ticket_type"),
      "due_by_time" : formatDate(getStringValue("due_by")),
      "fr_due_by_time" : formatDate(getStringValue("frDueBy")),
      "url" : url
    };

    var cfs = getCustomFields();

    var tktLiqObj= _.extend(tktObj, cfs);

    var reqLiqObj = {
      "id" : getStringValue("requester_id"),
      "name" : getStringValue("requester_name")
    };

    var agentLiqObj = {
      "name" : getStringValue("responder_name")
    };

    var finalOutput = {
      "ticket" : tktLiqObj,
      "requester" : reqLiqObj,
      "agent" : agentLiqObj
    }
    return finalOutput;
  }
};
