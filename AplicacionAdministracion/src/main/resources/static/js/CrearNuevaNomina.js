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
// Cargar empleados al iniciar la página
document.addEventListener('DOMContentLoaded', function() {
    cargarEmpleados();

    // Actualizar salario cuando cambia el empleado
    document.getElementById('empleadoId').addEventListener('change', function() {
        actualizarSalarioAnual();
    });
});

let salarioAnual = 0;

function actualizarSalarioAnual() {
    const empleadoId = document.getElementById('empleadoId').value;
    if (!empleadoId) {
        document.getElementById('salarioAnual').value = '';
        salarioAnual = 0;
        return;
    }

    fetch(`/api/empleados/${empleadoId}`)
        .then(response => response.json())
        .then(empleado => {
            salarioAnual = parseFloat(empleado.salarioAnual);
            document.getElementById('salarioAnual').value = salarioAnual.toFixed(2) + ' €';

            // Recalcular todas las líneas
            document.querySelectorAll('#lineas tbody tr').forEach(row => {
                calcularImporteLinea(row);
            });
            calcularTotal();
        })
        .catch(error => {
            console.error('Error al obtener empleado:', error);
        });
}

function agregarLinea() {
    const tbody = document.querySelector('#lineas tbody');
    const newRow = document.createElement('tr');
    newRow.innerHTML = `
        <td><input type="text" name="concepto" required></td>
        <td class="tipo-calculado">
            <select name="tipo" required>
                <option value="devengo">Devengo (Suma)</option>
                <option value="deduccion">Deducción (Resta)</option>
            </select>
            <select name="tipoCalculo" class="tipo-calculacion" required>
                <option value="porcentaje">Porcentaje</option>
                <option value="fijo">Importe Fijo</option>
            </select>
            <input type="number" step="0.01" min="0" name="valorCalculo" required>
        </td>
        <td><span class="importe-calculado">0.00 €</span></td>
        <td><button type="button" onclick="eliminarLinea(this)">Eliminar</button></td>
    `;
    tbody.appendChild(newRow);

    // Agregar event listeners para calcular automáticamente
    const inputs = newRow.querySelectorAll('select[name="tipo"], select[name="tipoCalculo"], input[name="valorCalculo"]');
    inputs.forEach(input => {
        input.addEventListener('change', function() {
            calcularImporteLinea(newRow);
            calcularTotal();
        });
        input.addEventListener('input', function() {
            calcularImporteLinea(newRow);
            calcularTotal();
        });
    });
}

function calcularImporteLinea(row) {
    const tipo = row.querySelector('select[name="tipo"]').value;
    const tipoCalculo = row.querySelector('select[name="tipoCalculo"]').value;
    const valor = parseFloat(row.querySelector('input[name="valorCalculo"]').value) || 0;

    let importe = 0;

    if (tipoCalculo === 'porcentaje') {
        // Calcular porcentaje sobre salario anual
        importe = salarioAnual * (valor / 100);
    } else {
        // Usar importe fijo
        importe = valor;
    }

    // Aplicar signo según el tipo
    if (tipo === 'deduccion') {
        importe = -importe;
        row.classList.remove('devengo');
        row.classList.add('deduccion');
    } else {
        row.classList.remove('deduccion');
        row.classList.add('devengo');
    }

    row.querySelector('.importe-calculado').textContent = importe.toFixed(2) + ' €';
}

function calcularTotal() {
    let total = 0;
    document.querySelectorAll('#lineas tbody tr').forEach(row => {
        const importeText = row.querySelector('.importe-calculado').textContent;
        const importe = parseFloat(importeText.replace(' €', '')) || 0;
        total += importe;
    });

    document.getElementById('totalNomina').textContent = total.toFixed(2) + ' €';
}

function eliminarLinea(button) {
    button.closest('tr').remove();
    calcularTotal();
}

function validarLineas() {
    const lineasRows = document.querySelectorAll('#lineas tbody tr');

    if (lineasRows.length === 0) {
        alert('Debe añadir al menos una línea');
        return false;
    }

    let valido = true;

    lineasRows.forEach(row => {
        const concepto = row.querySelector('input[name="concepto"]').value;
        const valor = row.querySelector('input[name="valorCalculo"]').value;

        if (!concepto) {
            alert('El concepto es obligatorio en todas las líneas');
            valido = false;
            return;
        }

        if (!valor || parseFloat(valor) <= 0) {
            alert('El valor debe ser mayor que 0');
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
        const tipoCalculo = row.querySelector('select[name="tipoCalculo"]').value;
        const linea = {
            concepto: row.querySelector('input[name="concepto"]').value,
            esDevengo: row.querySelector('select[name="tipo"]').value === 'devengo',
            [tipoCalculo === 'porcentaje' ? 'porcentaje' : 'importeFijo']:
            row.querySelector('input[name="valorCalculo"]').value
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