(function() {
  return {
    initialize: function() {
      console.log("My first app!");
      if(page_type == "ticket") {
        var requesterName = domHelper.ticket.getTicketInfo().helpdesk_ticket.requester_name;
        jQuery('#apptext').text("Ticket created by " + requesterName);
      }
      else if(page_type == "contact"){
        var agentName = domHelper.contact.getContactInfo().user.name;
        jQuery('#apptext').text("Hello " + agentName);
      }
    }
  }
})();

{%comment%}

# Iparam
  The ​installation parameters or iparams ​are essentially the settings that you
  want your users to configure when installing the apps.
# Iparam Usage
  - {{iparam.username}}
  - {{iparam.country}}

{%endcomment%}
