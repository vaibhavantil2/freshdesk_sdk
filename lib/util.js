"use strict";

exports.merge = function() {
  var out = {};
  for (let i = 0; i < arguments.length; i++) {
    for(let attrname in arguments[i]) {
      out[attrname] = arguments[i][attrname];
    }
  }
  return out;
}
