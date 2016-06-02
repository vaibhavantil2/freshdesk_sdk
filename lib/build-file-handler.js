/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

var eh = require('./err');
var contentUnifier = require('./unifier');
var fileUtil = require('./file-util');

var cwd = process.cwd();
var buildDirPath = cwd + '/build';
var buildFilePath = cwd + "/build/index.html";
var buildErrorMsg = "Error while generating build file.";

module.exports = {
  genBuildFile: function genBuildFile() {
    try {
      var unifiedData = contentUnifier.unify();
      fileUtil.ensureFile(buildFilePath);
      fileUtil.writeFile(buildFilePath, unifiedData);
      return;
    }
    catch (err) {
      eh.error(new Error(buildErrorMsg));
    }
  },

  delBuildDir: function() {
      return fileUtil.deleteFile(buildDirPath);
  }
}