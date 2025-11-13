// En fer clic en Autor/ISBN/Idioma, configurar el camp i mostrar-lo
document.querySelectorAll('.dropdown-item[data-field]').forEach(item => {
    item.addEventListener('click', e => {
        e.preventDefault();

        const field = item.dataset.field; // 'author' / 'isbn' / 'idioma'
        const hidden = document.getElementById('field');
        const input = document.getElementById('searchInput');
        const group = document.getElementById('searchGroup');
        const toggle = document.getElementById('dropdownAdvanced');

        hidden.value = field;
        toggle.textContent = `Cerca avançada: ${item.textContent}`;

        // Eliminar un possible selector d'idioma anterior
        const oldSelect = document.getElementById('idiomaSelect');
        if (oldSelect) {
            oldSelect.remove();
        }

        // Reset de l’input text
        input.classList.remove('d-none');
        input.value = '';
        input.placeholder = '';
        input.removeAttribute('pattern');
        input.removeAttribute('title');
        input.name = 'q'; // el backend rep sempre 'field' + 'q'

        // Mostrar el grup d’entrada
        group.classList.remove('d-none');

        if (field === 'isbn') {
            // Cerca per ISBN
            input.placeholder = 'Introdueix l\'ISBN';
            input.setAttribute('pattern', '^(97[89])\\d{10}$');
            input.setAttribute('title', 'Introdueix un ISBN-13 vàlid (13 dígits, sense guions)');
            input.focus();

        } else if (field === 'author') {
            input.placeholder = 'Introdueix l\'autor';
            input.focus();

        } else if (field === 'idioma') {
            input.classList.add('d-none');

            const select = document.createElement('select');
            select.id = 'idiomaSelect';
            select.name = 'q';
            select.className = 'form-select form-select-sm me-2';
            select.innerHTML = `
                <option value="">Selecciona un idioma</option>
                <option value="Català">Català</option>
                <option value="Castellà">Castellà</option>
            `;


            const btn = group.querySelector('button');
            group.insertBefore(select, btn);

            select.focus();
        }
    });
});



