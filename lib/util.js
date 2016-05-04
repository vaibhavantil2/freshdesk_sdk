"use strict";

var mergeInto = function(src, out) {
  for(let attrname in src) {
    out[attrname] = src[attrname];
  }
}

exports.merge = function(obj1, obj2) {
  var out = {};
  for (var i = 0; i < arguments.length; i++) {
    mergeInto(arguments[i], out);
  }
  return out;
}
