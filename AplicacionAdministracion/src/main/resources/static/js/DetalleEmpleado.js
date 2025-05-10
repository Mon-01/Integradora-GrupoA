const empleadoId = window.location.pathname.split('/').pop();

fetch(`/api/admin/${empleadoId}`)
    .then(response => {
        if (!response.ok) throw new Error("Error al cargar empleado");
        return response.json();
    })
    .then(data => {
        const container = document.getElementById("contenido-empleado");

        // Construir HTML para la información básica
        let html = `
                <div class="info-section">
                    <h2>Información Básica</h2>
                    <p><strong>Nombre:</strong> ${data.nombre} ${data.apellido}</p>
                    <p><strong>Email:</strong> ${data.email}</p>
                    <p><strong>Teléfono:</strong> ${data.telefono}</p>
                    <p><strong>Fecha Nacimiento:</strong> ${data.fechaNacimiento}</p>
                </div>

                <div class="info-section">
                    <h2>Información Laboral</h2>
                    <p><strong>Salario Anual:</strong> ${data.salarioAnual} €</p>
                    <p><strong>Comisión Anual:</strong> ${data.comisionAnual} €</p>
                    <p><strong>Comentarios:</strong> ${data.comentarios || 'Ninguno'}</p>
                </div>
            `;

        // Agregar información de usuario si existe
        if (data.usuario) {
            html += `
                <div class="info-section">
                    <h2>Información de Usuario</h2>
                    <p><strong>Email:</strong> ${data.usuario.email}</p>
                    <p><strong>Estado:</strong> ${data.usuario.bloqueado ? 'Bloqueado' : 'Activo'}</p>
                    ${data.usuario.motivoBloqueo ? `<p><strong>Motivo de bloqueo:</strong> ${data.usuario.motivoBloqueo}</p>` : ''}
                </div>
                `;
        }


        // Agregar sección de nóminas si existen
        if (data.nominas && data.nominas.length > 0) {
            html += `
                    <a class="btn btn-primary" href="/admin/nomina/${data.id_empleado}"
           class="btn-ver-nomina">
           Ver nóminas
        </a>
                `;
        }

        html += `</div>`;
        container.innerHTML = html;
    })
    .catch(error => {
        document.getElementById("contenido-empleado").innerHTML = `
                <div class="error">
                    <p>Error al cargar los datos del empleado: ${error.message}</p>
                    <a href="/admin/inicio" class="btn">Volver al listado</a>
                </div>
            `;
    });