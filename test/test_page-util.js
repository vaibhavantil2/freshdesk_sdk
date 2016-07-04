/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var helper = require('./helper');
var fs = require('fs-extra');
var _ = require('underscore');
var expect = require('chai').expect;

describe('page util test', function(){
  before(function(){
    process.chdir(projectDir['name']);
  });

  it('should get ticket page params', function(done) {
    var page_util = require(__dirname + '/../lib/liquid/page-util');
    var jsonData = require(testResourceDir + '/ticket_page_params.json');
    var res = page_util.getParams(jsonData);
    var str = require(__dirname + '/../test-res/tkt-page-op.json');
    expect(JSON.stringify(res)).equal(JSON.stringify(str));
    done();
  });

  it('should get contact page params', function(done) {
    var page_util = require(__dirname + '/../lib/liquid/page-util');
    var jsonData = require(testResourceDir + '/contact_page_params.json');
    var res = page_util.getParams(jsonData);
    var str = require(__dirname + '/../test-res/contact-page-op.json');
    expect(JSON.stringify(res)).equal(JSON.stringify(str));
    done();
  });

  it('should check allowed pages', function(done) {
    var page_util = require(__dirname + '/../lib/liquid/page-util');
    var jsonData = require(testResourceDir + '/ticket_page_params.json');
    expect(page_util.allowedPage(jsonData)).equal(true);
    done();
  });
});
