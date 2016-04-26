#!/usr/bin/env node

"use strict";

var Program = require('wiz-cliparse');

var pjson = require(__dirname + '/package.json');
global.pjson = pjson;
var validationConst = require( __dirname + '/lib/constants').validationContants;

// Cli Parsing:
// Fetching arguments from cli
var cliArg = process.argv.slice(2);

// Registering Cli commands
var prg = new Program('frsh', '[global-options] [command] [command-options] [arguments]');
prg.addHelp();

prg.addOpt('v', 'verbose', 'enable verbose output.');
prg.addOpt('x', 'exception', 'display exception trace');

var initCmd = prg.addCmd('init', 'create a new project.');
initCmd.addOpt('h', 'help', 'Usage: \n frsh [global-options] init <type> [dir]\n type can be "plug"');
prg.addCmd('info', 'display information about the project.');
var runCmd = prg.addCmd('run', 'local testing.');
runCmd.addOpt('h', 'help')
prg.addCmd('validate', 'run all validations.');
prg.addCmd('pack', 'pack for distribution.');
prg.addCmd('clean', 'cleans build/ and dist/ dirs.');
prg.addCmd('version', 'prints the version of SDK.');

// Pasring for commands
var res = prg.parseSync(cliArg);

// Setting the global options
if(res.gopts) {
  if(res.gopts.has('v')) {
    global.verbose = true;
  }
  if(res.gopts.has('x')) {
    global.trace = true;
  }
}

// Print and exit if command is help
if(res.gopts.has('h') || res.cmd === 'help') {
  prg.printHelp(res);
}
// Other commands.
switch(res.cmd) {

  case 'init':
    if(res.opts.has('h')) {
      console.log("Usage:\nfrsh init <type> [dir]\n  <type> is plug\n  " +
       "[dir] (optional) - dir to init the project, initializes in current working dir if no option is specified");
      break;
    }
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
