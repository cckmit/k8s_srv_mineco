package com.egoveris.edt.base.service.impl.sector;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.Estructura;
import com.egoveris.edt.base.service.sector.ISectorService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.EstructuraDTO;
import com.egoveris.sharedsecurity.base.model.Reparticion;
import com.egoveris.sharedsecurity.base.model.Sector;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.base.model.sector.SectorDTO;
import com.egoveris.sharedsecurity.base.repository.impl.SectorRepository;

@Service("sectorService")
@Transactional
public class SectorServiceImpl implements ISectorService {

	private DozerBeanMapper mapper = new DozerBeanMapper();

	@Autowired
	private SectorRepository sectorRepository;

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<SectorDTO> buscarSectoresPorReparticion(ReparticionDTO reparticion) {
		return ListMapper.mapList(sectorRepository.findByReparticionEstadoRegistroTrue(reparticion.getId(), new Date()),
				mapper, SectorDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SectorDTO> buscarSectoresMesaPorReparticion(ReparticionDTO reparticion) {
		return ListMapper.mapList(
				sectorRepository.findByReparticionEstadoRegistroTrueAndSectorMesa(reparticion.getId(), new Date(), "S"), mapper,
				SectorDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SectorDTO> buscarSectoresMesaVirtualPorReparticion(ReparticionDTO reparticion) {
		return ListMapper.mapList(
				sectorRepository.findByReparticionEstadoRegistroTrueAndMesaVirtualTrue(reparticion.getId(), new Date()), mapper,
				SectorDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SectorDTO> buscarSectoresPorCodigoDeSector(String sectorCodigo) {
		return ListMapper.mapList(sectorRepository.findByCodigoContaining(sectorCodigo), mapper, SectorDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SectorDTO> buscarSectoresPorDescripcion(String sectorDescripcion) {
		return ListMapper.mapList(sectorRepository.findByNombreContaining(sectorDescripcion), mapper, SectorDTO.class);
	}

	@Override
	public void guardarSector(SectorDTO sector) {
		try {
			sectorRepository.save(mapper.map(sector, Sector.class));
		} catch (ConstraintViolationException ex) {
			throw new NegocioException("Error al generar el ID del sector a guardar. Ya existe el mismo", ex);
		}
	}

	@Override
	public void modificarSector(SectorDTO sector) {
		sectorRepository.save(mapper.map(sector, Sector.class));
	}

	@Override
	public boolean esSectorActivo(SectorDTO sector) {
		Date fechaIni = sector.getVigenciaDesde() == null ? new Date(Long.MIN_VALUE) : sector.getVigenciaDesde();
		Date fechaFin = sector.getVigenciaHasta() == null ? new Date(Long.MAX_VALUE) : sector.getVigenciaHasta();
		if (fechaIni.after(new Date()) || fechaFin.before(new Date())) {
			return false;
		}

		return sector.getEstadoRegistro();
	}

	@Override
	@Transactional
	public SectorDTO getSectorbyId(Integer id) {
		SectorDTO retorno = null;
		final Sector resultado = sectorRepository.findOne(id);
		if (resultado != null) {
			retorno = mapper.map(resultado, SectorDTO.class);
		}
		return retorno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SectorDTO> listaSectores() {
		return ListMapper.mapList(sectorRepository.findAll(), mapper, SectorDTO.class);
	}

	@Override
	public SectorDTO buscarSectorPorRepaYSector(String sectorCodigo, ReparticionDTO reparticion) {
		SectorDTO retorno = null;
		final Sector resultado = sectorRepository.findByReparticionAndCodigoAndEstadoRegistroTrue(reparticion.getId(),
				sectorCodigo, new Date());
		if (resultado != null) {
			retorno = mapper.map(resultado, SectorDTO.class);
		}
		return retorno;
	}

	@Override
	public void crearSectorInterno(ReparticionDTO reparticion, List<EstructuraDTO> estructuras) {
		Sector sectorInterno = new Sector();
		Reparticion mappedReparticion = mapper.map(reparticion, Reparticion.class);
		sectorInterno.setReparticion(mappedReparticion);
		/*
		 * Me fijo si cargo o no el sector de acuerdo a la ESTRUCTURA de la nueva
		 * reparticion
		 */
		sectorInterno.setNombre(reparticion.getNombre());
		sectorInterno.setCodigo(reparticion.getCodigo());
		sectorInterno.setSectorMesa(ConstantesSesion.SI);
		sectorInterno.setUsuarioCreacion(reparticion.getUsuarioCreacion());
		sectorInterno.setCalle(reparticion.getCalleReparticion());
		sectorInterno.setVigenciaDesde(reparticion.getVigenciaDesde());
		sectorInterno.setVigenciaHasta(reparticion.getVigenciaHasta());
		sectorInterno.setEsArchivo(false);
		sectorInterno.setEstadoRegistro(true);
		sectorInterno.setMesaVirtual(false);
		sectorRepository.save(sectorInterno);
	}

	private boolean esSectorInternoAls(Estructura estructura, List<Estructura> estructuras) {
		for (Estructura est : estructuras) {
			if (est.getId().equals(estructura.getId())) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SectorDTO> buscarSectoresPorReparticiones(List<ReparticionDTO> reparticiones) {

		List<Reparticion> mappedReparticionList = ListMapper.mapList(reparticiones, mapper, Reparticion.class);
		List<Integer> idRepartiones = new ArrayList<>();

		for (Reparticion r : mappedReparticionList) {
			idRepartiones.add(r.getId());
		}

		return ListMapper.mapList(sectorRepository.findByReparticion_IdIn(idRepartiones), mapper, SectorDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SectorDTO> buscarSectoresPorCodigoDeSectorComodin(String codigoSector) {
		return ListMapper.mapList(sectorRepository.findByCodigoContaining(codigoSector), mapper, SectorDTO.class);
	}

}
