var request = require('supertest');
var helper = require('./helper');
var pjson = require('../package.json');
global.pjson = pjson;

describe('Cli run test', function () {
  var server;

  beforeEach(function () {
    process.chdir(projectDir['name']);
    server = require('../lib/cli/run').run();
  });

  afterEach(function () {
    server.close();
  });

  it('expects content', function testSlash(done) {
    var pageParams = require("../test-res/ticket_page_params");
    request(server)
      .post('/app/')
      .send(pageParams)
      .set({'FAExtnVersion' : '2.1.0'})
      .expect(200,done)
  })

  it('incompatible browser extension', function testSlash(done) {
    var pageParams = require("../test-res/ticket_page_params");
    request(server)
      .post('/app/')
      .send(pageParams)
      .set({'FAExtnVersion' : '0.0.1'})
      .expect(400,done)
  })

  it('expects json from /version.json', function testSlash(done) {
    request(server)
      .get('/version.json')
      .expect(200,done)
  });

  it('compatible with', function testSlash(done) {
    request(server)
      .get('/version/compatible/2.1.0')
      .expect(200,done)
  });
});
