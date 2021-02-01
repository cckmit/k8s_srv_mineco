<%@ page language="Java" import="java.util.*"%>
<html>
<body>
<h2>Hora</h2>
<%
    Calendar localCalendar = Calendar.getInstance();
    TimeZone localTimeZone = localCalendar.getTimeZone();
    Date date = new Date();
%>

<ul>
  <li>
    <b>Timezone:</b> <%= localTimeZone.getDisplayName() %>
  </li>
  <li>
    <b>Calendar.getTime:</b> <%= localCalendar.getTime() %>
  </li>
  <li>
    <b>new Date():</b> <%= date %>
  </li>    
</ul>

<h2>Variables de sistema</h2>
<table border="1">
<tr><td><b>Propiedad</b></td>
<td><b>Valor</b></td></tr>
<%
Properties prop = System.getProperties();
Enumeration enu = prop.propertyNames();
//System.out.println("PROPIEDAD ====> VALOR");
while(enu.hasMoreElements()) {
	String key = (String) enu.nextElement();
	String value = prop.getProperty(key);
	//System.out.println(key + " ====> " + value);
%>
	<tr>
		<td><%= key %></td>
		<td><%= value %></td>
	</tr>
<% } %>
</table>
</body>
</html>