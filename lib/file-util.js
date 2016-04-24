var fs = require('fs-extra');
var eh = require(__dirname + "/err");
var manifest = require(__dirname + "/manifest");

module.exports = {
  ensureFile: function(fileName){
    try {
      fs.ensureFileSync(fileName);
    }
    catch(err){
      eh.error(err);
    }
  },

  writeFile: function(file, data) {
    try{
      fs.writeFileSync(file, data);
    }
    catch (err){
      eh.error(err);
    }
  },

  deleteFile: function(file) {
    try{
      fs.removeSync(file);
    }
    catch (err){
      eh.error(err);
    }
  },

  readFile: function(file) {
    try{
      return fs.readFileSync(file, manifest.charset);
    }
    catch (err){
      eh.error(err);
    }
  },

  readDir: function(dir){
    try{
      return fs.readdirSync(dir);
    }
    catch (err){
      eh.error(err);
    }
  },

  statFile: function(file){
    try{
      return fs.statSync(file);
    }
    catch (err){
      eh.error(err);
    }
  }

}
