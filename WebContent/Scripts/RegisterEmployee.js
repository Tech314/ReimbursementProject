document.addEventListener("DOMContentLoaded", function(e){
	getAjaxList(url);
})

let url = "../GetEmployeeList";
var unames = [];

const getAjaxList = (url) => {
	let xhr = new XMLHttpRequest() || new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState==4 && this.status==200) {
			let arrRequest = JSON.parse(xhr.responseText);
			//console.log(arrRequest);
			for(i in arrRequest){
				unames[i] = arrRequest[i].uname;
			}
		}
	}
	xhr.open("GET", url);
	xhr.send();
}


const checkDuplicate = (value) => {
	let dupeCheck = document.getElementById("dupeCheck");
	
	if(unames.indexOf(value) == -1){
		dupeCheck.innerHTML = "Username is available";
		dupeCheck.style = "color: green;";
	}
	else{
		dupeCheck.innerHTML = "Username is unavailable";
		dupeCheck.style = "color: red;";
	}
	
	//console.log(unames);
}

const validateEmail = (mail) => {
	let validEmail = document.getElementById("validateEmail");
	if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))
	{
		validEmail.innerHTML = "Valid Email";
		validEmail.style = "color: green;";
	}
	else{
		validEmail.innerHTML = "Invalid Email";
		validEmail.style = "color: red;";
	}
}