/* Copyright (C) 2016 Freshdesk, Inc.
This source code is a part of the Fresh SDK and is covered by the our license terms. For details about this license, please read the LICENSE.txt which is bundled with this source code. */

var semver = require('semver');

exports.isCompatible = function(sdkVer, extnVer) {
  return semver.major(sdkVer) === semver.major(extnVer);
}
