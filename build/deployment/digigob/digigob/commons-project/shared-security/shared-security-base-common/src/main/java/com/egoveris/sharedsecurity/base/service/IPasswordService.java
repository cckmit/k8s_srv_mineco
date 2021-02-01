package com.egoveris.sharedsecurity.base.service;


public interface IPasswordService {

  /**
   * Este metodo genera un password aleatorio alfanumerico de la cantidad de
   * digitos dada.
   * 
   * @param cantidadDigitos
   *          del password que se desea generar
   * @return Un <b>String</b> con la password generada
   */
  String generarPasswordAleatoria(int cantidadDigitos);

  /**
   * Codifica un string en base64 y encripta en formato SHA1. Al String de
   * retorno se le concatena el tipo de ecriptacion, "SHA"
   * 
   * @param password
   *          que se desea codificar
   * @return Un <b>String</b> con las password codificada
   * @throws NegocioException
   *           en caso de ocurrir un error durante la codificacion
   */
  String codificarSHA(String password);
}
