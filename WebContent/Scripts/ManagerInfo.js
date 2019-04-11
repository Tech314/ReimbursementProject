document.addEventListener("DOMContentLoaded", function (e) {
  createOnStartUp();
})

let url = "../GetManagerInfo";

const createOnStartUp = () => {
	sendAjaxGet(url, display);
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
	requestArr = JSON.parse(xhr.responseText);
	console.log(requestArr);
	let table = document.getElementById("empTable");
	table.removeChild(document.getElementById("requestTableBody"));
	let newBody = document.createElement("tbody");

	newBody.innerHTML = "<tr><td>Manager Id</td><td>" + requestArr.id + "</td></tr>"
			+ "<tr><td>User Name</td><td>" + requestArr.uname + "</td></tr>"
			+ "<tr><td>First Name</td><td>" + requestArr.fname + "</td></tr>"
			+ "<tr><td>Last Name</td><td>" + requestArr.lname + "</td></tr>"
			+ "<tr><td>Email</td><td>" + requestArr.email + "</td></tr>"
	table.appendChild(newBody);
}