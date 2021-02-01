<%
// Tries to redirect to the proper TSF+ example (from the partial archetypes)

// Spring Web Flow
try {
	Class.forName("com.egoveris.deo.webapp.service.DummyWebFlowService");
	response.sendRedirect("app/dummy-flow");
	return;
} catch (ClassNotFoundException ignored) {}

// Apache Tiles
try {
	Class.forName("com.egoveris.deo.webapp.controller.DummyTilesController");
	response.sendRedirect("app/init");
	return;
} catch (ClassNotFoundException ignored) {}

// Otherwise, shows a plain HTML welcome page:
%>
<!DOCTYPE html>
<html>
<meta charset="ISO-8859-1">
<head><title>deo</title></head>
<body><h1>Hello, world!</h1></body>
</html>
