package com.everis.keycloack.provider;

import org.keycloak.Config.Scope;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;

public class VucEventListenerProviderFactory implements EventListenerProviderFactory  {

	public EventListenerProvider create(KeycloakSession session) {
		return new VucEventListenerProvider();
	}

	public void init(Scope config) {
	}

	public void postInit(KeycloakSessionFactory factory) {

	}

	public void close() {

	}

	public String getId() {
		return "everis_test_event_listener";
	}

}
