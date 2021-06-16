package com.egoveris.ccomplejos.base.model;

public class GenericEnum {

	public static enum SsppEnum {
		isp, sag, sernapesca, aduanas, otro;
	};
	public static enum DestinacionEnum {
		Exportacion, Importacion, Transito, Cualquiera;
	};
	public static enum DeclaracionEnum {
		DUS_AT, DUS_LEG, DIN;
	};
	public static enum PortTypeEnum {
		// Aereo, maritimo, ferroviario, terrestre
		Aereo, Maritimo_Fluvial_Lacustre, Terrestre, Ferroviario, Postal, Tendido_Electrico, Oleoducto, Otro;
	};
	
	public static enum MedioDeTransporteTypeEnum {
		// Aereo, maritimo, ferroviario, terrestre
		Aereo, Maritimo_Fluvial_Lacustre, Terrestre, Ferroviario, Postal, Tendido_Electrico, Oleoducto, Otro;
	};
	
	public static enum ParticipanteTypeEnum {
		exportador, importador, agente, courier, consignatario, representanteLegal, transportista, contacto;
	};
	public static enum PersonaTypeEnum {
		fisica, juridica;
	};
	public static enum DocPersonaTypeEnum {
		rut, passport;
	};

}
