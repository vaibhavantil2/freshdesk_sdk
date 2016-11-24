var linter = require("eslint").linter;
var fileUtil = require("../utils/file-util");
var _ = require("underscore");
var validationConst = require('./constants').validationContants;
var FILES = {
  "app.js" : "./app/app.js",
  "server.js" : "./app/server.js"
}

var lint = function() {
  var messages = {};
  var linterConfig = {
    rules: {
      "semi" : 2,
      "no-debugger" : 2,
      "no-extra-semi" : 2,
      "no-extra-parens" : 2,
      "no-unused-vars" : 2,
      "curly" : 2,
      "no-eval" : 2,
      "strict" : [2, "function"]
    }
  }
  for (var file in FILES) {
    if (fileUtil.fileExists(FILES[file])) {
      var fileContent = fileUtil.readFile(FILES[file]);
      var lintMessages = linter.verify(fileContent, linterConfig);
      if (!(_.isEmpty(lintMessages))) {
        messages[file] = lintMessages;
      }
    }
  }
  return messages;
}

module.exports = {
  validate: function() {
    var messages = lint();
    if (!(_.isEmpty(messages))) {
      console.log("Following errors must be fixed:\n")
      for (var file in messages) {
        console.log(`${file}:`);
        var lintMessages = messages[file];
        for (var message of lintMessages) {
          console.log(`line${message.line}: ${message.message}`);
        }
      }
      process.exit(0);
    }
  },
  validationType: [validationConst.PRE_PKG_VALIDATION]
}
