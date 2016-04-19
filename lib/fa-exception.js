module.exports = function FAException (message) {
  Error.call(this);
  this.name = this.constructor.name;
  this.message = message;
}

require('util').inherits(module.exports, Error);