document.addEventListener("DOMContentLoaded", function (e) {
  createOnStartUp();
})

const createOnStartUp = () => {
	sendAjaxGet("../GetEmployeeHomeInfo", display);
}

const sendAjaxGet = (url, func) => {
	//ActiveXObject for IE9 and older compatibility
	let xhr = new XMLHttpRequest() || new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState==4 && this.status==200) {
			func(this);
		}
	}
	xhr.open("GET", url);
	xhr.send();
}

const display = (xhr) => {
	let requestArr = JSON.parse(xhr.responseText);
	
	let welcomeText = document.getElementById("welcomeText");
	let reimbursementText = document.getElementById("reimbursementText");
	
	welcomeText.innerHTML = "Welcome to Your Reimbursement Portal " + requestArr[0];
	reimbursementText.innerHTML = "You currently have " + 
						requestArr[1] + " request(s) pending. View all your requests " +
						"<a href='EmployeeRequests.html' target='_blank'>here</a>";
}



