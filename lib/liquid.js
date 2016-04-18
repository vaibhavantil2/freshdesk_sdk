var Liquid = require("liquid-node");
var engine = new Liquid.Engine;

engine.registerFilters({
  "asset_url": function(input){
    return 'assets/' + input;
  }
});

// callback function takes two arguments:
// 1. error
// 2. result
exports.render = function(input, data, callback){
  engine.parse(input).then(function(template){
    return template.render(data);
  }).then(callback);
}
