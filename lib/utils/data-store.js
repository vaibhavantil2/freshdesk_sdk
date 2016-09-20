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
