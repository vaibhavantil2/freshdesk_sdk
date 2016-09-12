(function() {
  return {
    events: [
      { event: 'click', selector: '#clickbutton', callback: 'clickcb'},
      { event: 'note_created', callback: 'notecb'}
    ],
    notecb: function() {
      alert("note created");
    },
    clickcb: function() {
      alert("button clicked");
    },
    initialize: function() {
      console.log("My first app!");
    }
  }
})();
