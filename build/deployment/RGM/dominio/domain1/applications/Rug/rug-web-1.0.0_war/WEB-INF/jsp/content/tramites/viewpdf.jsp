<%@page import="java.io.*"%>
<%@page import="javax.servlet.http.HttpServletRequest"%>
<%@page import="javax.servlet.http.HttpSession"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%

	//HttpSession session=request.getSession();
	//String nameFile = request.getContextPath()+"/resources/pdf/plantilla.pdf";
	//request.getParameter("namefile");
	//String urlFile=session.getServletContext().getInitParameter("file");	
	//String urlFile=request.getRealPath("/resources/tramites/plantilla.pdf") ;
	//ServletConfig sc =(ServletConfig) applicationContext.get("javax.servlet.ServletConfig");
	//String ruta = getServletContext().getRealPath("resources") +File.separator+"pdf"+File.separator+"sector1.pdf";
	String ruta = getServletContext().getRealPath("resources"+File.separator+"pdf"+File.separator+"sector1.pdf");
	File file = new File(ruta);
	System.out.println("url " + ruta);

	try
	{
		InputStream in = new FileInputStream(file);
		//InputStream in = new FileInputStream(urlFile+nameFile);
		//InputStream in = new FileInputStream(nameFile);
		byte[] data = new byte[in.available()];
		in.read(data);
		response.setContentType("application/pdf;");
		response.setHeader("Content-Disposition","inline;filename=\"Certificado.pdf\";");
		response.setContentLength(data.length);
		response.getOutputStream().write(data);
		//response.getOutputStream().flush();
		response.getOutputStream().close();
	} catch (Exception e) {
	    e.printStackTrace();
	    System.out.println(e);
	} catch (IOError io) {
	    io.printStackTrace();
	    System.out.println(io);
	}
%>
