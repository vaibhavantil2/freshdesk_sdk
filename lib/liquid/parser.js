/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var liquid = require("liquid-node");
var engine = new liquid.Engine;

var ASSET_HOST = 'http://localhost:10001/assets/'

engine.registerFilters({
  "asset_url": function(input) {
    return ASSET_HOST + input;
  }
});

module.exports = {
          // callback function takes two arguments:
          // 1. error
          // 2. result
  render: function(input, data, callback) {
    engine.parse(input).then(function(template) {
      return template.render(data);
    }).then(callback);
  }
};
