module.exports = {
  getStringValue: function(input) {
    var obj = ticketDetails[input];
    if ( obj == null ) {
      return null;
    }
    else if ( obj instanceof String) {
      return obj;
    }
    else {
      return obj.toString();
    }
  }
};
