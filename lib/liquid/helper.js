/* 

Copyright (C) 2016 Freshdesk, Inc.

This source code is a part of the Fresh SDK and is covered by the our license
terms. For details about this license, please read the LICENSE.txt which is 
bundled with this source code.

*/

module.exports = {
  getStringValue: function(map, input) {
    var obj = map[input];
    if ( obj == null ) {
      return null;
    }
    else if ( obj instanceof String) {
      return obj;
    }
    else {
      return obj.toString();
    }
  }
};
