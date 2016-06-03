/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

var manifest = require('../manifest');

module.exports = {
  run: function() {
    console.log('# Project details:');
    console.log(`  Platform version: v${manifest.pfVersion}`);
    console.log();
    console.log('# Running on:');
    console.log(`  Sdk version: v${global.pjson.version}`);
    console.log(`  Running on Node version: ${process.version}`);
  }
};