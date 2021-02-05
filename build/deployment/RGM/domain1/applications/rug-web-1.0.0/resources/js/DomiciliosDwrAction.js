// Provide a default path to dwr.engine
if (typeof this['dwr'] == 'undefined') this.dwr = {};
if (typeof dwr['engine'] == 'undefined') dwr.engine = {};
if (typeof dwr.engine['_mappedClasses'] == 'undefined') dwr.engine._mappedClasses = {};

if (typeof this.SectorEconomico != 'function') {
  this.SectorEconomico = function() {
    this.sectorEconomico = null;
    this.seleccionada = false;
    this.usoRestringido = null;
    this.idSubsectorEconomico = 0;
    this.tieneComentario = null;
    this.idClase = 0;
    this.idSubrama = 0;
    this.idRama = 0;
    this.subsectorEconomico = null;
    this.subRama = null;
    this.comentario = null;
    this.porcentaje = 0;
    this.observacion = null;
    this.idSectorEconomico = 0;
    this.sectorSubsector = null;
    this.idScian = null;
    this.clase = null;
    this.idRegistroAe = null;
    this.rama = null;
  }
  this.SectorEconomico.$dwrClassName = 'SectorEconomico';
  this.SectorEconomico.$dwrClassMembers = {};
  this.SectorEconomico.$dwrClassMembers.sectorEconomico = {};
  this.SectorEconomico.$dwrClassMembers.seleccionada = {};
  this.SectorEconomico.$dwrClassMembers.usoRestringido = {};
  this.SectorEconomico.$dwrClassMembers.idSubsectorEconomico = {};
  this.SectorEconomico.$dwrClassMembers.tieneComentario = {};
  this.SectorEconomico.$dwrClassMembers.idClase = {};
  this.SectorEconomico.$dwrClassMembers.idSubrama = {};
  this.SectorEconomico.$dwrClassMembers.idRama = {};
  this.SectorEconomico.$dwrClassMembers.subsectorEconomico = {};
  this.SectorEconomico.$dwrClassMembers.subRama = {};
  this.SectorEconomico.$dwrClassMembers.comentario = {};
  this.SectorEconomico.$dwrClassMembers.porcentaje = {};
  this.SectorEconomico.$dwrClassMembers.observacion = {};
  this.SectorEconomico.$dwrClassMembers.idSectorEconomico = {};
  this.SectorEconomico.$dwrClassMembers.sectorSubsector = {};
  this.SectorEconomico.$dwrClassMembers.idScian = {};
  this.SectorEconomico.$dwrClassMembers.clase = {};
  this.SectorEconomico.$dwrClassMembers.idRegistroAe = {};
  this.SectorEconomico.$dwrClassMembers.rama = {};
  this.SectorEconomico.createFromMap = function(map) {
    var obj = new this();
    for(prop in map) if (map.hasOwnProperty(prop)) obj[prop] = map[prop];
    return obj;
  }
  dwr.engine._mappedClasses['SectorEconomico'] = this.SectorEconomico;
}

if (window['dojo']) dojo.provide('dwr.interface.DomiciliosDwrAction');

if (typeof this['DomiciliosDwrAction'] == 'undefined') DomiciliosDwrAction = {};

DomiciliosDwrAction._path = '/portal/dwr';

/**
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getLocale = function(callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getLocale', arguments);
};

/**
 * @param {interface mx.gob.economia.dgi.tuempresa.empresa.service.DomiciliosService} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.setDomiciliosServices = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'setDomiciliosServices', arguments);
};

/**
 * @param {class mx.gob.economia.dgi.framework.common.domicilios.dto.MunicipDeleg} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getLocalidadesConagua = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getLocalidadesConagua', arguments);
};

/**
 * @param {class mx.gob.economia.dgi.tuempresa.empresa.dto.PersonaMoral} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getDomicilioEmpresa = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getDomicilioEmpresa', arguments);
};

/**
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getEstados = function(callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getEstados', arguments);
};

/**
 * @param {class mx.gob.economia.dgi.framework.common.domicilios.dto.Estado} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getMunicipios = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getMunicipios', arguments);
};

/**
 * @param {class mx.gob.economia.dgi.framework.common.domicilios.dto.MunicipDeleg} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getColonias = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getColonias', arguments);
};

/**
 * @param {class mx.gob.economia.dgi.framework.common.domicilios.dto.MunicipDeleg} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getLocalidades = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getLocalidades', arguments);
};

/**
 * @param {class mx.gob.economia.dgi.framework.common.domicilios.dto.MunicipDeleg} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getColoniaLocalidad = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getColoniaLocalidad', arguments);
};

/**
 * @param {class mx.gob.economia.dgi.framework.common.domicilios.dto.Colonia} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getCodigoC = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getCodigoC', arguments);
};

/**
 * @param {class mx.gob.economia.dgi.framework.common.domicilios.dto.Localidad} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getCodigoL = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getCodigoL', arguments);
};

/**
 * @param {class mx.gob.economia.dgi.framework.common.domicilios.dto.Domicilio} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getCodigoPostalDomicilio = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getCodigoPostalDomicilio', arguments);
};

/**
 * @param {interface mx.gob.economia.dgi.framework.common.domicilios.service.DomiciliosService} p0 a param
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.setDomicilioServiceFwk = function(p0, callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'setDomicilioServiceFwk', arguments);
};

/**
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getSession = function(callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getSession', arguments);
};

/**
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getRequest = function(callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getRequest', arguments);
};

/**
 * @param {function|Object} callback callback function or options object
 */
DomiciliosDwrAction.getResponse = function(callback) {
  return dwr.engine._execute(DomiciliosDwrAction._path, 'DomiciliosDwrAction', 'getResponse', arguments);
};


