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

if (window['dojo']) dojo.provide('dwr.interface.ContactoDwrAction');

if (typeof this['ContactoDwrAction'] == 'undefined') ContactoDwrAction = {};

ContactoDwrAction._path = '/portal/dwr';

/**
 * @param {interface mx.gob.economia.dgi.tuempresa.common.contacto.service.ContactoService} p0 a param
 * @param {function|Object} callback callback function or options object
 */
ContactoDwrAction.setContactoService = function(p0, callback) {
  return dwr.engine._execute(ContactoDwrAction._path, 'ContactoDwrAction', 'setContactoService', arguments);
};

/**
 * @param {class mx.gob.economia.dgi.tuempresa.common.dto.Mensaje} p0 a param
 * @param {function|Object} callback callback function or options object
 */
ContactoDwrAction.contacto = function(p0, callback) {
  return dwr.engine._execute(ContactoDwrAction._path, 'ContactoDwrAction', 'contacto', arguments);
};

/**
 * @param {function|Object} callback callback function or options object
 */
ContactoDwrAction.getSession = function(callback) {
  return dwr.engine._execute(ContactoDwrAction._path, 'ContactoDwrAction', 'getSession', arguments);
};

/**
 * @param {function|Object} callback callback function or options object
 */
ContactoDwrAction.getRequest = function(callback) {
  return dwr.engine._execute(ContactoDwrAction._path, 'ContactoDwrAction', 'getRequest', arguments);
};

/**
 * @param {function|Object} callback callback function or options object
 */
ContactoDwrAction.getResponse = function(callback) {
  return dwr.engine._execute(ContactoDwrAction._path, 'ContactoDwrAction', 'getResponse', arguments);
};


