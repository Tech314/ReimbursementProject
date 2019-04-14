document.addEventListener("DOMContentLoaded", function (e) {
  createOnStartUp();
})


const createOnStartUp = () => {
	let url = "../GetEmployeeList"
	sendAjaxGet(url, displayOptions);
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

const sendAjaxPost = (url,decision,reqId,empId) => {
	let xhr = new XMLHttpRequest() || new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState==4 && this.status==200) {
			changeView(empId)
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

const displayOptions = (xhr) => {
	requestArr = JSON.parse(xhr.responseText);
	let selectBox = document.getElementById("empDropdown");
	
	requestArr.sort((a,b) => {
		return (a.fname > b.fname) ? 1 : -1;
	});
	
	for(i in requestArr){
		let newOption = document.createElement("option");
		
		newOption.innerHTML = requestArr[i].fname + " " + requestArr[i].lname;
		newOption.setAttribute("value", requestArr[i].id);
		
		selectBox.appendChild(newOption);
	}
} 

const changeView = (value) => {
	let url = "../GetRequestsByEmployee?uid="+value;

	sendAjaxGet(url,display);
}

const resolveReq = (decision,reqId,empId) => {
	let url = "../ResolveRequest";
	sendAjaxPost(url,decision,reqId,empId);
	
}



