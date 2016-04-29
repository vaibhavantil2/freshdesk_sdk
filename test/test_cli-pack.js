var expect = require('chai').expect;
var fs   = require('fs');
var os = require('os');

describe("test pack", function(){
  it("should generate the package in dist/ dir.", function(done){
    process.chdir(process.cwd() + "/test-res/plug");
      require(__dirname + '/../lib/cli-pack').run();
      setTimeout(function(){
        fs.accessSync(process.cwd() + '/dist/freshapps_sdk.plg');
        done();
      },50);

  });
  it("should clean dirs", function(done) {
    require(__dirname + '/../lib/cli-clean').run();
    done();
  }); 
});
