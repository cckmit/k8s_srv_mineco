package com.egoveris.edt.ws.service;

public interface IExternalPeriodoLicenciaService {

	/**
	 * Metodo que devuelve true si el usuario dado
	 * está con licencia activa para la fecha dada
	 * 
	 * @param username
	 * @return
	 */
  public boolean licenciaActiva(String username);
  
  /**
   * Metodo que devuelve el apoderado del usuario dado,
   * quien se supone que está con licencia. Si el apoderado
   * también está con licencia, retorna el apoderado
   * del apoderado (en forma recursiva) que no esté con licencia
   * 
   * @param username
   * @return
   */
  public String obtenerApoderadoUsuarioLicencia(String username);
  
}
