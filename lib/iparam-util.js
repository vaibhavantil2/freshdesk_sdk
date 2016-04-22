var yaml = require('js-yaml');
var async = require("async");
var _ = require('underscore');
var fs = require('fs');
var manifest = require(__dirname + "/manifest");

var cwd = process.cwd();

module.exports= {

  getConfig: function getConfig(lang, ConfigCB){
    getYmlContent(`iparam_${lang}.yml`, function(err, iparamdata){
      if(err)
        return ConfigCB(new Error(`Error in accessing iparam_${lang}.yml file`));
      return ConfigCB(null, iparamdata);
    });
  },

  getIparamLangs: function getIparamLangs(callback){
    var languages = [];
    listOfIparamFiles(function(err, iparamFiles){
      if(err) return callback(new Error("Error in accessing iparam files"));
      for(var i=0; i<iparamFiles.length; i++){
        languages.push(iparamFiles[i].match('iparam_(.*).yml')[1]);
      }
      return callback(null, languages);
    });
  },

  getAllKeys: function getAllKeys(callback){
    fs.readdir(cwd + '/iparam', function(err, files){
      var keys = [];
      async.each(files,function (filename, asyncCB) {
        getYmlContent(filename, function (err, ymlData){
          if(err) return asyncCB(err);
          if(_.isNull(ymlData)) return asyncCB();
          keys.push(Object.keys(ymlData));
          asyncCB();
        })
      },function (err) {
        if(err) return callback(err);
        return callback(null, keys);
      });
    });
  },

  getValuesForLocalTesting: function getValuesForLocalTesting() {
    var test_file = 'iparam/test_data.yml';
    var test_values = yaml.safeLoad(fs.readFileSync(cwd + "/" + test_file, manifest.mf['source-charset']));
    return test_values;
  }
}


function listOfIparamFiles(cb){
  var iparamFiles = [];
  fs.readdir(cwd + '/iparam', function(err, files){
    async.each(files, function (filename, callback) {
      if(filename.match('iparam_(.*).yml'))
        iparamFiles.push(filename);
      callback();
    },function (err) {
      if(err) return cb(err);
        return cb(null, iparamFiles);
    });
  });
}

function getFileContentsAsMap(iparamFiles, fileContentCB){
  var data = {};
  async.each(iparamFiles, function(filename, asyncCB){
    getYmlContent( filename, function(content) {
      data[file_name.match('iparam_(.*).yml')[1]] = content;
      fileContentCB();
    });
  },function (err) {
    if(err) return fileContentCB(err);
    return fileContentCB(null, data);
  });
}

function getYmlContent(file_name, YmlContentcb){
  fs.readFile(cwd + '/iparam/' + file_name, manifest.mf['source-charset'], function(err, data){
    if(err) return YmlContentcb(err);
    return YmlContentcb(null, yaml.safeLoad(data));
  });
}
