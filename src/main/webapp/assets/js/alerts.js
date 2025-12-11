/* 
 * 
 *  @Author: Equip TotEsBook
 */

/*Activar Toast*/
document.addEventListener("DOMContentLoaded", function () {
    const successToastEl = document.getElementById('successToast');
    if (successToastEl) {
        const successToast = new bootstrap.Toast(successToastEl);
        successToast.show();
    }

    const errorToastEl = document.getElementById('errorToast');
    if (errorToastEl) {
        const errorToast = new bootstrap.Toast(errorToastEl);
        errorToast.show();
    }
});

/*Confirmar cancelació*/
function confirmarCancelacio(idReserva) {
    if (confirm("Estàs segur que vols cancel·lar aquesta reserva?")) {
        document.getElementById("formCancel_" + idReserva).submit();
    }
}


document.addEventListener('DOMContentLoaded', function () {
    var sancioAlert = document.getElementById('alertSancio');
    if (sancioAlert) {
        
        setTimeout(function () {
            var bsAlert = bootstrap.Alert.getOrCreateInstance(sancioAlert);
            bsAlert.close();
        }, 15000);
    }
});