var iParamUtil = require('../iparam-util');
var _ = require('underscore');
var async = require('async');
var validationConst = require('../constants').validationContants;

var VALID_FILES = ["display_name", "description", "type", "required", "default_value", "options"];
var DROPDOWN = "dropdown";
var TEXT = "text";
var OPTIONS = "options";
var DEFAULT_VALUE = "default_value";
var TYPE = "type";

exports.validate = function (ValidateCB) {
  async.waterfall([
    iparamKeysValidator,
    dropDownOptionsValidator,
    keyConsistencyValidator
  ],function (err, result) {
      if(err) return ValidateCB(err);
      return ValidateCB(null);
  });
}

exports.validationType = [validationConst.PRE_PKG_VALIDATION];

function dropDownOptionsValidator(callback){
  iParamUtil.getIparamLangs(function(err, langs){
    if(err) return callback(err);
    async.each(langs, function(lang, langCB){
      iParamUtil.getConfig(lang, function(err, langcontent){
        if(err) return langCB(err);
        if(_.isNull(langcontent)) return langCB();
        ValidateDropdownKeys(langcontent, function(err){
          if( err )
            return langCB(err);
          return langCB();
        });
      });
    },function(err){
      if( err )
        return callback(err);
      return callback();
    });
  });
}

function ValidateDropdownKeys(langcontent, callback){
  async.forEachOf(langcontent, function(val, key, contentCB) {
    if((val[TYPE] == DROPDOWN) && (val[OPTIONS] == null))
      return contentCB(new Error(`Dropdown options for key '${key}' in iparam_${lang}.yml is not a list`));
    return contentCB();
  },function(err){
    if( err )
      return callback(err);
    return callback();
  });
}

function keyConsistencyValidator(callback){
  iParamUtil.getAllKeys(function(err, keys){
    if(err) return callback(err);
    if(keys.length == 1) return callback(null);
    async.forEachOf(keys, function (value, index, asyncCB) {
      if(!_.isEqual(keys[index],keys[index+1]))
        return asyncCB(new Error("Inconsistent keys detected among iparam files"));
      return asyncCB();
    },function(err){
      if( err )
        return callback(err);
      return callback();
    });
  });
}

function iparamKeysValidator(callback){
  iParamUtil.getIparamLangs(function(err, langs){
    if(err) return callback(err);
    async.each(langs, function(lang, asyncCB){
      iParamUtil.getConfig(lang, function(err, content){
        if(err) return callback(err);
        checkValidKeys(lang, content, function (err) {
          if(err) return asyncCB(err);
          return asyncCB();
        })
      });
    },function(err){
      if( err )
        return callback(err);
      return callback();
    });
  });
}

function checkValidKeys(lang, content, callback){
  if(_.isNull(content)) return callback();
  async.forEachOf(content,function(value, key, asyncCB){

    if(_.difference(Object.keys(value),VALID_FILES ).length > 0){
      return callback(new Error(`Invalid keys in iparam_${lang}.yml `));
    }

    if(!(value[TYPE]== DROPDOWN || value[TYPE] == TEXT)){
      return callback(new Error(`Invalid type in iparam_${lang}.yml `));
    }

    if(value[TYPE] == TEXT) {
      if (OPTIONS in value || DEFAULT_VALUE in value) {
        return callback(new Error(`Options or Default value must not be specified in iparam_${lang}.yml`))
      }
    }

    if(value[TYPE] == DROPDOWN) {
      if (!(OPTIONS in value)) {
        return callback(new Error(`Options or Default must be specified in iparam_${lang}.yml`))
      }
    }
    return asyncCB();

  },function(err){
      if( err )
        return callback(err);
      return callback();
    });
}
