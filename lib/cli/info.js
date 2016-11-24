/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';

var manifest = require('../manifest');

/*
  Print platform, sdk & node versions.
*/

module.exports = {
  run: function() {
    console.log('# Project details:');
    console.log(`  Platform version: v${manifest.pfVersion}`);
    console.log();
    console.log('# Running on:');
    console.log(`  SDK version: v${global.pjson.version}`);
    console.log(`  Node version: ${process.version}`);
  }
};