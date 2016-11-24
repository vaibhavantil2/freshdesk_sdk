/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var scss = require(__dirname + '/../lib/liquid/scss');
var expect = require('chai').expect;
var fs   = require('fs');
var os = require('os');

describe("scss compile", function(){
  var input = __dirname + '/../test-res/src.scss';
  var oput = os.tmpdir() + '/src.css';

  it("should compile", function(done){
    scss.compile(input, oput);
    var result = fs.readFileSync(oput, 'utf8');
    var exp = fs.readFileSync(__dirname + '/../test-res/src.css', 'utf8');
    expect(result).equals(exp);
    fs.unlinkSync(oput);
    done();
  });
});
