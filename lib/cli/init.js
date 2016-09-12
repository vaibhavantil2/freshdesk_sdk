/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var fs = require('fs-extra');
var emptyDir = require('empty-dir');
var _ = require('underscore');

var gitignore = ['dist', 'work', 'build', '.frsh'].join("\n");
const templatesAllowed = ['default'];

module.exports = {
  run: function(dir, template) {
    if (!template) {
      template = 'default';
    }
    if (!_.contains(templatesAllowed, template)) {
      console.error(`init template ${template} is invalid!`);
      process.exit(1);
    }
    var prjDir = dir? dir: process.cwd();
    if (!fs.existsSync(prjDir)) {
      fs.mkdirSync(prjDir);
      if (global.verbose) {
        console.log("Created project directory: " + prjDir);
      }
    }
    if (!emptyDir.sync(prjDir)) {
      console.error('Could not initialize as the project directory is not empty.');
      process.exit(1);
    }

    // Copy from project template:
    fs.copySync(__dirname + '/../../template/' + template, prjDir);
    fs.writeFileSync(`${prjDir}/.gitignore`, gitignore + "\n");
    if (global.verbose) {
      console.log('Initialized project directory: ' + prjDir);
    }
  }
};
