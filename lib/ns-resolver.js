/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

var path = require('path');

const PACKAGE_EXTENSION = ".zip"

function rootFolderName() {
  return process.cwd().split(path.sep).pop();
}

function normalize(folderName) {
  var MAX_CHARS = 6;
  var processed = folderName.toLowerCase().replace(/[^a-zA-Z0-9]/g, "_");
  if (processed.length > MAX_CHARS) {
    processed = processed.substring(0, MAX_CHARS);
  }
  return "fa_" + processed + "_101";
}

module.exports = {
  getNamespace: function() {
    folderName = rootFolderName();
    return { "app_id" : normalize(folderName)};
  },

  getRootFolder: function () {
    return rootFolderName();
  },

  pkgExt: PACKAGE_EXTENSION
}