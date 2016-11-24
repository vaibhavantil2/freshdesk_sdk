/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

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
    var invalidIparamFile = testResourceDir + '/invalid_iparam_en.yml';
    var validIparamFile = testResourceDir + '/valid_iparam_en.yml';
    var destIparamFile = projectDir['name'] + '/config/iparam_en.yml';
    fs.copySync(invalidIparamFile, destIparamFile);
    expect(["Invalid type 'textd' found in iparam_en.yml."]).eql(iparamValidator.validate());
    fs.copySync(validIparamFile, destIparamFile);
    done();
  })

});
