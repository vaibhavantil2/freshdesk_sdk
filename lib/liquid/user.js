/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

var helper = require('./helper');

var DEFAULT_USER_TYPE = "current_user";
var userDetails = {};

function stripName(customFields) {
  var res = {};
  if (!(typeof customFields === 'undefined')) {
    for (var cf in customFields) {
      if (customFields.hasOwnProperty(cf)) {
        if (cf.startsWith("cf_")) {
          res[cf.split("cf_")[1]] = customFields[cf];
        }
      }
    }
  }
  return res;
}

function getCustomFields() {
  return stripName(userDetails["custom_field"]);
}

module.exports = {
  liquefy: function(userObj, type) {

    type = (typeof type === 'undefined') ? DEFAULT_USER_TYPE : type;
    userDetails= userObj['user'];

    String.prototype.bool = function() {
      return (/^true$/i).test(this);
    };

    var userObj = {
      "id" : helper.getStringValue("id"),
      "name" : helper.getStringValue("name"),
      "email" : helper.getStringValue("email"),
      "active" : helper.getStringValue("active").bool()
    };

    var userLiqObj = {};
    var finalOutput= {};
    for (var key in userObj) {
      userLiqObj[key] = userObj[key];
    }
    if (type === "requester") {
      var customFields = getCustomFields();
      for (var cField in customFields) {
        userLiqObj[cField] = customFields[cField];
      }
      finalOutput["requester"] = userLiqObj;
      return finalOutput;
    }
    finalOutput["current_user"] = userLiqObj;
    return finalOutput;
  }
};
