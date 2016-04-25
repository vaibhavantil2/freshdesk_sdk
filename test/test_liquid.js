var liquid = require(__dirname + '/../lib/liquid');
var expect = require('chai').expect;

describe("test liquid filter asset_url", function(){
  var input = "asset: {{'a.png' | asset_url}}";
  var exp = "asset: http://localhost:10001/assets/a.png";

  it("should pass", function(done){
    liquid.render(input, {}, function(result){
      expect(exp).equals(result);
      done();
    });
  });
});
