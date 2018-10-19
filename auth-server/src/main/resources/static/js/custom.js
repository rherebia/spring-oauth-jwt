
function tratarOauth() {
	if (window.location.hash) {// implicit grant type
		var hash = window.location.hash.substring(1);
		
		var params = hash.split("&");
		
		document.getElementById("accessToken").innerHTML = params[0].split("=")[1];
	} else { // authorization code grant type
		var url = new URL(window.location);
		
		var code = url.searchParams.get("code");
		
		var headers = new Headers();
		headers.append('Authorization', 'Basic YnNlOjEyMw==');
		headers.append('Content-Type', 'application/x-www-form-urlencoded');
		
		fetch('https://localhost:8083/oauth/token', {
			method: 'POST',
			body: "grant_type=authorization_code&code=".concat(code,"&redirect_uri=https://localhost:8083/token-handler"),
			headers: headers 
		}).then(function(response) {
			response.json().then(function(data) {
				document.getElementById("accessToken").innerHTML = data.access_token;
			});
		}).then(html => console.log(html));
	}
}
