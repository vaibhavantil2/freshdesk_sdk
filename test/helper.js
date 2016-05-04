var tmp = require('tmp');
global.projectDir = "";
global.sdkDir = "";
global.testResourceDir = "";

(function(){
  projectDir = tmp.dirSync();
  sdkDir = process.cwd();
  testResourceDir = process.cwd() + "/test-res/plug";
  require(__dirname + '/../lib/cli-init').run('plug', projectDir['name']);
})();