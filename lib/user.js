var DEFAULT_USER_TYPE = "current_user";
var userDetails = {};

function getStringValue(input) {
  var o = userDetails[input];
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
  if(!(typeof custom_fields === 'undefined')) {
    for(var cf in custom_fields) {
      if (custom_fields.hasOwnProperty(cf)) {
        if(cf.startsWith("cf_")) {
          res[cf.split("cf_")[1]] = custom_fields[cf];
        }
      }
    }
  }
  return res;
}

function getCustomFields() {
  return stripName(userDetails["custom_field"]);
}

exports.liquefy = function(user_obj, type) {


  type = typeof type === 'undefined' ? DEFAULT_USER_TYPE : type;

  userDetails= user_obj['user'];

  String.prototype.bool = function() {
    return (/^true$/i).test(this);
  };

  var userObj = {
    "id" : getStringValue("id"),
    "name" : getStringValue("name"),
    "email" : getStringValue("email"),
    "active" : getStringValue("active").bool()
  };

  var userLiqObj = {};
  var finalOutput= {};
  for(var key in userObj) userLiqObj[key]=userObj[key];

  if(type == "requester") {
    var cfs = getCustomFields();
    for(var key in cfs) userLiqObj[key]=cfs[key];
    finalOutput["requester"] = userLiqObj;
    return finalOutput;
  }
  finalOutput["current_user"] = userLiqObj;
  return finalOutput;
}
