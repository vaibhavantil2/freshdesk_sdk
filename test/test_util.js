var util = require(__dirname + '/../lib/util');
var expect = require('chai').expect;

describe('test util', function(){
  it('object merge', function(){
    var obj1 = {
      a: 'a',
      b: 'b'
    };
    var obj2 = {
      x: 'x',
      y: 'y'
    };
    var exp = {
      a: 'a',
      b: 'b',
      x: 'x',
      y: 'y'
    };
    expect(exp).to.be.eql(util.merge(obj1, obj2));
  });
});
