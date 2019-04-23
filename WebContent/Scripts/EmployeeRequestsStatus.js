document.addEventListener("DOMContentLoaded", function (e) {
  createOnStartUp();
})

let url = "../GetAllRequests";
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

const sendAjaxPost = (url,decision,reqId,view) => {
	let xhr = new XMLHttpRequest() || new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState==4 && this.status==200) {
			changeView(view)
		}
	}
	xhr.open("POST", url);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	let sendString = "decision=" + decision + "&reqId=" + reqId;
	xhr.send(sendString);
}

const display = (xhr) => {
	tableRows = xhr.responseText;
	let table = document.getElementById("reqTable");
	table.removeChild(document.getElementById("reqTableBody"));
	let newBody = document.createElement("tbody");
	newBody.setAttribute("id", "reqTableBody");
	newBody.innerHTML = tableRows;
	table.appendChild(newBody);
		
	//console.log(requestArr);
}

const ShowAll = () => {
	url = "../GetAllRequests";
	sendAjaxGet(url, display);
}

const ShowPending = () => {
	url = "../GetPendingRequests";
	sendAjaxGet(url, display);
}

const ShowResolved = () => {
	url = "../GetResolvedRequests";
	sendAjaxGet(url, display);
}

const changeView = (value) => {
	switch(value){
	case "ShowAll":
		view="ShowAll";
		ShowAll();
		break;
	case "ShowPending":
		view="ShowPending";
		ShowPending();
		break;
	case "ShowResolved":
		view="ShowResolved";
		ShowResolved();
		break;
	default:
		alert("Unable to show requests");
	}
}

const resolveReq = (decision,reqId,empId) => {
	let url = "../ResolveRequest";
	sendAjaxPost(url,decision,reqId,empId);
	
}