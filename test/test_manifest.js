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
    expect(mfValidate.validate()).eql([]);
    done();
  });

  it('should fail', function(done) {
    var srcmfFile = testResourceDir + '/manifest_no_pages.yml';
    var destmfFile = projectDir['name'] + '/manifest.yml';
    fs.copySync(srcmfFile, destmfFile);
    mf.reload();
    expect(mfValidate.validate()[0]).eql('pages not specified in manifest.yml.');
    done();
  });

  it('should fail', function(done) {
    var srcmfFile = testResourceDir + '/manifest_invalid_pg.yml';
    var validsrcmFile = testResourceDir + '/manifest_valid.yml';
    var destmfFile = projectDir['name'] + '/manifest.yml';
    fs.copySync(srcmfFile, destmfFile);
    mf.reload();
    expect(mfValidate.validate()[0]).eql('Invalid page(s) mentioned in manifest.yml: admin.');
    fs.copySync(validsrcmFile, destmfFile);
    mf.reload();
    done();
  });

  it('should fail - invalid feature', function(done) {
    var srcmfFile = testResourceDir + '/manifest_invalid_feature.yml';
    var validsrcmFile = testResourceDir + '/manifest_valid.yml';
    var destmfFile = projectDir['name'] + '/manifest.yml';
    fs.copySync(srcmfFile, destmfFile);
    mf.reload();
    expect('Invalid feature mentioned in manifest.yml: featurex.').eql(
      mfValidate.validate()[0]);
    fs.copySync(validsrcmFile, destmfFile);
    mf.reload();
    done();
  });
});
