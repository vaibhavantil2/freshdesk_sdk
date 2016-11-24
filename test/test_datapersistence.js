var request = require('supertest');
var helper = require('./helper');
var pjson = require('../package.json');
global.pjson = pjson;

describe('Persistence test', function () {
  var server;

  beforeEach(function () {
    process.chdir(projectDir['name']);
    server = require('../lib/cli/run').run();
  });

  afterEach(function () {
    server.close();
  });

  it('should store', function testSlash(done) {
    var payload = {
      'data' : {
        'info' : 'hello world'
      },
      'dbKey' : 'user1',
      'action' : 'store'
    }
    request(server)
      .post('/dprouter')
      .send(payload)
      .set({
        "content-type":"application/json",
        "MKP-EXTNID": "101",
        "MKP-VERSIONID": "101",
        "MKP-ROUTE": "db"
      })
      .expect(201,done)
  })

  it('should fetch', function testSlash(done) {
    var payload = {
      'dbKey' : 'user1',
      'action' : 'fetch'
    }
    request(server)
      .post('/dprouter')
      .send(payload)
      .set({
        "content-type":"application/json",
        "MKP-EXTNID": "101",
        "MKP-VERSIONID": "101",
        "MKP-ROUTE": "db"
      })
     .expect(200,done)
  })

  it('should delete', function testSlash(done) {
    var payload = {
      'dbKey' : 'user2',
      'action' : 'delete'
    }
    request(server)
      .post('/dprouter')
      .send(payload)
      .set({
        "content-type":"application/json",
        "MKP-EXTNID": "101",
        "MKP-VERSIONID": "101",
        "MKP-ROUTE": "db"
      })
      .expect(200,done)
  })

  it('should fail - missing key', function testSlash(done) {
    var payload = {
      'data' : {
        'info' : 'hello world'
      },
      'dbKey' : '',
      'action' : 'store'
    }
    request(server)
      .post('/dprouter')
      .send(payload)
      .set({
        "content-type":"application/json",
        "MKP-EXTNID": "101",
        "MKP-VERSIONID": "101",
        "MKP-ROUTE": "db"
      })
      .expect(400,done)
  })

  it('should fail - key length > 30', function testSlash(done) {
    var payload = {
      'data' : {
        'info' : 'hello world'
      },
      'dbKey' : 'abcdefghijklmnopqrstuvwxyz12345678900987',
      'action' : 'store'
    }
    request(server)
      .post('/dprouter')
      .send(payload)
      .set({
        "content-type":"application/json",
        "MKP-EXTNID": "101",
        "MKP-VERSIONID": "101",
        "MKP-ROUTE": "db"
      })
      .expect(400,done)
  })

  it('should fail - missing key', function testSlash(done) {
    var payload = {
      'data' : {
        'info1' : 'hello world',
        'info2' : 'hello world',
        'info3' : 'hello world',
        'info4' : 'hello world',
        'info5' : 'hello world',
        'info6' : 'hello world',
        'info7' : 'hello world',
        'info8' : 'hello world',
        'info9' : 'hello world',
        'info10' : 'hello world',
        'info11' : 'hello world'
      },
      'dbKey' : 'user3',
      'action' : 'store'
    }
    request(server)
      .post('/dprouter')
      .send(payload)
      .set({
        "content-type":"application/json",
        "MKP-EXTNID": "101",
        "MKP-VERSIONID": "101",
        "MKP-ROUTE": "db"
      })
      .expect(400,done)
  })

  it('should fail - data must be json', function testSlash(done) {
    var payload = {
      'data' : "hello world",
      'dbKey' : 'user3',
      'action' : 'store'
    }
    request(server)
      .post('/dprouter')
      .send(payload)
      .set({
        "content-type":"application/json",
        "MKP-EXTNID": "101",
        "MKP-VERSIONID": "101",
        "MKP-ROUTE": "db"
      })
      .expect(422,done)
  })
});
