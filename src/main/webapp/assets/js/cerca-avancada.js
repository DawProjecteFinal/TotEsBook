document.addEventListener('DOMContentLoaded', function () {
    const dropdownLinks = document.querySelectorAll('a.dropdown-item[data-field]');
    const fieldInput    = document.getElementById('field');
    const searchGroup   = document.getElementById('searchGroup');
    const searchInput   = document.getElementById('searchInput');
    const idiomaSelect  = document.getElementById('idiomaSelect');

    if (!dropdownLinks.length || !fieldInput || !searchGroup || !searchInput) {
        return;
    }

    dropdownLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            const field = this.dataset.field; 
            fieldInput.value = field;

            searchGroup.classList.remove('d-none');

            searchInput.value = '';
            if (idiomaSelect) {
                idiomaSelect.value = '';
            }


            if (field === 'idioma' && idiomaSelect) {
                searchInput.classList.add('d-none');   
                idiomaSelect.classList.remove('d-none'); 
            } else {

                searchInput.classList.remove('d-none');
                if (idiomaSelect) {
                    idiomaSelect.classList.add('d-none');
                }


                switch (field) {
                    case 'autor':
                        searchInput.placeholder = 'Cerca per autor';
                        break;
                    case 'isbn':
                        searchInput.placeholder = 'Cerca per ISBN';
                        break;
                    default:
                        searchInput.placeholder = 'Cerca avançada';
                        break;
                }


                searchInput.focus();
            }
        });
    });


    if (idiomaSelect) {
        idiomaSelect.addEventListener('change', function () {
            searchInput.value = this.value; // ca / es
        });
    }


    const advancedForm = document.getElementById('advancedSearch');
    if (advancedForm) {
        advancedForm.addEventListener('submit', function (e) {
            if (fieldInput.value === 'idioma') {
                // Si no s'ha triat idioma, no enviar
                if (!searchInput.value) {
                    e.preventDefault();
                    if (idiomaSelect) {
                        idiomaSelect.focus();
                    }
                }
            }
        });
    }
});


