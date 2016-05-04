var expect = require('chai').expect;
var tmp = require('tmp');
var helper = require('./helper');
var mfValidate;
var mf;
var fs = require('fs-extra');


describe('manifest validate', function(){
  before(function(){
    process.chdir(projectDir['name']);
    mf = require(sdkDir + '/lib/manifest');
    mfValidate = require(sdkDir + '/lib/validations/manifest-validation');
  })

  it('should succeed', function(done){
    expect([]).eql(mfValidate.validate());
    done();
  });

  it('should fail', function(done){
    var srcmfFile = testResourceDir + '/manifest1.yml';
    var destmfFile = projectDir['name'] + '/manifest.yml';
    fs.copySync(srcmfFile, destmfFile);
    mf.reload();
    expect('pages not specified in manifest.yml').eql(mfValidate.validate()[0]);
    done();
  });

  it('should fail', function(done){
    var srcmfFile = testResourceDir + '/manifest2.yml';
    var destmfFile = projectDir['name'] + '/manifest.yml';
    fs.copySync(srcmfFile, destmfFile);
    mf.reload();
    expect('Invalid page(s) mentioned in manifest.yml: admin').eql(mfValidate.validate()[0]);
    done();
  });

});
