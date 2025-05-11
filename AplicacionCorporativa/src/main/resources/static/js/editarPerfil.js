document.addEventListener('DOMContentLoaded', function() {
    // Elementos del DOM
    const editProfileBtn = document.getElementById('editProfileBtn');
    const modal = document.getElementById('profileModal');
    const closeBtn = document.querySelector('.close');
    const profileForm = document.getElementById('profileForm');

    // Función para mostrar notificación
    function showNotification(message, isSuccess) {
        const notification = document.createElement('div');
        notification.className = `custom-notification ${isSuccess ? 'notification-success' : 'notification-error'}`;
        notification.textContent = message;

        document.body.appendChild(notification);

        // Desaparecer después de 3 segundos
        setTimeout(() => {
            notification.style.opacity = '0';
            setTimeout(() => {
                document.body.removeChild(notification);
            }, 500);
        }, 3000);
    }

    // Manejar el envío del formulario
    profileForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        // Mostrar mensaje de carga
        showNotification("Actualizando perfil...", true);

        const formData = {
            nombre: document.getElementById('nombre').value,
            apellido: document.getElementById('apellido').value,
            telefono: document.getElementById('telefono').value,
            email: document.getElementById('email').value
        };

        try {
            const response = await fetch('/usuario/actualizar', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || 'Error al actualizar el perfil');
            }

            showNotification(data.message, true);

            // Cerrar modal y recargar después de 1 segundo
            setTimeout(() => {
                modal.style.display = 'none';
                window.location.reload();
            }, 1000);

        } catch (error) {
            console.error('Error:', error);
            showNotification(error.message, false);
        }
    });

    // Event listeners para abrir/cerrar modal
    if (editProfileBtn) {
        editProfileBtn.addEventListener('click', function(e) {
            e.preventDefault();
            modal.style.display = 'block';
        });
    }

    closeBtn.addEventListener('click', function() {
        modal.style.display = 'none';
    });

    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
});