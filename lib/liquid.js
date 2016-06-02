/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

var Liquid = require("liquid-node");
var engine = new Liquid.Engine;

engine.registerFilters({
  "asset_url": function(input) {
    return 'http://localhost:10001/assets/' + input;
  }
});

// callback function takes two arguments:
// 1. error
// 2. result
exports.render = function(input, data, callback) {
  engine.parse(input).then(function(template) {
    return template.render(data);
  }).then(callback);
}
