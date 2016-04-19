module.exports = function SdkException (ExitStatus, message) {
  Error.call(this);
  this.name = this.constructor.name;
  this.message = message;
  this.ExitStatus = ExitStatus;
}

require('util').inherits(module.exports, Error);