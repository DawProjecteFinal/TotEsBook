document.addEventListener("DOMContentLoaded", function () {
    var modalEl = document.getElementById("errorReservaModal");

 
    if (modalEl) {
        var myModal = new bootstrap.Modal(modalEl);
        myModal.show();
    }
});


