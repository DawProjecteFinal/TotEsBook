document.addEventListener('DOMContentLoaded', function () {
    const dropdownLinks = document.querySelectorAll('a.dropdown-item[data-field]');
    const fieldInput    = document.getElementById('field');
    const searchGroup   = document.getElementById('searchGroup');
    const searchInput   = document.getElementById('searchInput');
    const idiomaSelect  = document.getElementById('idiomaSelect');

    if (!dropdownLinks.length || !fieldInput || !searchGroup || !searchInput) {
        return;
    }

    // Cuando se hace clic en Autor / Idioma / ISBN
    dropdownLinks.forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();

            const field = this.dataset.field;  // autor / idioma / isbn
            fieldInput.value = field;

            // Siempre mostramos el grupo
            searchGroup.classList.remove('d-none');

            // Reset de valores
            searchInput.value = '';
            if (idiomaSelect) {
                idiomaSelect.value = '';
            }

            // Si es IDIOMA → mostramos el select y ocultamos el input de texto
            if (field === 'idioma' && idiomaSelect) {
                searchInput.classList.add('d-none');   // input oculto
                idiomaSelect.classList.remove('d-none'); // select visible
            } else {
                // Para autor / isbn → input visible, select oculto
                searchInput.classList.remove('d-none');
                if (idiomaSelect) {
                    idiomaSelect.classList.add('d-none');
                }

                // Placeholder segons tipus de cerca
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

                // Focus al input
                searchInput.focus();
            }
        });
    });

    // Quan es triï un idioma al select, copiem el valor al input hidden q (searchInput)
    if (idiomaSelect) {
        idiomaSelect.addEventListener('change', function () {
            searchInput.value = this.value; // ca / es
        });
    }

    // Opcional: validar abans d'enviar el formulari per idioma
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


