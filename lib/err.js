
exports.error = function(error, exitStatus) {
  if(undefined === exitStatus) {
    exitStatus = 1;
  }
  if(global.trace) {
    throw error;
  }
  else {
    console.error(error.message);
  }
  process.exit(exitStatus);
}
