document.addEventListener("DOMContentLoaded", function (e) {
  createOnStartUp();
})

let url = "../GetAllEmpRequests";

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
	requestArr = JSON.parse(xhr.responseText);
	let table = document.getElementById("reqTable");
	table.removeChild(document.getElementById("reqTableBody"));
	let newBody = document.createElement("tbody");
	newBody.setAttribute("id", "reqTableBody");
	table.appendChild(newBody);
	for (i in requestArr) {
		let newRow = document.createElement("tr");
		
		newRow.innerHTML = 
			"<td>" + requestArr[i].reqDate + "</td>" +
			"<td>" + requestArr[i].expDate + "</td>" + 
			"<td>" + requestArr[i].reqAmt + "</td>" +
			"<td>" + requestArr[i].reqDesc + "</td>" + 
			"<td>" + requestArr[i].reqStatus + "</td>" +
			"<td>" + requestArr[i].reqDecision + "</td>";
			
		newBody.appendChild(newRow);
		
	}
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
		break;
	case "ShowPending":
		ShowPending();
		break;
	case "ShowResolved":
		ShowResolved();
		break;
	default:
		alert("Unable to show requests");
	}
}