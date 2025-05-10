// Cargar empleados al iniciar la página
document.addEventListener('DOMContentLoaded', function() {
    cargarEmpleados();
});

function cargarEmpleados() {
    fetch('/api/empleados') // Asegúrate de tener este endpoint
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById('empleadoId');
            data.forEach(empleado => {
                const option = document.createElement('option');
                option.value = empleado.id_empleado;
                option.textContent = `${empleado.nombre} ${empleado.apellido}`;
                select.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error al cargar empleados:', error);
        });
}

function agregarLinea() {
    const tbody = document.querySelector('#lineas tbody');
    const newRow = document.createElement('tr');
    newRow.innerHTML = `
                <td><input type="text" name="concepto" required></td>
                <td><input type="number" step="0.01" name="importe" required></td>
                <td><button type="button" onclick="eliminarLinea(this)">Eliminar</button></td>
            `;
    tbody.appendChild(newRow);
}

function eliminarLinea(button) {
    button.closest('tr').remove();
}

function guardarNomina() {
    const fecha = document.getElementById('fecha').value;
    const empleadoId = document.getElementById('empleadoId').value;

    if (!fecha) {
        alert('La fecha es obligatoria');
        return;
    }

    if (!empleadoId) {
        alert('Debe seleccionar un empleado');
        return;
    }

    // Recoger las líneas
    const lineas = [];
    const lineasRows = document.querySelectorAll('#lineas tbody tr');

    if (lineasRows.length === 0) {
        alert('Debe añadir al menos una línea');
        return;
    }

    let errorEnLineas = false;
    lineasRows.forEach(row => {
        const concepto = row.querySelector('input[name="concepto"]').value;
        const importe = row.querySelector('input[name="importe"]').value;

        if (!concepto || !importe) {
            errorEnLineas = true;
            return;
        }

        lineas.push({
            concepto: concepto,
            cantidad: parseFloat(importe)
        });
    });

    if (errorEnLineas) {
        alert('Todos los campos de las líneas deben estar completos');
        return;
    }

    const nominaData = {
        fecha: fecha,
        empleado: {
            id_empleado: parseInt(empleadoId)
        },
        lineas: lineas
    };

    fetch('/api/admin/nomina', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(nominaData)
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => { throw new Error(text) });
            }
            return response.json();
        })
        .then(data => {
            alert('Nómina creada con éxito!');
            window.location.href = '/listado';
        })
        .catch(error => {
            alert('Error al crear nómina: ' + error.message);
        });
}