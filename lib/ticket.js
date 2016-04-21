

exports.liquefy = function(ticket_obj, url) {
  var ticketDetails = ticket_obj['helpdesk_ticket'];

  function getStringValue(input) {
    var o = ticketDetails[input];
    if( o == null ) {
      return null;
    }
    else if( o instanceof String) {
      return o;
    }
    else {
      return o.toString();
    }
  }

  function stripName(custom_fields) {
    var res = {};
    for (var cf in custom_fields) {
      if (custom_fields.hasOwnProperty(cf)) {
        if(cf.indexOf("_") > -1) {
          res[cf.split("_")[0]] = custom_fields[cf];
        }
      }
    }
    return res;
  }
  
  function getCustomFields() {
    return stripName(ticketDetails["custom_field"]);
  }

  var tktObj = {
    "id" : getStringValue("display_id"),
    "description" : getStringValue("description"),
    "status" : getStringValue("status_name"),
    "priority" : getStringValue("priority_value"),
    "source" : getStringValue("source_name"),
    "subject" : getStringValue("subject"),
    "ticket_type" : getStringValue("ticket_type"),
    "due_by_time" : getStringValue("due_by"),
    "fr_due_by_time" : getStringValue("frDueBy"),
    "url" : url
  };

  var cfs = getCustomFields();
  var tktLiqObj= JSON.parse((JSON.stringify(tktObj) + JSON.stringify(cfs)).replace(/}{/g,","));

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
