var ventanaporque = null;

function abrirporque()
{
  if(ventanaporque == null || ventanaporque.closed)
  {
    ventanaporque = window.open("porquereservar.htm", "porque", "width=320,height=305,menubar=no,toolbar=no,resizable=no,scrollbars=no,status=no"); 
  }
  else
  {
    ventanaporque.focus();
  };
}