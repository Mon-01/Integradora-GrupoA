function actualizarEmpleado() {
    // Limpiar errores anteriores
    document.querySelectorAll('.error').forEach(e => e.textContent = '');
    document.querySelectorAll('.form-control').forEach(e => e.classList.remove('error-border'));

    // Obtener datos del formulario
    const empleadoDTO = {
        id_empleado: document.getElementById('id-empleado').value,
        nombre: document.getElementById('nombre').value,
        apellido: document.getElementById('apellido').value,
        genero: document.getElementById('genero').value,
        edad: parseInt(document.getElementById('edad').value),
        fechaNacimiento: document.getElementById('fecha-nacimiento').value,
        prefijoTel: parseInt(document.getElementById('prefijo-tel').value),
        telefono: document.getElementById('telefono').value,
        otroTelefono: document.getElementById('otro-telefono').value,
        email: document.getElementById('email').value,
        direccion: {
            tipoVia: document.getElementById('tipo-via').value,
            nombreVia: document.getElementById('calle').value,
            numero: document.getElementById('numero').value,
            portal: document.getElementById('portal').value,
            planta: document.getElementById('planta').value,
            puerta: document.getElementById('puerta').value,
            localidad: document.getElementById('localidad').value,
            cod_postal: document.getElementById('codigo-postal').value,
            region: document.getElementById('region').value
        },
        paisNacimiento: document.getElementById('pais-nacimiento').value,
        tipoDocumento: document.getElementById('tipo-documento').value,
        documento: document.getElementById('documento').value,
        idDepartamento: document.getElementById('departamento').value,
        salarioAnual: document.getElementById('salario-anual').value,
        comisionAnual: document.getElementById('comision-anual').value,
        comentarios: document.getElementById('comentarios').value,
        especializaciones: Array.from(document.getElementById('especializaciones').selectedOptions)
            .map(option => option.value),
        datosBancarios: {
            entidadBancaria: document.getElementById('entidad-bancaria').value,
            numCuenta: document.getElementById('cuenta-bancaria').value,
            tarjeta: {
                tipo: document.getElementById('tipo-tarjeta').value,
                numero: document.getElementById('numero-tarjeta').value,
                cvc: document.getElementById('cvc-tarjeta').value
            }
        }
    };

    // Procesar imagen si se ha seleccionado
    const imagenInput = document.getElementById('imagen');
    const imagenFile = imagenInput.files[0];

    // Si hay una imagen seleccionada, convertirla a base64
    if (imagenFile) {
        const reader = new FileReader();
        reader.onload = function(event) {
            const base64String = event.target.result.split(',')[1];
            empleadoDTO.imagen = base64String;
            enviarDatos(empleadoDTO);
        };
        reader.readAsDataURL(imagenFile);
    } else {
        enviarDatos(empleadoDTO);
    }
}

function enviarDatos(empleadoDTO) {
    const cuerpo =  JSON.stringify(empleadoDTO);
    alert(cuerpo);
    fetch(`/api/admin/empleados/${empleadoDTO.id_empleado}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: cuerpo
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(err => Promise.reject(err));
            }
            return response.json();
        })
        .then(() => {
            alert('Empleado actualizado con éxito');
            window.location.href = '/admin/inicio';
        })
        .catch(error => {
            if (error.errors) {
                error.errors.forEach(err => {
                    // Buscar el elemento de error por el campo exacto
                    const fieldErrorElement = document.querySelector(`[id$="${err.field}-error"]`);
                    // Buscar el input por el campo exacto o por el último segmento después del punto
                    const inputElement = document.getElementById(err.field.includes('.') ?
                        err.field.split('.').pop() :
                        err.field);

                    if (fieldErrorElement) {
                        fieldErrorElement.textContent = err.defaultMessage;
                    }
                    if (inputElement) {
                        inputElement.classList.add('error-border');
                    }
                });
            } else {
                alert('Error: ' + (error.message || 'Error desconocido'));
            }
        });
}