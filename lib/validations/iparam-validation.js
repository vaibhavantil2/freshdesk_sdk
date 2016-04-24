var iParamUtil = require('../iparam-util');
var _ = require('underscore');
var async = require('async');
var validationConst = require('../constants').validationContants;

var VALID_KEYS = ["display_name", "description", "type", "required", "default_value", "options"];
var DROPDOWN = "dropdown";
var TEXT = "text";
var OPTIONS = "options";
var DEFAULT_VALUE = "default_value";
var TYPE = "type";

exports.validate = function () {
  error_msgs = [];
  error_msgs.push(iparamKeysValidator());
  error_msgs.push(dropDownOptionsValidator());
  error_msgs.push(keyConsistencyValidator());
  return error_msgs;
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];

function dropDownOptionsValidator(){
  langs = iParamUtil.getIparamLangs();
  for(var i in langs){
    content = iParamUtil.getConfig(langs[i]);
    if(content)
      return ValidateDropdownKeys(langs[i], content);
  }
}

function ValidateDropdownKeys(lang, langcontent){
  for(var key in langcontent){
    if((langcontent[key][TYPE] == DROPDOWN) && (!Array.isArray(langcontent[key][OPTIONS])))
      return (`Dropdown options for key ${key} in iparam_${lang}.yml is not a list`);
  }
}

function keyConsistencyValidator(){
  iparamKeys = iParamUtil.getAllKeys();
  for(var i =0 ;i < (iparamKeys.length -1); i++){
    if(!_.isEqual(keys[i],keys[i + 1]))
      return ("Inconsistent keys detected among iparam files");
  }
}

function iparamKeysValidator(){
  langs = iParamUtil.getIparamLangs();
  for(var i in langs){
    var configs = iParamUtil.getConfig(langs[i]);
    if(configs)
      return checkValidKeys(langs[i], configs);
  }
}

function checkValidKeys(lang, content){
  for(var key in content){
    if(_.difference(Object.keys(content[key]),VALID_KEYS ).length > 0){
      return (`Invalid keys in iparam_${lang}.yml `);
    }

    if(content[key][TYPE] == TEXT) {
      if (OPTIONS in content[key] || DEFAULT_VALUE in content[key]) {
        return (`Options or Default value must not be specified for type text in iparam_${lang}.yml`);
      }
    }

    if(content[key][TYPE] == DROPDOWN) {
      if (!(OPTIONS in content[key])) return (`Options must be specified for type dropdown in iparam_${lang}.yml`);
      if (!(DEFAULT_VALUE in content[key])) return (`default_value must be specified for type dropdown in iparam_${lang}.yml`);
    }
  }
}
