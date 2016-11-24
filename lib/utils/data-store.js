/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict';
var Storage = require('node-storage');
var localStore = new Storage(`${process.cwd()}/.frsh/localstore`);

module.exports = {
  store: function(key, data) {
    localStore.put(key, data);
    return {"Created": true};
  },

  fetch: function(key) {
    return localStore.get(key);
  },

  delete: function(key) {
    localStore.remove(key);
    return {"Deleted": true};
  }
};
