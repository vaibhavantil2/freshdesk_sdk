/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var crypto = require('crypto');
var eh = require('./err');
var fileUtil = require('./file-util');

var cwd = process.cwd();
var digestFile = cwd + "/digest.md5";
var htmlFile = cwd + "/app/template.html";
var scssFile = cwd + "/app/style.scss";
var jsFile = cwd + "/app/app.js";
var buildFile = cwd + "/build/index.html";
var manifestFile = cwd + "/manifest.yml";
var digestErrorMsg = "Error while generating digest file.";

module.exports = {
  genDigestFile: function() {
    try {
      var htmlContent = fileUtil.readFile(htmlFile);
      var scssContent = fileUtil.readFile(scssFile);
      var jsContent = fileUtil.readFile(jsFile);
      var buildContent = fileUtil.readFile(buildFile);
      var combinedContent = htmlContent + scssContent + jsContent + buildContent + manifestFile;
      var digest = crypto.createHash('md5').update(combinedContent).digest("hex");
      fileUtil.writeFile(digestFile, digest);
    }
    catch (err) {
      eh.error(new Error(digestErrorMsg));
    }
  },

  delDigestFile: function() {
    fileUtil.deleteFile(digestFile);
  }
};
