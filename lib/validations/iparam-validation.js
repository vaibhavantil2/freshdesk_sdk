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
  var error_msgs = [];
  error_msgs.push(iparamKeysValidator());
  error_msgs.push(dropDownOptionsValidator());
  error_msgs.push(keyConsistencyValidator());
  error_msgs = _.compact(_.flatten(error_msgs));
  return error_msgs;
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];

function dropDownOptionsValidator(){
  var langs = iParamUtil.getIparamLangs();
  var dropdownErrs = [];
  for(var i in langs){
    var content = iParamUtil.getConfig(langs[i]);
    if(content)
      dropdownErrs.push(ValidateDropdownKeys(langs[i], content));
  }
  return _.flatten(dropdownErrs);
}

function ValidateDropdownKeys(lang, langcontent){
  var dropdownErr = [];
  for(var key in langcontent){
    if (langcontent[key][TYPE] != DROPDOWN)
      continue;
    if(!Array.isArray(langcontent[key][OPTIONS])){
      dropdownErr.push(`Dropdown options for key ${key} in iparam_${lang}.yml is not a list`); 
      continue;
    }
    if(langcontent[key][DEFAULT_VALUE] && !(_.contains(langcontent[key][OPTIONS], langcontent[key][DEFAULT_VALUE])))
      dropdownErr.push(`Default value specified in iparam_${lang}.yml is not available in options`);
  }
  return dropdownErr;
}

function keyConsistencyValidator(){
  var iparamKeys = iParamUtil.getAllIparamKeys();
  for(var i =0 ;i < (iparamKeys.length -1); i++){
    if(!_.isEqual(iparamKeys[i], iparamKeys[i + 1]))
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
