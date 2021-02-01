function nuevaCondicion(callback) {
    var newCondition = new Condicion(callback);
    newCondition.ingresarNuevoDato();
}

var Condicion = function (callbackFormularioAceptar){
    obtenerWidget = function(){ return this.widget;};
    obtenerLayout = function(){return this.layout;};
    obtenerConditionArray = function(){return this.conditionArray;} ;
    
    this.ingresarNuevoDato = function(accion){
        var callbackAceptar = function (datosDelFormulario) {   
        	var hayVariable = (datosDelFormulario[0] != null && !datosDelFormulario[0].replace(/\s/g,"") == "");
        	var hayValor = (datosDelFormulario[2] != null && !datosDelFormulario[2].replace(/\s/g,"") == "");
        	
        	if (hayVariable && hayValor) {
	            var condicionTemportal = datosDelFormulario[0] + datosDelFormulario[1] + "'" + datosDelFormulario[2] + "'";
	            this.conditionArray = [condicionTemportal];
	            crearFormularioCondicion(condicionTemportal);
	            agregarDatosDelFormularioCondicion(condicionTemportal);
        	}
        	else {
        		zAu.cmd0.alert("Debe ingresar una variable y un valor", "Error");
				return 'stop';
        	}
        };
        nuevoDato("Nueva Condición", obtenerChildren(), ["variable", "conector", "valor"], callbackAceptar, function(){});
        createContextMenu(jq('$variableWindow $variable')[0]);
    };

    ingresarDato = function(accion){
        var callbackCancelar = obtenerCallbackCancelar(accion);
        var callbackAceptar = function (datosDelFormulario) {   
        	var hayVariable = (datosDelFormulario[0] != null && !datosDelFormulario[0].replace(/\s/g,"") == "");
        	var hayValor = (datosDelFormulario[2] != null && !datosDelFormulario[2].replace(/\s/g,"") == "");
        	
        	if (hayVariable && hayValor) {
        		var condicionTemportal = datosDelFormulario[0] + datosDelFormulario[1] + "'" + datosDelFormulario[2] + "'";
                this.conditionArray.push(" " + accion + " " + condicionTemportal);
                agregarDatosDelFormularioCondicion(condicionTemportal);
        	}
        	else {
        		zAu.cmd0.alert("Debe ingresar una variable y un valor", "Error");
				return 'stop';
        	}
        };

        nuevoDato("Nueva Condición", obtenerChildren(), ["variable", "conector", "valor"], callbackAceptar, callbackCancelar);
        createContextMenu(jq('$variableWindow $variable')[0]);
    }

crearFormularioCondicion = function(condicion){
    this.layout = new zul.box.Vlayout({
            id: 'conditionalVars',
            height: '220px',
            style: 'overflow:auto;',
            children: []
        });

    var divBtn = new zul.wgt.Div({
        align: 'center',
        width: '100%',
        children: [
            new zul.box.Hlayout({
                width: '100%',
                children: [
                    new zul.wgt.Button({
                        label: 'Aceptar',
                        listeners: {
                            onClick: function(evt) {
                                var stringResultado = obtenerCondicionResultado();
                                callbackFormularioAceptar(stringResultado);
                                zk.Widget.$("$nuevaCondicion").detach();
                            }
                        }
                    }),
                    new zul.wgt.Separator({
                        width: '20px'
                    }),
                    new zul.wgt.Button({
                        label: 'Cancelar',
                        listeners: {
                            onClick: function(evt) {
                                zk.Widget.$("$nuevaCondicion").detach();
                            }
                        }
                    })
                ]
            })
        ]
    });

    var vlayout = new zul.box.Vlayout({ children: [
        new zul.wgt.Separator({ heigth: '15px' }), 
        new zul.box.Hlayout({
            children: [
                    new zul.wgt.Separator({ width: '10px' }),
                    this.layout
                ]
            }), divBtn
        ] 
    });

    this.widget = new zul.wnd.Window({
        id: 'nuevaCondicion',
        title: 'Nueva Condición',
        closable: false,
        border: 'normal',
        mode: 'modal',
        width: '430px',
        style: 'vertical-align:top',
        parent: zk.Widget.$("$info").getPage(),
        children: [vlayout]
    });

    jq('#z_messages').html(this.widget);

};
 agregarDatosDelFormularioCondicion= function(datosDelFormulario){
    //NuevaBotonera
    var conditionIndex = obtenerLayout().nChildren;
    obtenerLayout().appendChild(new zul.box.Hlayout({
        id: 'hLayout' + conditionIndex,
        children: [
            new zul.inp.Textbox({ id:'textBox'+ conditionIndex, value: datosDelFormulario }),
            new zul.wgt.Button({ label: 'Y', id: 'ButtonY' + conditionIndex,
                listeners: {
                    onClick: function(evt) {
                        obtenerWidget().$f('ButtonY' + conditionIndex).setDisabled(true);
                        obtenerWidget().$f('ButtonO' + conditionIndex).hide();  
                        ingresarDato('&&');                                                         
                    }
                }
            }),
            new zul.wgt.Button({
                label: 'O',
                id: 'ButtonO' + conditionIndex,
                listeners: {
                    onClick: function(evt) {
                        obtenerWidget().$f('ButtonO' + conditionIndex).setDisabled(true);
                        obtenerWidget().$f('ButtonY' + conditionIndex).hide();
                        ingresarDato('||');
                    }
                }
            }),
            new zul.wgt.Button({
                label: 'Eliminar condicion',
                listeners: {
                    onClick: function(evt) {
                        obtenerConditionArray().splice(conditionIndex, 1);
                        obtenerLayout().removeChild(obtenerWidget().$f('hLayout' + conditionIndex));
                        if (conditionIndex == 0) {
                            obtenerWidget().detach();
                        } else if (conditionIndex == layout.nChildren) {
                            var idActual = conditionIndex - 1;
                            obtenerWidget().$f('ButtonY' + idActual).show();
                            obtenerWidget().$f('ButtonY' + idActual).setDisabled(false);
                            obtenerWidget().$f('ButtonO' + idActual).show();
                            obtenerWidget().$f('ButtonO' + idActual).setDisabled(false);
                        }
                    }
                }
            })
        ]
    }));
    
    jq('$nuevaCondicion $textBox'+ conditionIndex)[0].focus();
   
};

obtenerCallbackCancelar= function(accion){
    var callbackCancelar; 
    var nChildren = this.obtenerLayout().nChildren - 1;
    if(accion == '&&'){
        callbackCancelar = function(){
            obtenerWidget().$f('ButtonY' + nChildren).setDisabled(false);
            obtenerWidget().$f('ButtonO' + nChildren).show();      
        }
    }else if (accion && accion == '||'){
        callbackCancelar = function(){
            obtenerWidget().$f('ButtonO' + nChildren).setDisabled(false);
            obtenerWidget().$f('ButtonY' + nChildren).show();      
        }
    }

    return callbackCancelar;
}; 
    obtenerChildren = function(){ 
        return [
            new zul.wgt.Label({value: 'Variable: '}),
            new zul.inp.Textbox({id: 'variable', type: 'text', value: '', cols: 15, rows: 10, maxlength: '100'}),
            new zul.wgt.Label({value: 'Conector: '}),
            new zul.inp.Combobox({id: 'conector', cols: 15, rows: 10, readonly: true, value: " != ", 
                children: [
                       new zul.inp.Comboitem({label: " != ", description: ' Distinto de '}),
                       new zul.inp.Comboitem({label: " == ", description: ' Igual a '}),
                       new zul.inp.Comboitem({label: " > ", description: ' Mayor a '}),
                       new zul.inp.Comboitem({label: " >= ", description: ' Mayor o igual a '}),
                       new zul.inp.Comboitem({label: " < ", description: ' Menor a '}),
                       new zul.inp.Comboitem({label: " <= ", description: ' Menor o igual a '}),
                    ]
                }),
            new zul.wgt.Label({value: 'Valor: '}),
            new zul.inp.Textbox({id: 'valor', type: 'text', value: '', cols: 15, rows: 10, maxlength: '100'})
        ];
    };

    obtenerCondicionResultado= function(){
        var condicion = "";
        var condR = this.conditionArray;

        for (i = 0 ; i < condR.length; i++) {
            if(i == 0){
                condicion = '( ' + condR[i] + ' )';
            } else if( i < condR.length - 1 ){
                var cond = condR[i].substring(0, 4) + '(' + condR[i].substring(4, condR[i].length);
                condicion = condicion.substring(0, condicion.length - i) + cond + ')' + condicion.substring(condicion.length - i, condicion.length);
            } else {
                condicion = condicion.substring(0, condicion.length - i) + condR[i] + condicion.substring(condicion.length - i, condicion.length);
            }
        }

        this.conditionArray = [];     
      return condicion;
    };
}