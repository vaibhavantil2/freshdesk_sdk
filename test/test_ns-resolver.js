var helper = require('./helper');
var expect = require('chai').expect;

describe('page util test', function(){
  before(function(){
    process.chdir(projectDir['name']);
  });

  it('should get app_id', function(done) {
    var ns = require(__dirname + '/../lib/ns-resolver');
    expect('{"app_id":"fa_fresha_101"}').eql(JSON.stringify(ns.getNamespace()));
    done();
  });
});