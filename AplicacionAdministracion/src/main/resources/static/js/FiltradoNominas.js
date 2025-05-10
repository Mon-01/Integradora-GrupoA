document.getElementById("filtroForm").addEventListener('submit', async (event) => {
    event.preventDefault();

    const nombre = document.getElementById("nombre").value;
    const fecha = document.getElementById("fecha").value;
    const departamento = document.getElementById("departamento").value;

    const filtroData = {
        nombre: nombre,
        departamento: departamento,
        fecha: fecha

    };

    try {
        const response = await fetch('/api/admin/filtroNominas', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(filtroData)
        });

        if (response.ok) {
            const nominas = await response.json();
            // Actualizar la tabla con los resultados
            actualizarTabla(nominas);
        } else {
            const error = await response.text();
            document.getElementById('error').innerText = error;
        }
    } catch (err) {
        document.getElementById('error').innerText = "Error inesperado: " + err.message;
    }
});

function actualizarTabla(nominas) {
    const tbody = document.querySelector('#nominasTable tbody');
    tbody.innerHTML = ''; // Limpiar tabla

    nominas.forEach(nomina => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${nomina.id}</td>
            <td>${nomina.fecha ? new Date(nomina.fecha).toLocaleDateString() : 'Sin fecha'}</td>
            <td>${nomina.nombre}</td>
            <td>${nomina.departamento}</td>
            <td>€ ${nomina.total ? nomina.total.toFixed(2) : '0.00'}</td>
            <td>
                <button data-id="${nomina.id}" class="btn btn-danger eliminar-btn">Eliminar</button>
            </td>
        `;
        tbody.appendChild(row);
    });

    // Reasignar eventos a los botones de eliminar
    $('.eliminar-btn').off('click').click(function() {
        // Tu código existente para eliminar
    });
}