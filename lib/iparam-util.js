"use strict";
var yaml = require('js-yaml');
var _ = require('underscore');
var manifest = require(__dirname + "/manifest");
var fileUtil = require(__dirname + '/file-util');
var eh = require(__dirname + "/err");
var testDataFile = 'test_data.yml';
var cwd = process.cwd();

module.exports= {

  getConfig: function getConfig(lang){
    return getYmlContent(`iparam_${lang}.yml`);
  },

  getIparamLangs: function getIparamLangs(){
    var languages = [];
    var validParamFiles = listOfIparamFiles();
    for(var i in validParamFiles){
      languages.push(validParamFiles[i].match('iparam_(.*).yml')[1]);
    }
    return languages;
  },

  getAllIparamKeys: function getAllIparamKeys(){
    var iparamFiles = listOfIparamFiles();
    var iparamKeys = [];
    for(let file of iparamFiles){
      let content = getYmlContent(file);
      iparamKeys.push(flattenIparamKeys(content));
    }
    return iparamKeys;
  },

  getValuesForLocalTesting: function getValuesForLocalTesting() {
    return getYmlContent(testDataFile);
  }
}


function listOfIparamFiles(){
  var iparamFiles = [];
  var files = fileUtil.readDir(cwd + '/iparam');
  for(var i in files){
    if(files[i].match('iparam_(.*).yml'))
      iparamFiles.push(files[i]);
  }
  return iparamFiles;
}

function getYmlContent(file_name){
  try {
    var data = fileUtil.readFile(cwd + `/iparam/${file_name}`);
    return yaml.safeLoad(data);
  }
  catch(err){
    eh.error(new Error(`Error in reading file ${file_name}`), 1);
  }
}

function flattenIparamKeys(data){
  var ignoredKeys = ["default_value", "required"];
  for(let key in data){
    data[key] = _.reject(_(data[key]).keys(), function(val) {return _.contains(ignoredKeys, val)});
  }
  return data;
}
