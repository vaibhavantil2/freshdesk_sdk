/* 

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is 
bundled with this source code.

*/

var fs = require('fs-extra');
var eh = require("./err");

var RE_IGNORED_FILES = /^\.(.)*$|^\.Spotlight-V100$|\.Trashes$|^Thumbs\.db$|^desktop\.ini$/g;
var charset = 'utf8';

module.exports = {
  ensureFile: function(fileName) {
    try {
      fs.ensureFileSync(fileName);
    }
    catch (err) {
      eh.error(err);
    }
  },

  writeFile: function(file, data) {
    try {
      fs.writeFileSync(file, data);
    }
    catch (err) {
      eh.error(err);
    }
  },

  deleteFile: function(file) {
    try {
      fs.removeSync(file);
    }
    catch (err) {
      eh.error(err);
    }
  },

  readFile: function(file) {
    try {
      return fs.readFileSync(file, charset);
    }
    catch (err) {
      eh.error(err);
    }
  },

  readDir: function(dir) {
    try {
      return fs.readdirSync(dir);
    }
    catch (err) {
      eh.error(err);
    }
  },

  statFile: function(file) {
    try {
      return fs.statSync(file);
    }
    catch (err) {
      eh.error(err);
    }
  },

  fileExists: function(file) {
    try {
      fs.accessSync(file);
    }
    catch (err) {
      return false;
    }
    return true;
  },

  removeJunkFiles: function(file) {
    return !file.match(RE_IGNORED_FILES);
  }

};
