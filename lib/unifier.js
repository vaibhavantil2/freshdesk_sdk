/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

var scss = require("./liquid/scss");
var eh = require('./utils/err');
var fileUtil = require('./utils/file-util');

var cwd = process.cwd();
var tmpFilePath = cwd + "/work/tmp.scss";
var cssFilePath = cwd + "/work/app.css";

function appendStyleTag(input) {
  return "<style>\n" + input + "\n</style>\n";
}

function appendScriptTag(input) {
  return "<script>\n" + input + "\n</script>";
}

function appendLocalTestingTag(input) {
  return "Localtesting.run(`" + input + "`);"
}

function appendFreshappExecTag(input, ctx) {
  var scriptData = "var {{app_id}} = " + input;
  if (ctx === 'build') {
    return scriptData +
    `Freshapp.exec({
      'content' : {{app_id}},
      'appName' : '{{app_id}}'
    })
    `;
  }
  else if (ctx === 'run') {
    return appendLocalTestingTag(scriptData);
  }
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

function performUnification(ctx) {
  try {
    fileUtil.ensureFile(tmpFilePath);
    writeCssToTmpFile();
    compileCss();
    return combinedData(ctx);
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

function combinedData(ctx) {
  var htmlContent = appendAppIDTagForHtml(fileUtil.readFile(cwd + "/app/template.html"));
  var cssContent = replaceAppID(fileUtil.readFile(cssFilePath));
  cssContent = appendStyleTag(cssContent);
  var jsContent = appendFreshappExecTag(fileUtil.readFile(cwd + "/app/app.js"), ctx);
  jsContent = appendScriptTag(jsContent);
  return (cssContent + htmlContent + jsContent);
}

module.exports = {
  unify: function(ctx) {
    return performUnification(ctx);
  }
};
