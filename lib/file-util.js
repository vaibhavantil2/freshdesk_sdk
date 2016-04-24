var fs = require('fs-extra');
var eh = require(__dirname + "/err");
var manifest = require(__dirname + "/manifest");
var fileErrMsg = "Error in accessing file: "
var dirErrMsg = "Error in accessing dir: "

module.exports.fileUtil = {
  ensureFile: function(fileName){
    try {
      fs.ensureFileSync(fileName);
    }
    catch(err){
      throwError(fileErrMsg + fileName);
    }
  },
  
  writeFile: function(file, data) {
    try{
      fs.writeFileSync(file, data);
    }
    catch (err){
      throwError(fileErrMsg + file);
    }
  },

  deleteFile: function(file) {
    try{
      fs.removeSync(file);
    }
    catch (err){
      throwError(fileErrMsg + file);
    }
  },

  readFile: function(file) {
    try{
      return fs.readFileSync(file, manifest.charset);
    }
    catch (err){
      throwError(fileErrMsg + file);
    }
  },

  readDir: function(dir){
    try{
      return fs.readdirSync(dir);
    }
    catch (err){
      throwError(dirErrMsg + dir);
    }
  },

  statFile: function(file){
    try{
      return fs.statSync(file);
    }
    catch (err){
      throwError(fileErrMsg + file);
    }
  }

}

function throwError(message){
  eh.error(new Error(message), 1);
}