/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var expect = require('chai').expect;
var helper = require('./helper');
var mfValidate;
var mf;
var fs = require('fs-extra');


describe('manifest validate', function() {
  before(function() {
    process.chdir(projectDir['name']);
    mf = require(sdkDir + '/lib/manifest');
    mfValidate = require(sdkDir +
      '/lib/validations/manifest-validation');
  });

  it('should succeed', function(done) {
    expect([]).eql(mfValidate.validate());
    done();
  });

  it('should fail', function(done) {
    var srcmfFile = testResourceDir + '/manifest_no_pages.yml';
    var destmfFile = projectDir['name'] + '/manifest.yml';
    fs.copySync(srcmfFile, destmfFile);
    mf.reload();
    expect('pages not specified in manifest.yml.').eql(mfValidate.validate()[
      0]);
    done();
  });

  it('should fail', function(done) {
    var srcmfFile = testResourceDir + '/manifest_invalid_pg.yml';
    var validsrcmFile = testResourceDir + '/manifest_valid.yml';
    var destmfFile = projectDir['name'] + '/manifest.yml';
    fs.copySync(srcmfFile, destmfFile);
    mf.reload();
    expect('Invalid page(s) mentioned in manifest.yml: admin.').eql(
      mfValidate.validate()[0]);
    fs.copySync(validsrcmFile, destmfFile);
    mf.reload();
    done();
  });

});
