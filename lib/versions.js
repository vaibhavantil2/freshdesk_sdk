var semver = require('semver');

exports.isCompatible = function(sdkVer, extnVer) {
  return semver.major(sdkVer) == semver.major(extnVer);
}
