/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var helper = require('./helper');
var expect = require('chai').expect;

describe('Name space resolver test', function(){
  before(function(){
    process.chdir(projectDir['name']);
  });

  it('should get app_id', function(done) {
    var ns = require(__dirname + '/../lib/utils/ns-resolver');
    expect(JSON.stringify(ns.getNamespace())).eql('{"app_id":"fresha_101_101"}');
    done();
  });
});