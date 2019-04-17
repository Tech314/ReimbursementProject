document.addEventListener("DOMContentLoaded", function (e) {
  createOnStartUp();
})

let url = "../GetAllEmpRequests";
let view = "ShowAll";

const createOnStartUp = () => {
	sendAjaxGet(url, display);
}

const sendAjaxGet = (url, func) => {
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
	tableRows = xhr.responseText;
	let table = document.getElementById("reqTable");
	table.removeChild(document.getElementById("reqTableBody"));
	let newBody = document.createElement("tbody");
	newBody.setAttribute("id", "reqTableBody");
	newBody.innerHTML = tableRows;
	table.appendChild(newBody);
	
}

const ShowAll = () => {
	url = "../GetAllEmpRequests";
	sendAjaxGet(url, display);
}

const ShowPending = () => {
	url = "../GetPendingEmpRequests";
	sendAjaxGet(url, display);
}

const ShowResolved = () => {
	url = "../GetResolvedEmpRequests";
	sendAjaxGet(url, display);
}

const changeView = (value) => {
	switch(value){
	case "ShowAll":
		ShowAll();
		view="ShowAll";
		break;
	case "ShowPending":
		ShowPending();
		view="ShowPending";
		break;
	case "ShowResolved":
		ShowResolved();
		view="ShowResolved";
		break;
	default:
		alert("Unable to show requests");
	}
}