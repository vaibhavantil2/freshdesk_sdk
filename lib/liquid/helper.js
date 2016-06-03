module.exports = {
  getStringValue: function(map, input) {
    var obj = map[input];
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
