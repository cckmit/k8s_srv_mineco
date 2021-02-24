<%@ taglib prefix="s" uri="/struts-tags"%>
<table width="100%">
	<tr>
		<td class="encabezadoTablaResumen" width="100%">
			<s:text name="Activacion de cuenta" />
		</td>
	</tr>
</table>

<s:actionmessage />
<s:actionerror />

<s:form namespace="usuario" action="activAcreedor.do" theme="simple">
<INPUT type="hidden" name="personaFisica.datosContacto.emailPersonal" value="<s:property value="personaFisica.datosContacto.emailPersonal"/>" id="personaFisica.datosContacto.emailPersonal">
<INPUT type="hidden" name="personaFisica.nombre" value="<s:property value="personaFisica.nombre"/>" id="personaFisica.nombre">
<INPUT type="hidden" name="personaFisica.apellidoPaterno" value="<s:property value="personaFisica.apellidoPaterno"/>" id="personaFisica.apellidoPaterno">
<INPUT type="hidden" name="personaFisica.apellidoMaterno" value="<s:property value="personaFisica.apellidoMaterno"/>" id="personaFisica.apellidoMaterno">
	<table class="formularioSide">
		<tr>
			<td width="85%">
				<table width="100%" border="0" cellpadding="2" cellspacing="2">
					<tr>
						<td class="textoGeneralAzul" valign="bottom" align="right"><b class="textoGeneralRojo"></b><s:text name="Usuario :" /></td>
						<td class="campo" valign="bottom">
							<s:property value="personaFisica.nombre"/> <s:property value="personaFisica.apellidoPaterno"/> <s:property value="personaFisica.apellidoMaterno"/>
						</td>
					</tr>
					
					<tr>
						<td class="textoGeneralAzul" valign="bottom" align="right"><b class="textoGeneralRojo"></b><s:text name="Clave de usuario :" /></td>
						<td class="campo" valign="bottom">
							<s:property value="personaFisica.datosContacto.emailPersonal"/>
						</td>
					</tr>
					
					<tr>
						<td colspan="3">
							<div id=scrolltable style=" background: #FFFFFF; overflow:auto;
							border-right: #6699CC 1px solid; border-top: #999999 1px solid;
							border-left: #6699CC 1px solid; border-bottom: #6699CC 1px solid;
							scrollbar-arrow-color : #999999; scrollbar-face-color : #666666;
							scrollbar-track-color :#3333333 ; 
							height:200px; left: 100; top: 20; width: 80%">
							
							<table cellpadding="3" cellspacing="2" width="100%">
							<tr>
									<td class="texto_general" colspan="2" align="justify" >
											<s:text name="usuario.terminos.politicas"/>	
									</td>
							</tr>										
							</table>		
							<table cellpadding="3" cellspacing="2" width="100%">
						<tr>
							<td align="center" class="tituloInteriorRojo"><br>
							<b><s:text name="publico.ayuda.terminos.de.uso.titulo" /></b></td>
						</tr>
					</table>

					<table cellpadding="3" cellspacing="2" width="100%" border="0">
						<tr>
							<td align="justify"><br class="texto_general">
							<s:text name=" " /></td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Estimado usuario, esta página establece los" /> "<s:text name="Términos y Condiciones" />" <s:text name="que regulan el uso del Registro Único de Garantías Mobiliarias, en adelante el RUG, de conformidad con lo dispuesto por el artículo 32 bis 2 y siguientes del Código de Comercio, como una sección del Registro Público de Comercio, en el cual se pueden inscribir todo tipo de garantías mobiliarias, privilegios especiales y derechos de retención sobre bienes muebles para garantizar cualesquiera créditos que una persona reciba y que funciona a través del sitio de Internetwww.rug.gob.mx." />
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="La persona que haga uso del sitio de Internet arriba citado, en adelante" /> "<s:text name="el usuario" />" <s:text name=", debe aceptar los presentes Términos y Condiciones, por lo que está obligada a leerlos y analizarlos detenidamente antes de continuar, ya que por el simple uso o acceso a cualquiera de las páginas que integran el RUG se entenderá que acepta y está de acuerdo en obligarse a cumplir con estos Términos y Condiciones. En caso de no estar de acuerdo en sujetarse a los mismos, debe suspender inmediatamente su uso y abstenerse de acceder al RUG, así como a la información, contenidos o programas que forman parte del mismo. Estos Términos y Condiciones podrán ser modificados libremente por la Secretaría de Economía, por lo que se recomienda ampliamente al usuario realizar una consulta periódica de los mismos para conocer las disposiciones vigentes al momento de acceder a la página." />
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="El usuario tendrá acceso al RUG a través de su dominio de Internetwww.rug.gob.mxy a través de sus funcionalidades y contenidos podrá realizar diversos trámites registrales puestos a su disposición por la Secretaría de Economía. El usuario acepta ser el único responsable de la información enviada al RUG a través del sistema, así como de la existencia y veracidad de la documentación relacionada con el acto jurídico que se inscriba y de la identidad de las personas que lo celebran." /> 
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Firma electrónica." /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="En los términos del Reglamento del Registro Público de Comercio es necesario que el usuario firme electrónicamente las operaciones que realiza en el RUG  en las que ingresa información a la base de datos nacional del RUG, entendiéndose satisfecho dicho requisito al utilizar las firmas electrónicas avanzadas autorizadas o reconocidas por la Secretaría de Economía." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Al momento de firmar electrónicamente cualquier operación en este sitio de Internet, el firmante acepta conocer y se adhiere plenamente y sin reservas a los presentes Términos y Condiciones, así como el contenido, la existencia y veracidad de la información enviada y documentación relativa a la operación que se lleva a cabo. Los actos y/o documentos inscritos en el RUG con el uso de firma electrónica avanzada, producirán los mismos efectos que las leyes otorgan a los documentos firmados autógrafamente. La firma electrónica avanzada utilizada para firmar lainformación, anotaciones, registro,  documento electrónico,  en su caso, cualquier mensaje de datos permite dar certeza de que el mismo ha sido emitido por el firmante de manera tal que su contenido le es atribuible al igual que las consecuencias jurídicas que de él deriven." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="El usuario del RUG reconoce y acepta que los datos en forma electrónica consignados en un mensaje de datos, o adjuntados o lógicamente asociados al mismo por cualquier tecnología, que son utilizados para identificar al usuario en relación con el mensaje de datos e indicar que el usuario aprueba la información contenida en el mensaje de datos, producen los mismos efectos jurídicos que la firma autógrafa, siendo admisible como prueba en juicio." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="En todo momento el usuario deberá:" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="I. Cumplir las obligaciones derivadas del uso de la firma electrónica;" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="II. Actuar con diligencia y establecer los medios razonables para evitar la utilización no autorizada de los datos de creación de la firma;" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="III. Cuando se emplee un certificado en relación con una firma electrónica, actuar con diligencia razonable para cerciorarse de que todas las declaraciones que haya hecho en relación con el certificado, con su vigencia, o que hayan sido consignadas en el mismo, son exactas. El firmante será responsable de las consecuencias jurídicas que deriven por no cumplir oportunamente las obligaciones previstas en el presente artículo, y" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="IV. Responder por las obligaciones derivadas del uso no autorizado de su firma." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="De conformidad con el artículo 31 del Reglamento del Registro Público de Comercio" /> "<s:text name="Todo asiento quedará realizado en el momento en que sea firmado electrónicamente por quien lo realiza cuya fecha y hora quedará establecida en el sello digital de tiempo contenido en la boleta que emita el Sistema" />" <s:text name=", para lo cual el usuario en todo momento en el que realiza una operación con su firma electrónica, declara lo siguiente:" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="1.	Reconozco como propia, veraz y auténtica la información avalada por mi Firma Electrónica Avanzada emitida por el Servicio de Administración Tributaria, la emitida por un Prestador de Servicios de Certificación acreditado por la Secretaría de Economía, o la emitida o reconocida por la Secretaría de Economía y que la información presentada por este medio producirá los mismos efectos que las leyes otorgan a los documentos firmados autógrafamente y, en consecuencia, tendrá el mismo valor probatorio que las disposiciones aplicables les otorgan a éstos." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="2.	Manifiesto conocer el contenido del artículo 32 bis  4 del Código de Comercio que dispone:" /> "<s:text name="los acreedores, instancias de autoridad o personas facultadas que realicen inscripciones o anotaciones serán responsables para todos los efectos legales de la existencia y veracidad de la información y documentación relativa a inscripciones y anotaciones que lleven a cabo.  Si una institución financiera o persona moral autorizada realiza la inscripción o anotación y es parte del contrato como acreedor prendario, fideicomisario, fiduciario, será responsable independientemente del empleado o funcionario que realiza la inscripción.  Los acreedores, instancias de autoridad o personas facultadas para llevar a cabo inscripciones o anotaciones en el Registro, responden por los daños y perjuicios que se pudieran originar por tal motivo.  El afectado podrá optar por reclamar daños y perjuicios que se le ocasionen mediante su cálculo y acreditación o por sanción legal.  La sanción legal se calculará y exigirá en un monto equivalente a 1,000  veces el salario mínimo diario general vigente en el Distrito Federal, sin perjuicio de las sanciones penales o administrativas a que hubiere lugar." />" 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="3.	Acepto expresamente que cualquier información inscrita de garantías mobiliarias que se envíe a través de este medio, así como los siguientes datos personales: nombre, razón o denominación social, teléfono, correo electrónico y domicilio, formarán parte de un expediente público." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="El usuario que lleva a cabo altas, bajas y cambios en la aplicación de" /> "<s:text name="alta de acreedor" />" <s:text name="en el RUG, en todo momento en el que realiza dichas operaciones con su firma electrónica, adicionalmente declara lo siguiente:" />
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="1. Declaro que cuento con poder general para actos de dominio o administración otorgado por la Persona Física/Moral a la cual represento." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="2. Manifiesto conocer el alcance del artículo 19 A del Código Fiscal de la Federación, que dispone que " /> "<s:text name="se presumirá sin que se admita prueba en contrario, que los documentos digitales que contengan firma electrónica avanzada de las personas morales, fueron presentados por el administrador único, el presidente del consejo de administración o la persona o personas, cualquiera que sea el nombre con el que se les designe, que tengan conferida la dirección general, la gerencia general o la administración de la persona moral de que se trate, en el momento en el que se presentaron los documentos digitales." />"
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Uso del RUG" /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Los contenidos del RUG tales como textos, gráficos, imágenes, logos, íconos de botón, software y cualquier otro contenido, deberán utilizarse por el usuario únicamente para los fines para los cuales ha sido creado.  El uso no autorizado del contenido del RUG  será sancionado en términos de la legislación aplicable.  De igual forma se sancionará penalmente al que  de manera dolosa o con fines de lucro interrumpa o interfiera las comunicaciones alámbricas, inalámbricas o de fibra óptica, sean telefónicas o satelitales, por medio de las cuales se transfieran señales de datos." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Al hacer uso de la página, el usuario se obliga a:" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="1.	Usar la página conforme a los presentes Términos y Condiciones. " /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="2.	Realizar operaciones legítimas, ya sean personales o a nombre de quien esté autorizado para actuar legalmente. " /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="3.	Proporcionar la información veraz, precisa y legítima que se le solicite para el RUG. " /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="4.	No suplantar a ninguna persona física o moral, ni utilizar o proporcionar una dirección de correo electrónico falsa. " /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="5.	No hacer uso indebido o inapropiado de la página, tales como realizar operaciones falsas; enviar, distribuir, difundir, anunciar o divulgar información obscena, indecente, ilegal o que amenace, degrade, difame, hostigue o acose a terceros en sus bienes o en sus personas o limite el ejercicio de sus derechos; utilice recursos, medios, programas, archivos que pudieren dañar el equipo de cómputo de otras personas o afectar los derechos de autor, las marcas o patentes relacionadas con el software utilizado en la página o en el equipo de cómputo de terceros; utilizarla con propósitos comerciales o cualquier otro acto, acción o propósito que de cualquier forma dañe, impida o limite el uso del RUG. " /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="6.	Utilizar la página bajo su propio riesgo conforme a los presentes Términos y Condiciones. " /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="El usuario puede utilizar el RUG para inscribir todo tipo de garantías mobiliarias, privilegios especiales y derechos de retención sobre bienes muebles para garantizar cualesquiera créditos que una persona reciba, así como para consultar la informacióna través del sistema mediante el cual pueden realizarse las siguientes operaciones:" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="1. Aviso Preventivo" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="2. Inscripción de Garantía Mobiliaria" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="3. Modificación de registro" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="4. Transmisión de Garantías Mobiliaria" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="5. Rectificación por error" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="6. Renovación de Vigencia" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="7. Cancelación de Garantías Mobiliaria" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="8. Consulta" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="9. Solicitud de Certificación" /> 
								<br>
							</td>
						</tr>	
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="De conformidad con el Código de Comercio, así como del Reglamento del Registro Público de Comercio, los acreedores, instancias de autoridad o personas facultadas que realicen inscripciones o anotaciones sobre garantías mobiliarias, serán responsables para todos los efectos legales de la existencia y veracidad de la información y documentación relativa a inscripciones y anotaciones que lleven a cabo." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Si una institución financiera o persona moral autorizada realiza la inscripción o anotación y es parte del contrato como acreedor prendario, fideicomisario o fiduciario, será responsable, independientemente del empleado o funcionario que realiza la inscripción." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Será responsabilidad de quien realice una inscripción o anotación llevar a cabo la rectificación  de los errores materiales o de concepto que las mismas contengan.  Se entiende que se comete un  error de concepto, cuando al expresar en la inscripción o anotación, alguno de los contenidos convencionales o formales de la garantía o acto objeto a registro, se altere o varíe su sentido en virtud de un juicio equivocado de quien la lleve a cabo.  Todos los demás errores se considerarán materiales." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Los acreedores, instancias de autoridad o personas facultadas para llevar a cabo inscripciones o anotaciones en el Registro, responden por los daños y perjuicios que se pudieren originar por tal motivo.  El afectado podrá optar por reclamar los daños y perjuicios que se le ocasionen mediante su cálculo y acreditación o por sanción legal.  La sanción legal se calculará y exigirá  en un monto equivalente a 1,000  veces al Salario Mínimo Diario General vigente en el Distrito Federal." /> 
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Prohibiciones" /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Se prohíbe a los usuarios violar o intentar violar la seguridad del RUG, incluyendo pero no limitándose a:  A) acceder a datos que no estén destinados al usuario o entrar en un servidor o cuenta cuyo acceso no esté autorizado al usuario,  B) evaluar o probar la vulnerabilidad  de un sistema o red, o violar las medidas de seguridad o identificación sin la adecuada autorización y  C) intentar impedir el servicio a cualquier usuario, anfitrión o red, incluso mediante el envío de virus al RUG o bloqueos del sistema." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Las violaciones de la seguridad del sistema o de la red pueden resultar en responsabilidades civiles o penales en términos de la legislación aplicable." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Se prohíbe específicamente el uso del RUG  para cualquiera de los siguientes fines:" /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="-	Usar el RUG para cualquier propósito distinto al fin para el cual fue creado." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="-	Impedir el adecuado funcionamiento del RUG, mediante el uso de cualquier mecanismo, software o rutina." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="-	Intentar descifrar, separar u obtener el código fuente de cualquier programa de software que comprenda o constituya una parte del RUG." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="-	Interrumpir o interferir las comunicaciones alámbricas, inalámbricas o de fibra óptica, sean telefónicas o satelitales, por medio de las cuales se transfieran señales de datos." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="-	Intentar legar información o el sistema páginas rurales." /> 
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Propiedad intelectual." /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Los derechos de propiedad intelectual respecto del sistema informático del RUG,  así como de todos los servicios, contenidos, derechos de explotación, reproducción, distribución, y modificación o transformación corresponden única y exclusivamente a la Secretaría de Economía los usuarios del RUG, no adquieren bajo ninguna circunstancia derechos o autorización alguna para uso distinto al establecido en los presentes Términos y Condiciones." /> 
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Bienes y servicios de terceros enlazados." /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Respecto de los servicios y contenidos que prestan terceros dentro o mediante enlace al RUG, (tales como ligas, banners y botones), el Gobierno Federal se limita exclusivamente para conveniencia del usuario a: A) Informar al usuario sobre los mismos y  B) Proporcionar un medio para poner en contacto  al usuario con terceros.  Salvo en los casos de enlaces con dependencias federales, el Gobierno Federal no es ni podrá ser considerado como proveedor de los bienes y/o servicios que se ofrecen en dichas páginas y/o sitios.  La inclusión de dichas páginas y/o enlaces no implica la aprobación, respaldo, patrocinio, recomendación o garantía por parte del Gobierno Federal de los servicios y bienes que se comercializan en los mismos ni del contenido de dichas páginas, por lo tanto, el usuario será el responsable de revisar las políticas de privacidad que requieran esos sitios de internet." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Las imágenes que aparecen tanto en la página de inicio del sitio de Internet del RUG, como en todo el material de difusión del RUG, incluyendo trípticos y carteles, han sido reproducidas de conformidad con la fracción segunda del artículo 148 de la Ley Federal del Derecho de Autor, en virtud de que las mismas no cuentan con un titular de derechos patrimoniales identificado, que hubiere prohibido expresamente dicha reproducción. En cumplimiento con lo establecido por el artículo antes mencionado, se hacen de su conocimiento las fuentes (páginas de Internet) de las cuales fueron obtenidas dichas imágenes:" /> 
									<ul> 
										<li> http://diarioautomotriz.com; http://balancannoticias.blogspot.com</li>
										<li> http://portaleconomicoweb.blogspot.com </li>
										<li> http://bioseguridad.blogspot.com/2008_08_01_archive.html</li>
										<li> http://www.crisisenergetica.org/forum/viewtopic.php?howtopic=11033&mode=&show=10&page=6 </li>
										<li> http://www.nauticexpo.es/prod/sodifa-esca/maquina-tendedora-con-sistema-de-corte-br-para-astilleros-navales-31971-204182.html y</li>
										<li> http://web.softideascorp.com/Venta_manten.html</li>
									</ul>
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Uso de funciones y archivos <b>Cookies</b>." /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="El usuario que tenga acceso al RUG, acuerda recibir las cookies transmitidas por los servidores del Gobierno Federal.  Una " /> "<s:text name="Cookie" />" <s:text name="es un componente que almacena datos para asegurar la confidencialidad de los datos que pasan por la red de internet, con el fin de garantizar que la información no sea observada por otros usuarios." />
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Clave de usuario y contraseña." /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="En todo momento, el usuario es el responsable único y final de mantener en secreto sus cuentas, contraseñas personales, claves de acceso y números confidenciales con los cuales tenga acceso a los servicios y contenidos del RUG, y es el único  responsable del mal uso que se les dé a éstas." /> 
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Actualización de la información." /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Es responsabilidad del usuario mantener actualizada la información concerniente a su persona y Garantías Mobiliarias que haya registrado en el Sistema del RUG." /> 
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Términos de uso adicionales." /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="La información, datos, manifestaciones, actos y hechos jurídicos que se deriven por el uso del RUG, estarán sujetos a la legislación que resulte aplicable, en relación a cada trámite que se realice." /> 
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Validez Jurídica." /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Tendrán plena validez jurídica las inscripciones, modificaciones, transmisiones, rectificaciones de error, renovaciones de vigencia cancelaciones, anotaciones, avisos preventivos, consultas y certificaciones que se realicen en el RUG  con apego a lo dispuesto por los artículos 32 bis  1 al 32 bis 9 y por el Capítulo V  del Reglamento del Registro de Público de Comercio." /> 
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Modificaciones." /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Tendrán plena validez jurídica las inscripciones, modificaciones, transmisiones, rectificaciones de error, renovaciones de vigencia cancelaciones, anotaciones, avisos preventivos, consultas y certificaciones que se realicen en el RUG  con apego a lo dispuesto por los artículos 32 bis  1 al 32 bis 9 y por el Capítulo V  del Reglamento del Registro de Público de Comercio." /> 
								<br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="El Gobierno Federal tendrá el derecho de modificar en cualquier momento el contenido y alcance de los presentes Términos y Condiciones lo cual se hará del conocimiento del usuario de manera oportuna, por lo cual deberá leerlos atentamente cada vez que se le haga de conocimiento y pretenda utilizar el RUG." /> 
								<br>
							</td>
						</tr>
						
						<tr>
							<td align="left" class="tituloInteriorRojo">
								<br><s:text name="Leyes aplicables y Jurisdicción en caso de controversia." /><br>
							</td>
						</tr>
						<tr>
							<td align="justify" class="texto_general">		
								<br>
									<s:text	name="Para la interpretación, cumplimiento, ejecución así como para el caso de controversia derivada de la interpretación o cumplimiento de los presentes Términos y Condiciones, de las Políticas de Privacidad o de cualquier otro documento relevante del sitio de internet del RUG serán aplicables las leyes federales de los Estados Unidos Mexicanos y competentes los tribunales de la Ciudad de México, Distrito Federal, renunciando expresamente a cualquier otro fuero o jurisdicción que pudiera corresponderle en razón de su domicilio presente o futuro." /> 
								<br>
							</td>
						</tr>
					</table>

							</div>		
						</td>
					</tr>
					<tr>
						<td class="terminos" colspan="2">
							<br /><br />
							<s:fielderror fieldName="registroUsuario.terminos" />
							<s:checkbox name="registroUsuario.terminos" value="registroUsuario.terminos"
									cssClass="campo_general" />
							<s:text name="usuario.field.leido" />&nbsp;														
							<s:url id="uri" action="help.do" encode="true" namespace="/comun/publico">
								<s:param name="llave">terminosUsoModal</s:param>
								<s:param name="keepThis">true</s:param>
								<s:param name="TB_iframe">true</s:param>
								<s:param name="height">500</s:param>
								<s:param name="width">750</s:param>
							</s:url> 																					
							
							<a onclick="showTerminos()" style="cursor: pointer;"><u><s:text name="common.terminos" /></u></a>
							
							<s:text name="usuario.field.ylas" />&nbsp;
							<s:url id="uri" action="help.do" encode="true" namespace="/comun/publico">
								<s:param name="llave">politicasPrivacidadModal</s:param>
								<s:param name="keepThis">true</s:param>
								<s:param name="TB_iframe">true</s:param>
								<s:param name="height">500</s:param>
								<s:param name="width">750</s:param>
							</s:url> 
							<a onclick="showPoliticas()" style="cursor: pointer;"><u><s:text name="common.politicas" /></u></a>
						</td>
					</tr>
					
					<tr>
						<td class="btnSubmit" colspan="2">
							<s:url var="uri"  value="%{getContextPath()}/imgs/%{getText('common.img.i18n.path')}/aceptar.gif" />
					
							<s:submit type="button" src="%{uri}" name="Aceptar" value="Aceptar" align="center" />
						</td>
						
					</tr>
				</table>			
			</td>
			<td width="15%" valign="top">
<!--				<img width="162" src="<%= request.getContextPath() %>/imgs/imgrecortada_jugueteria.jpg" /> -->
			</td>			
		</tr>			
	</table>
</s:form>
<script type="text/javascript">
function showTerminos() {
    var URL="<%=request.getContextPath()%>/comun/publico/help.do?llave=terminosUsoModal";
    window.open(URL,"ventana1","width=500,height=750,scrollbars=1");
}
function showPoliticas() {
    var URL="<%=request.getContextPath()%>/comun/publico/help.do?llave=politicasPrivacidadModal";
    window.open(URL,"ventana1","width=500,height=750,scrollbars=1");
}
</script>