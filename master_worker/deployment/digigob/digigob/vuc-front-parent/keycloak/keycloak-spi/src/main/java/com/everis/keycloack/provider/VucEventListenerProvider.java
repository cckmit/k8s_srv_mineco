package com.everis.keycloack.provider;

import java.util.Map;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;

public class VucEventListenerProvider implements EventListenerProvider {

	public void close() {

	}

	public void onEvent(Event event) {
		System.out.println("Event Occurred:" + toString(event));

	}

	public void onEvent(AdminEvent event, boolean includeRepresentation) {
		System.out.println("Admin Event Occurred:" + event.toString());

	}

	private String toString(Event event) {
		StringBuilder sb = new StringBuilder();

		sb.append("type=");

		sb.append(event.getType());

		sb.append(", realmId=");

		sb.append(event.getRealmId());

		sb.append(", clientId=");

		sb.append(event.getClientId());

		sb.append(", userId=");

		sb.append(event.getUserId());

		sb.append(", ipAddress=");

		sb.append(event.getIpAddress());

		if (event.getError() != null) {

			sb.append(", error=");

			sb.append(event.getError());

		}

		if (event.getDetails() != null) {

			for (Map.Entry<String, String> e : event.getDetails().entrySet()) {
				
				sb.append(", ");

				sb.append(e.getKey());

				if (e.getValue() == null || e.getValue().indexOf(' ') == -1) {

					sb.append("=");

					sb.append(e.getValue());

				} else {

					sb.append("='");

					sb.append(e.getValue());

					sb.append("'");

				}

			}

		}

		return sb.toString();
	}

}
