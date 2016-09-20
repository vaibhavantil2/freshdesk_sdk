var expect = require('chai').expect;
var helper = require('./helper');
var fs = require('fs-extra');
var iparamValidator;

describe('iparam validate', function() {
  before(function() {
    process.chdir(projectDir['name']);
    iparamValidator = require('../lib/validations/iparam-validation')
  });

  it('should validate keys', function(done) {
    expect([]).eql(iparamValidator.validate());
    done();
  });

  it('should fail', function(done) {
    var srcmfFile = testResourceDir + '/invalid_iparam_en.yml';
    var destmfFile = projectDir['name'] + '/config/iparam_en.yml';
    fs.copySync(srcmfFile, destmfFile);
    expect(["Invalid type 'textd' found in iparam_en.yml."]).eql(iparamValidator.validate());
    done();
  })

});
