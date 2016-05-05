var tmp = require('tmp');
var rmdir = require('rimraf');
global.projectDir = "";
global.sdkDir = "";
global.testResourceDir = "";

(function(){
  projectDir = tmp.dirSync();
  sdkDir = process.cwd();
  testResourceDir = process.cwd() + "/test-res";
  require(__dirname + '/../lib/cli-init').run('plug', projectDir['name']);
})();
