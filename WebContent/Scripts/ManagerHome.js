document.addEventListener("DOMContentLoaded", function (e) {
  createOnStartUp();
})

const createOnStartUp = () => {
	sendAjaxGet("../GetManagerHomeInfo", display);
}

const sendAjaxGet = (url, func) => {
	//ActiveXObject for IE9 and older compatibility
	let xhr = new XMLHttpRequest() || new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState==4 && this.status==200) {
			func(this);
		}
		else if(this.status==418){
			window.location.href = "../418.html";
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
	reimbursementText.innerHTML = "There are currently <a href='EmployeeRequestsStatus.html' target='_self'>" + 
						requestArr[1] + "</a> requests pending. Please view and resolve, or request " +
						"additional information.";
}

let coffee = document.getElementById("coffee");

coffee.addEventListener("click",function(e){
	sendAjaxGet("../Welcome",coffeeFunc);
});

const coffeeFunc = (xhr) => {
	window.location.href = "./418.html";
}

