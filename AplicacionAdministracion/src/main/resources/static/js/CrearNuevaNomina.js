// Cargar empleados al iniciar la página
document.addEventListener('DOMContentLoaded', function() {
    cargarEmpleados();
});

function cargarEmpleados() {
    fetch('/api/empleados')
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
        <td><input type="text" step="0.01" min="0" max="100" name="porcentaje" placeholder="%"></td>
        <td><input type="text" step="0.01" min="0" name="devengo" placeholder="€"></td>
        <td><input type="text" step="0.01" min="0" name="deduccion" placeholder="€"></td>
        <td><span class="importe-calculado">0.00 €</span></td>
        <td><button type="button" onclick="eliminarLinea(this)">Eliminar</button></td>
    `;
    tbody.appendChild(newRow);

    // Agregar event listeners para calcular automáticamente
    const inputs = newRow.querySelectorAll('input[name="porcentaje"], input[name="devengo"], input[name="deduccion"]');
    inputs.forEach(input => {
        input.addEventListener('input', function() {
            calcularImporteLinea(newRow);
        });
    });
}

function calcularImporteLinea(row) {
    const porcentaje = parseFloat(row.querySelector('input[name="porcentaje"]').value) || 0;
    const devengo = parseFloat(row.querySelector('input[name="devengo"]').value) || 0;
    const deduccion = parseFloat(row.querySelector('input[name="deduccion"]').value) || 0;

    let importe = 0;

    if (porcentaje > 0) {
        // Necesitaríamos el salario del empleado para calcular esto
        // Esto se hará mejor en el backend
        importe = 0; // Se calculará en el servidor
    } else {
        importe = devengo - deduccion;
    }

    row.querySelector('.importe-calculado').textContent = importe.toFixed(2) + ' €';
}

function eliminarLinea(button) {
    button.closest('tr').remove();
}

function validarLineas() {
    const lineasRows = document.querySelectorAll('#lineas tbody tr');
    let valido = true;

    lineasRows.forEach(row => {
        const concepto = row.querySelector('input[name="concepto"]').value;
        const porcentaje = row.querySelector('input[name="porcentaje"]').value;
        const devengo = row.querySelector('input[name="devengo"]').value;
        const deduccion = row.querySelector('input[name="deduccion"]').value;

        if (!concepto) {
            alert('El concepto es obligatorio en todas las líneas');
            valido = false;
            return;
        }

        // Al menos uno de los tres campos (porcentaje, devengo o deducción) debe tener valor
        if (!porcentaje && !devengo && !deduccion) {
            alert('Cada línea debe tener al menos un valor (porcentaje, devengo o deducción)');
            valido = false;
            return;
        }
    });

    return valido;
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

    // Validar líneas
    if (!validarLineas()) {
        return;
    }

    // Recoger las líneas
    const lineas = [];
    const lineasRows = document.querySelectorAll('#lineas tbody tr');

    lineasRows.forEach(row => {
        const linea = {
            concepto: row.querySelector('input[name="concepto"]').value,
            porcentaje: row.querySelector('input[name="porcentaje"]').value,
            devengo: row.querySelector('input[name="devengo"]').value,
            deduccion: row.querySelector('input[name="deduccion"]').value
        };
        lineas.push(linea);
    });

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
                return response.json().then(err => {
                    throw new Error(err.message || 'Error al crear nómina');
                });
            }
            return response.json();
        })
        .then(data => {
            alert('Nómina creada con éxito!');
            window.location.href = '/listado';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al crear nómina: ' + error.message);
        });
}