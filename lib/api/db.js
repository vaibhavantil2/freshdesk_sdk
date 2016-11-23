'use strict';
var dbApi = require('../utils/data-store');
var nsUtil = require('../utils/ns-resolver');
var _ = require('underscore');
var objsize = require('object-sizeof');
var INTERNAL_ERROR_STATUS = 500;
var INVALID_DATA_STATUS = 400;
var UNPROCESSABLE_DATA_STATUS = 422;
var CREATED_STATUS = 201;
var OK_STATUS = 200;
var NOT_FOUND_STATUS = 404;
var STORE_ERROR = "Failed to store data";
var FETCH_ERROR = "Failed to fetch data";
var DELETE_ERROR = "Failed to delete data";
var KEY_LENGTH = 30;
var DATA_SIZE = 10;
var BLANK_KEY_ERR = "The key cannot be blank";
var BLANK_VALUE_ERR = "The value cannot be blank and should be of type JSON";
var KEY_LENGTH_ERR = "Key length should not exceed 30 characters";
var VALUE_ATTRIB_ERR = "The value (which is a JSON object) should not contain more than 10 attributes";
var OBJECT_SIZE_ERR = "The combined size of the key and value should not exceed 1KB";

module.exports = {

  store: function(req, res) {
    try {
      var dbKey = req.body.dbKey;
      var data = req.body.data;
      validateKey(dbKey, data);
      dbKey = fetchKey(dbKey);
      data = _.extend(normalizeItem(data), {"createdAt" : getTimestamp(), "updatedAt" : getTimestamp() });
      var resp = dbApi.store(dbKey, data);
      res.status(CREATED_STATUS).send(resp);
    }
    catch (err) {
      if (global.verbose) {
        console.log(err.stack);
      }
      if (err.code) {
        return res.status(err.code).send(err.message);
      }
      res.status(INTERNAL_ERROR_STATUS).send(STORE_ERROR);
    }
  },

  fetch: function(req, res) {
    try {
      var key = fetchKey(req.body.dbKey);
      var resp = dbApi.fetch(key);
      if (_.isEmpty(resp)) {
        return res.status(NOT_FOUND_STATUS).send(resp);
      }
      res.status(OK_STATUS).send(resp);
    }
    catch (err) {
      if (global.verbose) {
        console.log(err.stack);
      }
      if (err.code) {
        return res.status(err.code).send(err.message);
      }
      res.status(INTERNAL_ERROR_STATUS).send(FETCH_ERROR);
    }
  },

  delete: function(req, res) {
    try {
      var key = fetchKey(req.body.dbKey);
      var resp = dbApi.delete(key);
      res.status(OK_STATUS).send(resp);
    }
    catch (err) {
      if (global.verbose) {
        console.log(err.stack);
      }
      if (err.code) {
        return res.status(err.code).send(err.message);
      }
      res.status(INTERNAL_ERROR_STATUS).send(DELETE_ERROR);
    }
  }
};

function fetchKey(key) {
  return `${nsUtil.getRootFolder()}:${key}`;
}

function getTimestamp() {
  var utcTime = new Date().toISOString();
  return new Date(utcTime).getTime();
}

function normalizeItem(data) {
  var dupData = {};
  for (var key in data) {
    if (!isBlank(key) && !isBlank(data[key]) && !_.isUndefined(data[key])) {
      dupData[key] = data[key];
    }
  }
  if (_.isEmpty(dupData)) {
    throw new customError({"message": "Mandatory attributes Missing", "code": INVALID_DATA_STATUS});
  }
  return dupData;
}

function validateKey(dbKey, data) {
  if (isBlank(dbKey)) {
    throw new customError({"message": BLANK_KEY_ERR, "code": INVALID_DATA_STATUS});
  }
  if (isBlank(data)) {
    throw new customError({"message": BLANK_VALUE_ERR, "code": INVALID_DATA_STATUS});
  }
  if (!_.isObject(data)) {
    throw new customError({"message": BLANK_VALUE_ERR, "code": UNPROCESSABLE_DATA_STATUS});
  }
  if (dbKey.length > KEY_LENGTH || checkDataSize(data)) {
    throw new customError({"message": KEY_LENGTH_ERR, "code": INVALID_DATA_STATUS});
  }
  if (_.size(data) > DATA_SIZE) {
    throw new customError({"message": VALUE_ATTRIB_ERR, "code": INVALID_DATA_STATUS});
  }
  if (objsize(data)/1024 > 2) {
    throw new customError({"message": OBJECT_SIZE_ERR, "code": INVALID_DATA_STATUS});
  }
}

function checkDataSize(data) {
  var keys = _.keys(data);
  for (let i = 0; i < keys.length; i++) {
    if (keys[i].length > KEY_LENGTH) {
      return true;
    }
  }
  return false;
}

function customError(exp) {
  Error.captureStackTrace(this, customError);
  this.name = customError.name;
  this.code = exp.code;
  this.message = exp.message;
}

function isBlank(val) {
  return (_.isNumber(val) || _.isBoolean(val)) ? _.isEmpty(String(val)) : _.isEmpty(val);
}
