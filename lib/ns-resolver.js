const pkgExt = ".plg"

exports.getNamespace = function() {
  folderName = rootFolderName();
  return { "app_id" : normalize(folderName)};
}

exports.getRootFolder = function () {
  return rootFolderName();
}

exports.pkgExt = pkgExt;

function rootFolderName(){
  return process.env.PWD.split("/").pop();
}

function normalize(folderName) {
  var MAX_CHARS = 6;
  var processed = folderName.toLowerCase().replace(/[^a-zA-Z0-9]/g, "_");
  if(processed.length > MAX_CHARS) {
    processed = processed.substring(0, MAX_CHARS);
  }
  return "fa_" + processed + "_101";
}