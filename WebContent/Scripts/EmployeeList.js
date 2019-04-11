document.addEventListener("DOMContentLoaded", function (e) {
  createOnStartUp();
})

let url = "../GetEmployeeList";
let sortOn = "id";
let ascDesc = "asc";

const createOnStartUp = () => {
	sendAjaxGet(url, display);
}

const sendAjaxPost = (url, eid) => {
	let xhr = new XMLHttpRequest() || new ActiveXObject("Microsoft.HTTPRequest");
	xhr.onreadystatechange = function() {
		if (this.readyState==4 && this.status==200) {
			alert(this.responseText);
		}
	}
	xhr.open("POST", url);
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send("eid=" + eid);
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
	let table = document.getElementById("empTable");
	table.removeChild(document.getElementById("empList"));
	let newBody = document.createElement("tbody");
	newBody.setAttribute("id", "empList");
	table.appendChild(newBody);
	
	requestArr.sort(compare);
	
	for (i in requestArr) {
		let newRow = document.createElement("tr");

		newRow.innerHTML = 
			"<td>" + requestArr[i].id + "</td>" +
			"<td>" + requestArr[i].fname + "</td>" +
			"<td>" + requestArr[i].lname + "</td>" + 
			"<td>" + requestArr[i].uname + "</td>" + 
			"<td>" + requestArr[i].email + "</td>" +
			"<td><input type='button' value='Reset Password' onclick='resetPassword(" + requestArr[i].id + ")'></td>";
			
		newBody.appendChild(newRow);
		
	}
}

const sortColumn = (col) => {
	sortOn = col;
	sendAjaxGet(url,display);
}

const sortAscDesc = (ad) => {
	ascDesc = ad;
	sendAjaxGet(url,display);
}

const compare = (a,b) => {
	if(sortOn == "id"){
		if(ascDesc == "asc"){
			return a.id - b.id;
		}
		else{
			return b.id - a.id;
		}
	}
	
	if(sortOn == "fname"){
		if(ascDesc == "asc"){
			return (a.fname > b.fname) ? 1 : -1;
		}
		else{
			return (a.fname < b.fname) ? 1 : -1;
		}
	}
	
	if(sortOn == "lname"){
		if(ascDesc == "asc"){
			return (a.lname > b.lname) ? 1 : -1;
		}
		else{
			return (a.lname < b.lname) ? 1 : -1;
		}
	}
	
	if(sortOn == "uname"){
		if(ascDesc == "asc"){
			return (a.uname > b.uname) ? 1 : -1;
		}
		else{
			return (a.uname < b.uname) ? 1 : -1;
		}
	}
	
	if(sortOn == "email"){
		if(ascDesc == "asc"){
			return (a.email > b.email) ? 1 : -1;
		}
		else{
			return (a.email < b.email) ? 1 : -1;
		}
	}
}

const resetPassword = (eid) => {
	let url = "../ResetPassword";
	
	sendAjaxPost(url,eid);
}