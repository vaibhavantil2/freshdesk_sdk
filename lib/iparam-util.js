'use strict';

var yaml = require('js-yaml');
var _ = require('underscore');
var fileUtil = require('./file-util');
var eh = require("./err");

var testDataFile = 'iparam_test_data.yml';
var cwd = process.cwd();

module.exports= {

  getConfig: function getConfig(lang) {
    return getYmlContent(`iparam_${lang}.yml`);
  },

  getIparamLangs: function getIparamLangs() {
    var languages = [];
    var validParamFiles = listOfIparamFiles();
    for (var i in validParamFiles) {
      languages.push(validParamFiles[i].match('iparam_(.*).yml')[1]);
    }
    return languages;
  },

  getAllIparamKeys: function getAllIparamKeys() {
    var iparamFiles = listOfIparamFiles();
    var iparamKeys = [];
    for (let file of iparamFiles) {
      let content = getYmlContent(file);
      iparamKeys.push(flattenIparamKeys(content));
    }
    return iparamKeys;
  },

  getValuesForLocalTesting: function getValuesForLocalTesting() {
    return getYmlContent(testDataFile);
  }
}


function listOfIparamFiles() {
  var iparamFiles = [];
  var files = fileUtil.readDir(cwd + '/config');
  for (let i in files) {
    if (files[i].match(/^iparam_[a-z]{2}(\-[A-Z]{2})?\.yml/g)) {
      iparamFiles.push(files[i]);
    }
  }
  return iparamFiles;
}

function getYmlContent(fileName) {
  try {
    var data = fileUtil.readFile(cwd + `/config/${fileName}`);
    return yaml.safeLoad(data);
  }
  catch (err) {
    eh.error(new Error(`Error in reading file ${fileName}`), 1);
  }
}

function flattenIparamKeys(data) {
  var ignoredKeys = ["default_value", "required"];
  for (let key in data) {
    data[key] = _.reject(_(data[key]).keys(), function(val) {return _.contains(ignoredKeys, val)});
  }
  return data;
}
