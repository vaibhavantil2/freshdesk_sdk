/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

var expect = require('chai').expect;
var helper = require('./helper');
var fs   = require('fs');

describe("test pack", function(){
  before(function(){
    process.chdir(projectDir['name']);
  });

  it("should generate the package in dist/ dir.", function(done){
    require(__dirname + '/../lib/cli-pack').run();
    setTimeout(function(){
      var ns = require(__dirname + '/../lib/ns-resolver');
      fs.accessSync(projectDir['name'] + '/dist/'+ ns.getRootFolder() + '.zip');
      done();
    }, 200);
  });

  it("should clean dirs", function(done) {
    require(__dirname + '/../lib/cli-clean').run();
    done();
  }); 
});
