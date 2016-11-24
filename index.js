#!/usr/bin/env node

/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var pjson = require('./package.json');
global.pjson = pjson;
var validationConst = require('./lib/validations/constants').validationContants;

if (Number(process.versions.node.split(".")[0]) < 4) {
  console.log('Node version of 4.x.x and above is required to run SDK. ' + process.versions.node + ' found.');
  process.exit(0);
}
// Cli Parsing:

// 1. Registering Cli commands
var Program = require('wiz-cliparse');
var prg = new Program('frsh',
  'Freshdesk SDK.',
  '[global-options] [command] [command-options] [arguments]');

prg.addOpt('v', 'verbose', 'enable verbose output.');
prg.addOpt('x', 'exception', 'display exception trace.');

var cmdInit = prg.addCmd('init',
  'create a new project.',
  '[folder]',
  'When [folder] is not given, CWD (if empty) is used to init.');

cmdInit.addOpt('f', 'feature', 'Features to be added to the project.', {
  hasArg: true
});

var cmdUpdate = prg.addCmd('update',
  'update existing project.',
  '[folder]',
  'When [folder] is not given, CWD (if empty) is used to init.');
cmdUpdate.addOpt('a', 'addfeature', 'Add features to existing project.', {
  hasArg: true,
  multiArg: true
})
cmdUpdate.addOpt('r', 'removefeature', 'Remove features from existing project.', {
  hasArg: true,
  multiArg: true
})

prg.addCmd('info', 'display information about the project.');
prg.addCmd('run', 'local testing.');
prg.addCmd('validate', 'run all validations.');
prg.addCmd('pack', 'pack for distribution.');
prg.addCmd('clean', 'removes dist/ and work/ dirs.');
prg.addCmd('version', 'prints the version of SDK.');

prg.addHelp();

// 2. Parse cli options:
var res = null;
try {
  res = prg.parse();
}
catch (err) {
  console.error('Cli parse error: ' + err);
  process.exit(1);
}

// Setting the global options
if (res.gopts) {
  if (res.gopts.has('v')) {
    global.verbose = true;
  }
  if (res.gopts.has('x')) {
    global.trace = true;
  }
}

// Print and exit if command is help:
if (res.gopts.has('h') || res.cmd === 'help') {
  prg.printHelp(res);
  process.exit();
}

// Other commands:
switch (res.cmd) {

  case 'init':
    var template = res.opts.has('f')? res.optArg.get('f'): 'default';
    require('./lib/cli/init').run(res.args[0], template);
    break;

  case 'info':
    require('./lib/cli/info').run();
    break;

  case 'run':
    require('./lib/cli/run').run();
    break;

  case 'validate':
    var validationStatus = require('./lib/cli/validate').run(
      validationConst.PRE_PKG_VALIDATION);
    if (validationStatus) {
      if (global.verbose) {
        console.log("Validation successful.");
      }
    }
    else {
      console.log("Validation failed with above errors.");
    }
    break;

  case 'pack':
    require('./lib/cli/pack').run();
    break;

  case 'clean':
    require('./lib/cli/clean').run();
    break;

  case 'version':
    console.log(pjson.version);
    break;

  case 'update':
    var options = {};
    if (res.opts.has('a')) {
      options['add'] = res.optArg.get('a');
    }
    if (res.opts.has('r')) {
      options['remove'] = res.optArg.get('r');
    }
    require('./lib/cli/update').run(options);
    break;

  default:
    prg.printHelp(res);
}
