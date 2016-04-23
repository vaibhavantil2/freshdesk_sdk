#!/usr/bin/env node

var program = require('commander');

var pjson = require(__dirname + '/package.json');
global.pjson = pjson;

// Cli parsing:
program.version(pjson.version)
  .option('-v, --verbose', 'verbose execution', function() {
    global.verbose = true;
    return true;
  })
  .option('-x, --exception', 'print exception trace', function(){
    global.trace = true;
    return true;
  });

program.command('init <type> [dir]')
  .description('create a new project.')
  .action(function(type, dir){
    require(__dirname + '/lib/cli-init').run(type, dir);
  });

program.command('info')
  .description('display information about the project.')
  .action(function(){
    require(__dirname + '/lib/cli-info').run();
  });

program.command('run')
  .description('local testing.')
  .action(function(){
    require(__dirname + '/lib/cli-run').run();
  });

program.command('validate').description('run all validations.')
    .action(function(){
        require(__dirname + '/lib/cli-validate').run();
    });

program.command('pack')
  .description('pack for distribution.')
  .action(function(){
    require(__dirname + '/lib/cli-pack').run();
  });

program.command('help', null, {isDefault:true})
  .description('display help information.')
  .action(function(env, options){
    program.outputHelp();
  });

program.command('clean')
  .description('cleans build/ and dist/ dirs.')
  .action(function() {
    require(__dirname + '/lib/cli-clean').run();
  });

program.command('*', null, {noHelp:true})
  .action(function(cmd){
    console.error(`Unrecognized command: ${cmd}.`);
    program.outputHelp();
  });

program.parse(process.argv);

// Process no command execution:
if (!process.argv.slice(2).length) {
  program.outputHelp();
}
