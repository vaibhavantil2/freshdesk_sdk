#!/usr/bin/env node

"use strict";

var Program = require('wiz-cliparse');

var pjson = require(__dirname + '/package.json');
global.pjson = pjson;
var validationConst = require( __dirname + '/lib/constants').validationContants;

// Cli Parsing:

// Registering Cli commands
var prg = new Program('frsh', '[global-options] [command] [command-options] [arguments]', 'Fresh SDK.');

prg.addOpt('v', 'verbose', 'enable verbose output.');
prg.addOpt('x', 'exception', 'display exception trace.');

var initCmd = prg.addCmd('init',
  '<type> [folder]', 'create a new project.',
  'Supported <type>: plug. When [folder] is not given, CWD (if empty) is used to init.');
prg.addCmd('info', null, 'display information about the project.');
var runCmd = prg.addCmd('run', null, 'local testing.');
runCmd.addOpt('h', 'help')
prg.addCmd('validate', null, 'run all validations.');
prg.addCmd('pack', null, 'pack for distribution.');
prg.addCmd('clean', null, 'removes build/ and dist/ dirs.');
prg.addCmd('version', null, 'prints the version of SDK.');

prg.addHelp();

// Parsing for commands:
// Fetching arguments from cli
var cliArg = process.argv.slice(2);
try {
  var res = prg.parseSync(cliArg);
}
catch(err) {
  console.error(`Cli parse error: ${err}`);
  process.exit(1);
}

// Setting the global options
if(res.gopts) {
  if(res.gopts.has('v')) {
    global.verbose = true;
  }
  if(res.gopts.has('x')) {
    global.trace = true;
  }
}

// Print and exit if command is help:
if(res.gopts.has('h') || res.cmd === 'help') {
  prg.printHelp(res);
  process.exit();
}

// Other commands:
switch(res.cmd) {

  case 'init':
    require(__dirname + '/lib/cli-init').run(res.args[0], res.args[1]);
    break;

  case 'info':
    require(__dirname + '/lib/cli-info').run();
    break;

  case 'run':
    require(__dirname + '/lib/cli-run').run();
    break;

  case 'validate':
    var validationStatus = require(__dirname + '/lib/cli-validate').run(validationConst.PRE_PKG_VALIDATION);
    if(validationStatus) {
      if(global.verbose) {
        console.log("No failures observed when running validations.");
      }
    }
    else {
      console.log("Validation failed with above errors.");
    }
    break;

  case 'pack':
    require(__dirname + '/lib/cli-pack').run();
    break;

  case 'clean':
    require(__dirname + '/lib/cli-clean').run();
    break;

  case 'version':
    console.log(pjson.version);
    break;

  default:
    prg.printHelp(res);
}
