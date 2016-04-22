var fs = require('fs-extra');
const pkgExt = ".plg"

exports.getNamespace = function() {
  return { "app_id" : rootFolderName()}; // TBD: normalize
}

exports.getRootFolder = function () {
  return rootFolderName();
}

exports.pkgExt = pkgExt;

function rootFolderName(){
  return process.env.PWD.split("/").pop();
}