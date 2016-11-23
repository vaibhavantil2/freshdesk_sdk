/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var validationConst = require('./constants').validationContants;
var fileUtil = require('../utils/file-util');

module.exports = {
  validate: function() {
    var errMsgs = [];
    var configFolderPath = process.cwd() + '/config/';
    var configFiles = fileUtil.readDir(configFolderPath);
    configFiles = configFiles.filter(fileUtil.removeJunkFiles);
    var invalidConfigFiles = [];
    for (var file of configFiles) {
      if (!(file.endsWith(".yml"))) {
        invalidConfigFiles.push(file);
      }
    }
    if (invalidConfigFiles.length > 0) {
      errMsgs.push(`Config directory has invalid file(s) - ${invalidConfigFiles}`);
    }
    return errMsgs;
  },

  validationType: [validationConst.PRE_PKG_VALIDATION]
};
