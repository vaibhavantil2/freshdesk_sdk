'use strict';

var yaml = require('js-yaml');
var fs = require('fs');
var passport = require('passport');
var OAuth2Strategy = require('passport-oauth2').Strategy;
var nsUtil = require('../utils/ns-resolver');
var storage = require('../utils/data-store');
var httpUtil = require("../utils/http-util");
var _ = require('underscore');
var refresh = require('passport-oauth2-refresh');
var STRATEGY = 'oauth2';
var callback;
var charset = 'utf8';

var storeCredentials = function(data) {
  try {
    storage.store(keyNamespace(), data);
  }
  catch (err) {
    return err;
  }
}

var fetchCredentials = function() {
  try {
    return storage.fetch(keyNamespace());
  }
  catch (err) {
    return err;
  }
}

var keyNamespace = function() {
  return `${nsUtil.getNamespace()['app_id']}_oauth`;
}

var oauthInit = function(req, res, next) {
  var oauthConfig = yaml.safeLoad(fs.readFileSync(
    './config/oauth_config.yml', charset));
  var oauthStrategy = new OAuth2Strategy({
      clientID: oauthConfig['client_id'],
      clientSecret: oauthConfig['client_secret'],
      callbackURL: 'http://localhost:10001/auth/callback',
      authorizationURL: oauthConfig['authorize_url'],
      tokenURL: oauthConfig['token_url']
    },
    function(accessToken, refreshToken, profile, done) {
      let credentials = {
        'access_token': accessToken
      };
      if (refreshToken) {
        _.extend(credentials, {
          'refresh_token': refreshToken
        })
      }
      storeCredentials(credentials);
      res.writeHead(302, {
        'Location': callback
      });
      res.end();
    }.bind({
      'response': res
    })
  );
  if (!(_.isEmpty(oauthConfig['options']))) {
    oauthStrategy.authorizationParams = function() {
      return oauthConfig['options'];
    };
  }
  passport.use(oauthStrategy);
  refresh.use(oauthStrategy);
  if (next) {
    next();
  }
}

var refreshInit = function(req, res) {
  var data = fetchCredentials();
  if (data['refresh_token']) {
    refresh.requestNewAccessToken(STRATEGY, data['refresh_token'], function(
      err, accessToken, refreshToken) {
      if (accessToken || refreshToken) {
        let credentials = {
          'access_token': accessToken || data['access_token']
        };
        _.extend(credentials, {
          'refresh_token': refreshToken || data['refresh_token']
        })
        storeCredentials(credentials);
      }
      else {
        let credentials = {
          'access_token': data['access_token']
        };
        _.extend(credentials, {
          'refresh_token': data['refresh_token']
        })
        storeCredentials(credentials);
      }
      res.send(accessToken);
    });
  }
  else {
    var responsePayLoad = { status:210 };
    res.status(210).send(responsePayLoad);
  }
}

module.exports = {

  fetchCredentials: fetchCredentials,

  refresh:function(req, res) {
    oauthInit(req, res);
    refreshInit(req, res);
  },

  run: function(app) {

    app.use('/auth/index', function(req, res, next) {
      callback = req.query['callback'];
      oauthInit(req, res, next);
    });

    app.use('/auth/callback', function(req, res, next) {
      oauthInit(req, res, next);
    });

    app.use('/accesstoken', function(req, res, next) {
      httpUtil.enableCORS(res);
      next();
    });

    app.get('/auth/index', passport.authenticate(STRATEGY));

    app.get('/auth/callback', passport.authenticate(STRATEGY, {
      session: false
    }));

    app.get('/accesstoken', function(req, res) {
      httpUtil.enableCORS(res);
      var credential = fetchCredentials();
      if (credential) {
        res.send(credential['access_token']);
      }
      else {
        res.status(404).send();
      }
    });

  }
}
