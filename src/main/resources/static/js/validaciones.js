$(document).ready(function () {
  //SOLO NUMEROS EN CAMPO DNI

  $('*[id*=dni]').each(function () {
    $(this).keypress(function (event) {
      var charCode = event.which ? event.which : event.keyCode;
      if (charCode < 48 || charCode > 57) {
        event.preventDefault();
      }
    });

    //AL PRESIONAR EDITAR USUARIO CHEQUEA QUE SE ENVIE POR GET LA INFO Y ABRE
    // EL MODAL...

    if (window.location.href.indexOf("edit") > -1) {
      $("#editUser").modal("show");
    }
  });
});
