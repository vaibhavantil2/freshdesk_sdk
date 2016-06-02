/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

var scss = require("./scss");
var eh = require('./err');
var fileUtil = require('./file-util');

var cwd = process.cwd();
var tmpFilePath = cwd + "/work/tmp.scss";
var cssFilePath = cwd + "/work/app.css";

function appendStyleTag(input) {
  return "<style>\n" + input + "\n</style>\n";
}

function appendScriptTag(input) {
  return "<script>\n" + input + "\n</script>";
}

function appendFreshappRunTag(input) {
  return "Freshapp.run(function() {\n" +
  "  var $myApp = document.querySelector('#{{app_id}}');\n" +
  "  var {{app_id}} = " + input + "\n{{app_id}}.initialize(); \n});\n";
}

function appendAppIDTagForScss(input) {
  return "#__app_id__ {\n" + input + "\n}\n";
}

function appendAppIDTagForHtml(input) {
  return '<div id="{{app_id}}">\n' + input + '\n</div>\n';
}

function replaceAppID(input) {
  return input.replace(/__app_id__/g, '{{app_id}}')
}

function compileCss() {
  fileUtil.ensureFile(tmpFilePath);
  scss.compile(tmpFilePath, cssFilePath);
}

function performUnification() {
  try {
    fileUtil.ensureFile(tmpFilePath);
    writeCssToTmpFile();
    compileCss();
    return combinedData();
  }
  catch (err) {
    eh.error(new Error(err.message));
  }
}

function writeCssToTmpFile() {
  var scssContent = fileUtil.readFile(cwd + "/app/style.scss");
  var toWrite = appendAppIDTagForScss(scssContent);
  fileUtil.writeFile(tmpFilePath, toWrite);
  return;
}

function combinedData() {
  var htmlContent = appendAppIDTagForHtml(fileUtil.readFile(cwd + "/app/template.html"));
  var cssContent = replaceAppID(fileUtil.readFile(cssFilePath));
  cssContent = appendStyleTag(cssContent);
  var jsContent = appendFreshappRunTag(fileUtil.readFile(cwd + "/app/app.js"));
  jsContent = appendScriptTag(jsContent);
  return (cssContent + htmlContent + jsContent);
}

module.exports = {
  unify: function() {
    return performUnification();
  }
};
