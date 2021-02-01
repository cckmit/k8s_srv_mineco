//console.log("llamando al require('wsUtils');");
/*
var myXML = '<animals>     <dog>          <name>Rufus</name>          <breed>labrador</breed>     </dog>     <dog>          <name>Marty</name>          <breed>whippet</breed>     </dog>     <cat name="Matilda"/></animals>';
var xml = "<root><child><textNode>First &amp; Child</textNode></child><child><textNode>Second Child</textNode></child><testAttrs attr1='attr1Value'/></root>";
*/

var repo = require('repoClient');

var xml = new String(repo.obtenerContenido(repo.obtenerArchivoLocal('c:\\Desarrollo\\ResponseWS.xml'))); 
xml.replace("\r\n","");
/*
//console.log("xml -> "+xml);
*/

/*
var xml = '<?xml version="1.0" encoding="utf-8"?>';
xml+='<soap:Envelope xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">';
xml+='<soap:Body>';
xml+='   <m:AllPlayersWithYellowCardsResponse xmlns:m="http://footballpool.dataaccess.eu">';
xml+='      <m:AllPlayersWithYellowCardsResult>';
xml+='         <m:tPlayersWithCards>';
xml+='            <m:sName>Mile Jedinak</m:sName>';
xml+='            <m:iYellowCards>2</m:iYellowCards>';
xml+='            <m:iRedCards>0</m:iRedCards>';
xml+='            <m:sTeamName>Australia</m:sTeamName>';
xml+='            <m:sTeamFlag>http://footballpool.dataaccess.eu/images/flags/au.gif</m:sTeamFlag>';
xml+='            <m:sTeamFlagLarge>http://footballpool.dataaccess.eu/images/flags/au.png</m:sTeamFlagLarge>';
xml+='         </m:tPlayersWithCards>';
xml+='         <m:tPlayersWithCards>';
xml+='            <m:sName>Mark Milligan</m:sName>';
xml+='            <m:iYellowCards>1</m:iYellowCards>';
xml+='            <m:iRedCards>0</m:iRedCards>';
xml+='            <m:sTeamName>Australia</m:sTeamName>';
xml+='            <m:sTeamFlag>http://footballpool.dataaccess.eu/images/flags/au.gif</m:sTeamFlag>';
xml+='            <m:sTeamFlagLarge>http://footballpool.dataaccess.eu/images/flags/au.png</m:sTeamFlagLarge>';
xml+='         </m:tPlayersWithCards>';
xml+='         <m:tPlayersWithCards>';
xml+='            <m:sName>Tim Cahill</m:sName>';
xml+='            <m:iYellowCards>1</m:iYellowCards>';
xml+='            <m:iRedCards>0</m:iRedCards>';
xml+='            <m:sTeamName>Australia</m:sTeamName>';
xml+='            <m:sTeamFlag>http://footballpool.dataaccess.eu/images/flags/au.gif</m:sTeamFlag>';
xml+='            <m:sTeamFlagLarge>http://footballpool.dataaccess.eu/images/flags/au.png</m:sTeamFlagLarge>';
xml+='         </m:tPlayersWithCards>';
xml+='      </m:AllPlayersWithYellowCardsResult>';
xml+='   </m:AllPlayersWithYellowCardsResponse>';
xml+='</soap:Body>';
xml+='</soap:Envelope>';
*/

//console.log('pepe -> '+xml);
var myJsonObject=xml2json.parser(xml);

var selector = ".sname";

var resultObj = JSONSelect.match(selector, myJsonObject);

//console.log(typeof resultObj);
//console.log(resultObj);
//console.log('- - - - -');

JSONSelect.forEach(selector, myJsonObject, function(resultObj) {
//    console.log(typeof resultObj);
 //   console.log(resultObj);
  //  console.log('- - - - -');
    console.log(' -> '+JSON.stringify(resultObj, null, ' '));
    //$('body').append('<p>' + $.trim(JSON.stringify(resultObj, null, ' ')) + '</p>');
});


//console.log("-->");
//console.log(JSON.stringify(myJsonObject));


/*
var xml = "<root><child><textNode>First &amp; Child</textNode></child><child><textNode>Second Child</textNode></child><testAttrs attr1='attr1Value'/></root>";

var x2js = new X2JS();
console.log(x2js.xml_str2json(xml));
*/

/*
var _wsdl_ = require('wsdlUtils');


//var service = _wsdl_.inspeccionar('http://mule-test.gcaba.everis.int:8880/EEServices/generar-caratula?wsdl');
var service = _wsdl_.inspeccionar('http://footballpool.dataaccess.eu/data/info.wso?wsdl');

println("volvio service -> "+service);

var consulta = crearReq('AllPlayersWithYellowCards');

consulta.bSortedByName='true';
consulta.bSortedByYellowCards='false';

var response = service.AllPlayersWithYellowCards(consulta);


println("volvio bien -> "+ JSON.stringify(response));
*/

/*

*/

/*
var consulta = crearReq('generarExpedienteElectronicoCaratulacion');

consulta.request.apellido='Shin';

println("Para el chino que esta mirando...");
println("volvio bien -> "+ JSON.stringify(consulta));
*/

/*
println("volvio bien -> "+consulta.request.apellido);

var response = service.generarExpedienteElectronicoCaratulacion(consulta);

if (!response._Envelope._Body._Fault) {
	println("TODO OK....." + response._Envelope._Body._generarExpedienteElectronicoCaratulacionResponse._return);
} else {
	println("Hubo el siguiente error: "+response._Envelope._Body._Fault._faultstring);
}

*/