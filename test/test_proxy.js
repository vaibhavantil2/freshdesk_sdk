/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var request = require('supertest');
var helper = require('./helper');
var fs = require('fs-extra');
var proxymFile = testResourceDir + '/manifest_proxy.yml';
var destmfFile = projectDir['name'] + '/manifest.yml';

describe('Oauth test', function () {
  this.timeout(15000);
  var server;

  beforeEach(function () {
    mf = require(sdkDir + '/lib/manifest');
    process.chdir(projectDir['name']);
    server = require('../lib/cli/run').run();
  });

  afterEach(function () {
    server.close();
  });

  it('should make request', function(done) {
    fs.copySync(proxymFile, destmfFile);
    mf.reload();
    var payload = {
      "action": "execute",
      "data":
      {
        "method": "get",
        "url": "https://www.google.com"
      }
    }
    request(server)
      .post('/dprouter')
      .send(payload)
      .set({
        "MKP-ROUTE": "proxy"
      })
      .expect(200,done);
  });
});
