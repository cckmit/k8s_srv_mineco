<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="section"></div>
<main>
    <center>
        <div class="container">
            <div class="row">
                <div class="col s12">
                    <div class="card">
                        <div class="card-content">
                            <span class="card-title">Encuesta de Satisfación</span>
                            <div class="row">
                                <div class="col s2"></div>
                                <div class="col s8">
                                    <s:if test="hasFieldErrors()">
                                        <div class="row">
                                            <div class="col s12 action-error">												
                                                <s:fielderror />
                                            </div>
                                        </div>
                                    </s:if>
                                    <s:elseif test="hasActionErrors()">
                                        <div class="row">
                                            <div class="col s12 action-error">
                                                <s:actionerror />
                                            </div>
                                        </div>
                                    </s:elseif>
                                    <s:else>
                                        <s:form namespace="encuesta" id="frmSurvey" action="guardar.do" theme="simple">																				
                                            <div class="row note">
                                                Gracias por realizar la encuesta de satisfaccion del Registro de Garantias Mobilirias.
                                                Nos será de gran ayuda para mejorar los servicios que ofrecemos.
                                                Los datos que en ella se consignen se tratarán de forma anónima.
                                            </div>
                                            <div class="row">
                                                <div class="input-field col s12">
                                                    <label>¿Es un usuario del sistema RUG?</h2></label>
                                                </div>
                                                <div class="input-field col s12">
                                                    <s:radio name="registro" id="registro" list="#{'1':'Si','2':'No'}" value="1" />
                                                    <s:fielderror fieldName="registro" />
                                                </div>  									    	
                                            </div>
                                            <div class="row">
                                                <div class="input-field col s12">
                                                    <s:textfield name="tiempo" id="tiempo" required="true" maxlength="50" />
                                                    <label for="tiempo">¿Cuantos meses tiene de utilizar el sistema?</label>
                                                    <s:fielderror fieldName="tiempo" />
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="input-field col s12">
                                                    <label>¿Cómo califica la facilidad en el proceso de registro?</h2></label>
                                                </div>
                                                <div class="input-field col s12">
                                                    <s:radio name="proceso" id="proceso" list="#{'1':'Muy facil','2':'Facil','3':'Normal','4':'Dificil','5':'Muy Dificil'}" value="1" />
                                                    <s:fielderror fieldName="proceso" />
                                                </div>  									    	
                                            </div>
                                            <div class="row">
                                                <div class="input-field col s12">									        													
                                                    <label for="facilidad">¿Cómo califica la facilidad para encontrar el trámite a realizar (Inscripción, Modificación, etc)?</label>									    		
                                                </div>
                                                <div class="input-field col s12">
                                                    <s:radio name="facilidad" id="facilidad" list="#{'1':'Muy facil','2':'Facil','3':'Normal','4':'Dificil','5':'Muy Dificil'}" value="1" />
                                                    <s:fielderror fieldName="facilidad" />
                                                </div>  
                                            </div>
                                            <div class="row">
                                                <div class="input-field col s12">									        	
                                                    <label for="pagos">¿Utiliza pago en ventanilla o en linea?</label>									    		
                                                </div>
                                                <div class="input-field col s12">
                                                    <s:radio name="pagos" id="pagos" list="#{'1':'Si','2':'No'}" value="1" />
                                                    <s:fielderror fieldName="pagos" />
                                                </div> 
                                            </div>
                                            <div class="row">
                                                <div class="input-field col s12">									        	
                                                    <label for="subusuarios">¿Utiliza sub usuarios?</label>									    		
                                                </div>
                                                <div class="input-field col s12">
                                                    <s:radio name="subusuarios" id="subusuarios" list="#{'1':'Si','2':'No'}" value="1" />
                                                    <s:fielderror fieldName="subusuarios" />
                                                </div>  
                                            </div>
                                            <div class="row">
                                                <div class="input-field col s12">
                                                    <s:textarea name="comentario" id="comentario" required="true" cols="20" rows="3" wrap="true" />
                                                    <label for="comentario">¿Tiene un comentario acerca del sistema?</label>
                                                    <s:fielderror fieldName="comentario" />
                                                </div>
                                            </div>
                                            <div class="row">
                                                <div class="input-field col s12">
                                                    <s:textfield name="total" id="total" required="true" maxlength="50" />
                                                    <label for="total">Value de 0 a 10 su satisfacción global del servicio</label>
                                                    <s:fielderror fieldName="total" />
                                                </div>
                                            </div>									 	
                                            <center>
                                                <div class='row'>												
                                                    <s:submit type="button" align="center" cssClass="btn btn-large waves-effect indigo" id="btnEnviar" value="Enviar"/>
                                                </div>
                                            </center>								          	
                                            <s:hidden value="%{bToken}" id="bToken" name="bToken" />											
                                        </s:form>
                                    </s:else>
                                </div>
                                <div class="col s2"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </center>
</main>
<div class="section"></div>
