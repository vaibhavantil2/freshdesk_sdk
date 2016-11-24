module.exports = {
  enableCORS : function(res) {
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.setHeader('Access-Control-Allow-Methods', 'POST, GET, PUT, UPDATE, DELETE, OPTIONS');
    res.setHeader('Access-Control-Allow-Headers', 'X-CSRF-Token, X-Requested-With, content-type, MKP-EXTNID, MKP-VERSIONID, MKP-Route');
  }
}
