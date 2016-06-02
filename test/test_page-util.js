/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

var helper = require('./helper');
var fs = require('fs-extra');
var _ = require('underscore');

describe('page util test', function(){
  before(function(){
    process.chdir(projectDir['name']);
  });

  it('should get ticket page params', function(done) {
    var page_util = require(__dirname + '/../lib/page-util');
    var data = fs.readFileSync(testResourceDir + '/ticket_page_params.json');
    var jsonData = JSON.parse(data.toString());
    var res = page_util.getParams(jsonData);
    var str = fs.readFileSync(__dirname + '/../test-res/tkt-page-op.json');
    _.isEqual(str, JSON.stringify(res));
    done();
  });

  it('should get contact page params', function(done) {
    var page_util = require(__dirname + '/../lib/page-util');
    var data = fs.readFileSync(testResourceDir + '/contact_page_params.json');
    var jsonData = JSON.parse(data.toString());
    var res = page_util.getParams(jsonData);
    var str = fs.readFileSync(__dirname + '/../test-res/tkt-page-op.json');
    _.isEqual(str, JSON.stringify(res));
    done();
  });

  it('should check allowed pages', function(done) {
    var page_util = require(__dirname + '/../lib/page-util');
    var data = fs.readFileSync(testResourceDir + '/ticket_page_params.json');
    var jsonData = JSON.parse(data.toString());
    page_util.allowedPage(jsonData);
    done();
  });
});
