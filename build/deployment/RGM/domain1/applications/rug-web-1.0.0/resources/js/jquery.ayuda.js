$.fn.ayuda = function (opt) {
	
	var def = {
			url: 'ayuda.do',
			ico: 'ayuda.gif',
			width: 250,
			height: 150
	}
	
	$.extend(def, opt)

    $(this).each(function () {
        var id = $(this).attr('id');

        if(!id || id == '')
            return;

        $(this).after(
            $('<img src="' + def.ico + '" title="¿Desea obtener ayuda?" id="boton_' + $(this).attr('id') + '">')
                .css({
                    'cursor': 'pointer',
                    'margin-left': '5px'
                })
                .click(function () {
                    tb_show(def.url + '?llave=' + $(this).attr('id').replace('boton_', '') + '&width=' + def.width + '&height=' + def.height)
                    return false;
                })
        );
    })
    
    return this;
}