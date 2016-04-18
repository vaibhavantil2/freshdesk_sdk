var scss = require(__dirname + '/../lib/scss');
var expect = require('chai').expect;
var fs   = require('fs');
var os = require('os');

describe("scss compile", function(){
  var input = __dirname + '/src.scss';
  var oput = os.tmpdir() + '/src.css';

  it("should compile", function(done){
    scss.compile(input, oput);
    var result = fs.readFileSync(oput, 'utf8');
    var exp = fs.readFileSync(__dirname + '/src.css', 'utf8');
    expect(exp).equals(result);
    fs.unlinkSync(oput);
    done();
  });
});
