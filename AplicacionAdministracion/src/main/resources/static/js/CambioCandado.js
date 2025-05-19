const modal = document.getElementById('modalBloqueo');
const spanClose = document.querySelector('.modal .close');
const cancelarBtn = document.getElementById('cancelarBloqueo');
const aceptarBtn = document.getElementById('aceptarBloqueo');



//cogemos el texto de cada celda para mostrarlo en el modal
let nombre;
let apellidos;

document.addEventListener('DOMContentLoaded', function() {
    const celdasCandado = document.querySelectorAll('.botonCandado');
    celdasCandado.forEach(celda => {
        const idUser = celda.getAttribute('data-id');
        if (idUser) {
            actualizarIconoCandado(idUser, celda);
        }
    });
});

function actualizarIconoCandado(idUser, celdaCandado) {
    fetch(`/api/admin/isblock/${idUser}`)
        .then(response => {
            if (response.ok) {
                // la llamada devuelve 200, el usuario no está bloqueado
                celdaCandado.innerHTML = '<button class="lock-btn" data-id="' + idUser + '" title="Bloquear usuario">🔒</button>';
                // añadimos listenner para abrir ventana modal y bloquear al usuario
                const lockButton = celdaCandado.querySelector('.lock-btn');
                if (lockButton) {
                    lockButton.addEventListener('click', function(event) {
                        //evita el onblur que salta al detale del empleado
                        event.stopPropagation();
                        //cogemos la fila entera de cada empleado
                        const row = this.closest('tr');
                        nombre = row.children[0].textContent;
                        apellidos = row.children[1].textContent;
                        document.getElementById('modalId').textContent = `ID del usuario: ${idUser}`;
                        document.getElementById('modalNombre').textContent = `Nombre: ${nombre} ${apellidos}`;
                        document.getElementById('motivoBloqueo').value = '';
                        document.getElementById('tiempo').value = '';
                        modal.style.display = 'flex';
                    });
                }
            } else if (response.status === 403) {
                // Está bloqueado (la API devolvió 403 Forbidden)
                celdaCandado.innerHTML = '<button class="unlock-btn" data-id="' + idUser + '" title="Desbloquear usuario">🔓</button>';
                // añadimos listenner para desbloquear al usuario
                const unlockButton = celdaCandado.querySelector('.unlock-btn');
                if (unlockButton) {
                    unlockButton.addEventListener('click', function (event) {
                        //evita el onblur que salta al detale del empleado
                        event.stopPropagation();
                        const userIdToUnlock = this.getAttribute('data-id');
                        console.log('Desbloquear usuario con ID:', userIdToUnlock);
                        fetch(`/api/admin/desbloquear/${idUser}`,{
                            method: 'POST'
                        })
                            .then(response => {
                                if (response.ok) {
                                    const row = this.closest('tr');
                                    nombre = row.children[0].textContent;
                                    alert(`Se ha desbloqueado al usuario: ${nombre}`);
                                    window.location.reload();
                                    // actualizarIconoCandado(userIdToUnlock, celdaCandado); // Volver a actualizar el icono
                                    // O realizar alguna otra acción tras el desbloqueo
                                } else {
                                    console.error('Error al desbloquear el usuario');
                                }
                            });
                    });

                } else {
                    console.error('Error al comprobar el estado de bloqueo:', response.status);
                    celdaCandado.innerHTML = '⚠️'; // Mostrar un icono de error si falla la llamada
                }
            }
        })
        .catch(error => {
            console.error('Error de red:', error);
            celdaCandado.innerHTML = '⚠️'; // Mostrar un icono de error si hay un error de red
        });
}
//cerramos el modal con la x
spanClose.onclick = cancelarBtn.onclick = () => {
    modal.style.display = 'none';
};

//al darle aceptar en el modal hacemos la llamada al contralador para bloquear al usuario
aceptarBtn.onclick = () => {
    const id = document.getElementById("modalId").value;
    const motivo = document.getElementById('motivoBloqueo').value;
    const tiempo = document.getElementById('tiempo').value;
    // Convertir a formato ISO 8601 que Java puede parsear automáticamente
    const fechaISO = new Date(tiempo).toISOString();

    //obligamos a introducir un motivo de bloqueo
    if (!motivo) {
        alert("Por favor, introduce un motivo.");
        return;
    }

    fetch(`/api/admin/bloquear`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify({
            id: id,
            motivo: motivo,
            tiempo: fechaISO
        })
    })
        .then(response => {
            if (response.ok) {
                alert("Usuario bloqueado correctamente");
                const btn = document.querySelector(`.lock-btn[data-id="${empleadoActual}"]`);
                btn.textContent = '🔓';
                btn.title = "Desbloquear usuario";
                btn.classList.remove('lock-btn');
                btn.classList.add('unlock-btn');
                modal.style.display = 'none';
            } else {
                alert("Error al bloquear usuario");
            }
        });
};

//
// // Seleccionar todas las celdas de candado y aplicar la lógica
// document.addEventListener('DOMContentLoaded', function() {
//     const celdasCandado = document.querySelectorAll('.botonCandando');
//     celdasCandado.forEach(celda => {
//         // Obtener el ID del empleado de la fila correspondiente
//         const fila = celda.parentNode;
//         const linkDetalle = fila.getAttribute('th:onclick');
//         if (linkDetalle) {
//             const match = linkDetalle.match(/'\/admin\/detalle\/([^']+)'/);
//             if (match && match[1]) {
//                 const idUser = match[1];
//                 actualizarIconoCandado(idUser, celda);
//             }
//         }
//     });
// });