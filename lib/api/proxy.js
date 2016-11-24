/*

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Freshdesk SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is
bundled with this source code.

*/

'use strict'

var request = require('request');
var _ = require("underscore");
var oauth = require("../routes/oauth2");
var manifest = require("../manifest");
var url = require("url");
var iparamUtil = require("../utils/iparam-util");
var NETWORK_ERROR_STATUS = 502;
var NETWORK_ERROR_MSG = "Error in establishing connection";

module.exports = {
  execute: function(req, res) {
    var templates = {};
    templates["iparam"] = iparamUtil.getValuesForLocalTesting();
    var reqOptions = req.body.data;
    if (_.contains(manifest.features, "oauth")) {
      templates = _.extend(templates, fetchToken());
    }
    reqOptions = substituteTemplates(reqOptions, templates);
    var urlObject = url.parse(reqOptions.url);
    var domain = `${urlObject.protocol}//${urlObject.hostname}`;
    if (!(_.contains(substituteTemplates(manifest.whitelistedDomains, templates), domain))) {
      return res.status(400).send("Domain not allowed.");
    }
    makeRequest(reqOptions, function(error, response, responseBody) {
      if (error) {
        return res.status(NETWORK_ERROR_STATUS).send(NETWORK_ERROR_MSG);
      }
      var responsePayLoad = { status:response.statusCode, headers:response.headers, response:responseBody };
      res.status(response.statusCode).send(responsePayLoad);
    });
  }
};

function fetchToken() {
  return { 'access_token' : oauth.fetchCredentials()['access_token'] };
}

function substituteTemplates(options, templates) {
  options = JSON.parse(_.template(JSON.stringify(options))(templates));
  return options
}

function makeRequest(options, callback) {
  request(options, function (error, response, responseBody) {
    callback(error, response, responseBody);
  });
}
