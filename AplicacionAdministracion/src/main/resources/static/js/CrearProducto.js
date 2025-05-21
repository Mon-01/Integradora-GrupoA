document.getElementById('catalogoForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const archivo = document.getElementById('archivoJson').files[0];
    if (!archivo) {
        alert("Selecciona un archivo JSON primero.");
        return;
    }

    const lector = new FileReader();
    lector.onload = function (event) {
        try {
            const contenido = JSON.parse(event.target.result);

            fetch('/api/admin/importar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(contenido)
            })
                .then(res => res.text())
                .then(texto => {
                    document.getElementById('resultado').innerText = texto;
                })
                .catch(error => {
                    document.getElementById('resultado').innerText = 'Error: ' + error.message;
                });

        } catch (e) {
            document.getElementById('resultado').innerText = 'Error de sintaxis JSON: ' + e.message;
        }
    };

    lector.readAsText(archivo);
});