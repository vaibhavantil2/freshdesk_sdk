/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var validationConst = require('./constants').validationContants;
var path = require('path');
var _ = require('underscore');
var fileUtil = require('../utils/file-util');

var MAX_ASSET_COUNT = 200;
var MAX_INDVL_ASSET_SIZE = 2000000; // size in bytes. 2000000 bytes = 2 MB.
var ALLOWED_EXTNS = [".jpg", ".jpeg", ".png", ".gif", ".css", ".js"];

module.exports = {
  validate: function() {
    var assetsFolderPath = process.cwd() + '/assets/';
    var assetFiles = fileUtil.readDir(assetsFolderPath);
    assetFiles = assetFiles.filter(fileUtil.removeJunkFiles);
    var errMsgs = []

    //Max asset count check
    if (assetFiles.length > MAX_ASSET_COUNT) {
      errMsgs.push("Max asset count exceeded: " + assetFiles.length);
    }

    for (var i in assetFiles) {
      var filePath = assetsFolderPath + assetFiles[i];
      var fileExtType = path.extname(assetFiles[i]);
      var fileStat = fileUtil.statFile(filePath);

      // Dir check:
      if (fileStat.isDirectory()) {
        errMsgs.push("Directory not allowed inside assets/': " + assetFiles[i]);
      }

      //Extension type check
      if (!_.contains(ALLOWED_EXTNS, fileExtType.toLowerCase())) {
        errMsgs.push("Unsupported asset type: assets/" + assetFiles[i]);
      }

      //Asset size check
      if (fileStat.size > MAX_INDVL_ASSET_SIZE) {
        errMsgs.push("Asset exceeds threshold size: assets/" + assetFiles[i]);
      }
    }
    return errMsgs;
  },

  validationType: [validationConst.PRE_PKG_VALIDATION]
};
