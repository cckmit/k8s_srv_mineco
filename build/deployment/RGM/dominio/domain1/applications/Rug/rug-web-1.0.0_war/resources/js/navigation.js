
var formId;
var backAction;
var nextAction;



function setNavigation(id, backAction, nextAction) {
	this.formId = id;
	this.backAction = backAction;
	this.nextAction = nextAction;
}

function submitAction(action) {
	var form = document.getElementById(formId);
	var forward = "/portal/";
	
	// verdadero si es siguiente
	if(action) {
		forward = nextAction;
	} else {
		forward = backAction;
	}
	
	form.action = forward;
	form.submit();
	
}