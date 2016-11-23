/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var expect = require('chai').expect;
var helper = require('./helper');
var mf;

describe("test update", function(){
  before(function(){
    process.chdir(projectDir['name']);
    mf = require(sdkDir + '/lib/manifest');
  });

  it("should remove feature", function(done) {
    require(__dirname + '/../lib/cli/update').run({
      'remove' : ['db']
    });
    mf.reload();
    expect([]).eql(mf.features);
    done();
  });

  it("should add feature", function(done) {
    require(__dirname + '/../lib/cli/update').run({
      'add' : ['db']
    });
    mf.reload();
    expect(['db']).eql(mf.features);
    done();
  });

});
