/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var tmp = require('tmp');
var rmdir = require('rimraf');
global.projectDir = "";
global.sdkDir = "";
global.testResourceDir = "";

(function(){
  projectDir = tmp.dirSync({prefix: 'freshapps_sdk'});
  sdkDir = process.cwd();
  testResourceDir = process.cwd() + "/test-res";
  global.verbose = true;
  require(__dirname + '/../lib/cli/init').run(projectDir['name']);
})();
