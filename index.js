#!/usr/bin/env node

"use strict";

var pjson = require(__dirname + '/package.json');
global.pjson = pjson;
var validationConst = require( __dirname + '/lib/constants').validationContants;

// Cli Parsing:

// 1. Registering Cli commands
var Program = require('wiz-cliparse');
var prg = new Program('frsh',
  'Fresh SDK.',
  '[global-options] [command] [command-options] [arguments]');

prg.addOpt('v', 'verbose', 'enable verbose output.');
prg.addOpt('x', 'exception', 'display exception trace.');

var initCmd = prg.addCmd('init',
  'create a new project.',
  '[folder]',
  'When [folder] is not given, CWD (if empty) is used to init.');
prg.addCmd('info', 'display information about the project.');
prg.addCmd('run', 'local testing.');
prg.addCmd('validate', 'run all validations.');
prg.addCmd('pack', 'pack for distribution.');
prg.addCmd('clean', 'removes dist/ and work/ dirs.');
prg.addCmd('version', 'prints the version of SDK.');

prg.addHelp();

// 2. Parse cli options:
try {
  var res = prg.parse();
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
    require(__dirname + '/lib/cli-init').run(res.args[0]);
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
