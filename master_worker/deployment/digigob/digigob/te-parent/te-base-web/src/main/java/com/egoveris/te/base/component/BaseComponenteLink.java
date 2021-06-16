package com.egoveris.te.base.component;

import com.egoveris.te.base.exception.PermisoException;
import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;
import com.egoveris.te.base.util.ConfiguracionInicialModuloEEFactory;

import org.springframework.beans.factory.annotation.Autowired;




public abstract class BaseComponenteLink {
    @Autowired
    private ConfiguracionInicialModuloEEFactory configuracionInicialModuloEEFactory;

    public BaseComponenteLink() {
    }

    abstract protected boolean checkeoPermiso(String permisoFuncionId)
        throws PermisoException;

    /**
    * Se chequea que el <code>java.lang.String</code> permisoFuncionId, para un <code>ExpedienteElectronico</code> que indica el sistema externo,
    * para la funcion <b>EE_TIPO_EXPEDIENTE_SISTEMA_EXTERNO</b>=BAC
     * @param <code>java.lang.String</code>permisoFuncionId
     * @return <code>java.lang.Boolean</code> TRUE, para este componente la regla es configurada por sistema externo.
     * @throws Exception
     */
    protected boolean checkeoPermisoPorFuncion(ParametrosSistemaExternoDTO parametrosSistemaExterno ,String permisoFuncionId)
        throws PermisoException {
        return trimmed(permisoFuncionId) && (parametrosSistemaExterno.getEsactivo() && permisoFuncionId.trim().contains(parametrosSistemaExterno.getCodigo().trim()));
    }

    public ConfiguracionInicialModuloEEFactory getConfiguracionInicialModuloEEFactory() {
        return this.configuracionInicialModuloEEFactory;
    }

    public void setConfiguracionInicialModuloEEFactory(ConfiguracionInicialModuloEEFactory configuracionInicialModuloEEFactory) {
        this.configuracionInicialModuloEEFactory = configuracionInicialModuloEEFactory;
    }

    private boolean trimmed(String trimmed) {
        return ((trimmed != null) && !"".equalsIgnoreCase(trimmed) && !"null".equalsIgnoreCase(trimmed));
    }
}
