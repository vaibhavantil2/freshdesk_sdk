/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var liquid = require(__dirname + '/../lib/liquid/parser');
var expect = require('chai').expect;

describe("test liquid filter asset_url", function(){
  var input = "asset: {{'a.png' | asset_url}}";
  var exp = "asset: http://localhost:10001/assets/a.png";

  it("should pass", function(done){
    liquid.render(input, {}, function(result){
      expect(result).equals(exp);
      done();
    });
  });
});
