package com.egoveris.ffdd.render.zk.ws;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

public class Client implements FactoryBean<Object>, InitializingBean {

	private URL wsdlDocumentLocation;
	private Class<?> serviceEndpointInterface;
	private String namespaceURI;
	private String localPart;

	// derived from namespaceURI and localPart
	private QName serviceName;

	public void afterPropertiesSet() {
		serviceName = new QName(namespaceURI, localPart);
	}

	public Object getObject() {
		Service service = Service.create(wsdlDocumentLocation, serviceName);
		Object port = service.getPort(serviceEndpointInterface);
		return port;
	}

	public Class<?> getObjectType() {
		return serviceEndpointInterface;
	}

	public boolean isSingleton() {
		return false;
	}

	public URL getWsdlDocumentLocation() {
		return wsdlDocumentLocation;
	}

	public void setWsdlDocumentLocation(final URL wsdlDocumentLocation) {
		this.wsdlDocumentLocation = wsdlDocumentLocation;
	}

	public Class<?> getServiceEndpointInterface() {
		return serviceEndpointInterface;
	}

	public void setServiceEndpointInterface(final Class<?> serviceEndpointInterface) {
		this.serviceEndpointInterface = serviceEndpointInterface;
	}

	public String getNamespaceURI() {
		return namespaceURI;
	}

	public void setNamespaceURI(final String namespaceURI) {
		this.namespaceURI = namespaceURI;
	}

	public String getLocalPart() {
		return localPart;
	}

	public void setLocalPart(final String localPart) {
		this.localPart = localPart;
	}

}
