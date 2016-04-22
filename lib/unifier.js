// TBD: https://github.com/freshdesk/freshapps_devportal/issues/893

var fs = require('fs-extra');
var scss = require(__dirname + "/scss");
var async = require('async');
var cwd = process.cwd();
var eh = require(__dirname + '/err');
var tmpFilePath = cwd + "/work/tmp.scss";
var cssFilePath = cwd + "/work/app.css";
var manifest;

function readFile(file, callback) {
  fs.readFile(file, manifest.mf['source-charset'], (err, data) => {
    if (err) {
      eh.error(new Error("Error while reading " + file), 1);
    }
    callback(data);
  });
}

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

function getHtmlContent(callback) {
  readFile(cwd + "/app/template.html", function(data) {
    return callback(data.toString());
  });
}

function getScssContent(callback) {
  readFile(cwd + "/app/style.scss", function(data) {
    return callback(data.toString());
  });
}

function getJsContent(callback) {
  readFile(cwd + "/app/app.js", function(data) {
    return callback(data.toString());
  });
}

function getCssContent(callback) {
  readFile(cssFilePath, function(data) {
    return callback(data.toString());
  });
}

function compileCss(callback) {
  fs.ensureFile(cssFilePath, function(err) {
    if(err) return callback(err);
    try{
      scss.compile(tmpFilePath, cssFilePath);
    }
    catch(err){
      return callback(err)
    }
    return callback(null);
  });
}

function createTmpFile(callback){
  fs.ensureFile(tmpFilePath, function (err) {
    if(err)
      eh.error(new Error("Error while creating temp file."), 1);
    callback(null);
  });
}

function performUnification(callback){
  async.waterfall([
    createTmpFile,
    writeCssToTmpFile,
    compileCss,
    combineHtmlContent,
    combineCssContent,
    combineJSContent
  ], function (err, result) {
    if(err)
      return callback(err);
    return callback(result);
  });
}

function writeCssToTmpFile(callback){
  getScssContent(function(data) {
    var toWrite = appendAppIDTagForScss(data);
    fs.writeFile(tmpFilePath, toWrite, function(err){
      if(err)
        eh.error(new Error("Error while writing tmp file"), 1);
      callback(null);
    })
  });
}

function combineHtmlContent(callback){
  getHtmlContent(function(data){
    callback(null, appendAppIDTagForHtml(data));
  });
}

function combineCssContent(combinedData, callback){
  getCssContent(function(data){
    callback(null, appendStyleTag(replaceAppID(data)) + combinedData);
  });
}

function combineJSContent(combinedData, callback){
  getJsContent(function(data){
    callback(null, combinedData + appendScriptTag(appendFreshappRunTag(data)));
  });
}

exports.unify = function(mf, callback) {
  manifest = mf;
  performUnification(function(data){
    return callback(data);
  });
}