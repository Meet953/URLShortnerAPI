document.addEventListener("DOMContentLoaded", function(){

document.getElementById("submit").addEventListener("click", function(event){
  event.preventDefault();
  getShortUrl();
});

function getShortUrl() {
	var originalUrl = document.getElementById("url").value;
	if (originalUrl == '') {
		alert('Please enter a valid url.');
	} else {
		var xhttp = new XMLHttpRequest();
        var url='http://localhost:8080/url/shorten';
        xhttp.open("POST", url);
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.onreadystatechange = function() {
          if (this.readyState == 4 && this.status == 201) {
            var response = this.responseText;
            createLink(response);
          }
       };
       var data = {originalUrl:originalUrl};
       xhttp.send(JSON.stringify(data));
	}
}



 function createLink(response) {
  document.getElementById("redirect").innerHTML = "";
  var a = document.createElement('a');
  var link = document.createTextNode("Click here");
  a.appendChild(link);
  a.title = response;
  a.href = 'http://localhost:8080/url/'+response;
  document.body.appendChild(a);
  }
});


