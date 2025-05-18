document.addEventListener('DOMContentLoaded', () => {
    const modal = document.getElementById('modalBloqueo');
    const spanClose = document.querySelector('.modal .close');
    const cancelarBtn = document.getElementById('cancelarBloqueo');
    const aceptarBtn = document.getElementById('aceptarBloqueo');
    let empleadoActual = null;

    document.querySelectorAll('.lock-btn').forEach(btn => {
        btn.addEventListener('click', function(e) {
            console.log('Se hizo clic en el botón de bloqueo'); // <-- LOG
            e.stopPropagation();
            const id = this.dataset.id;
            console.log('ID del empleado a bloquear:', id); // <-- LOG

            fetch(`/api/admin/isblock/${id}`)
                .then(res => res.json())
                .then(data => {
                    console.log('Respuesta de /api/admin/isblock:', data); // <-- LOG
                    if (!data.bloqueado) {
                        empleadoActual = id;
                        const row = this.closest('tr');
                        const nombre = row.children[0].textContent;
                        const apellidos = row.children[1].textContent;
                        document.getElementById('modalNombre').textContent = `Nombre: ${nombre} ${apellidos}`;
                        document.getElementById('motivoBloqueo').value = '';
                        document.getElementById('tiempo').value = '1';
                        console.log('Mostrando modal'); // <-- LOG
                        modal.style.display = 'flex';
                        console.log('Estado del modal:', modal.style.display); // <-- LOG
                    } else {
                        alert("Este usuario ya está bloqueado. Motivo: " + data.motivo);
                    }
                })
                .catch(error => {
                    console.error('Error al verificar bloqueo:', error); // <-- LOG
                });
        });
    });

    spanClose.onclick = cancelarBtn.onclick = () => {
        modal.style.display = 'none';
    };

    aceptarBtn.onclick = () => {
        const motivo = document.getElementById('motivoBloqueo').value.trim();
        const tiempo = document.getElementById('tiempo').value;

        if (!motivo) {
            alert("Por favor, introduce un motivo.");
            return;
        }

        fetch(`/api/admin/bloquear`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                idEmpleado: empleadoActual,
                motivo,
                diasBloqueo: tiempo
            })
        })
            .then(res => {
                if (res.ok) {
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

    document.addEventListener('click', function(e) {
        if (e.target.classList.contains('unlock-btn')) {
            e.stopPropagation();
            const id = e.target.dataset.id;

            if (confirm("¿Seguro que quieres desbloquear a este usuario?")) {
                fetch(`/api/admin/desbloquear/${id}`, { method: 'POST' })
                    .then(res => {
                        if (res.ok) {
                            alert("Usuario desbloqueado");
                            e.target.textContent = '🔒';
                            e.target.title = "Bloquear usuario";
                            e.target.classList.remove('unlock-btn');
                            e.target.classList.add('lock-btn');
                        } else {
                            alert("Error al desbloquear usuario");
                        }
                    });
            }
        }
    });
});