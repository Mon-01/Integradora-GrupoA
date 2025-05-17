document.addEventListener("DOMContentLoaded", function () {
    const form = document.getElementById("formCambioClave");

    form.addEventListener("submit", function (e) {
        e.preventDefault();

        const id = document.getElementById("id_usuario").value;
        const clave = document.getElementById("clave").value;
        const confirmarClave = document.getElementById("confirmarClave").value;
        const respuestaSecreta = document.getElementById("respuestaSecreta").value;
        const mensajeDiv = document.getElementById("mensaje");

        if (clave !== confirmarClave) {
            mensajeDiv.textContent = "Las contraseñas no coinciden.";
            mensajeDiv.style.color = "red";
            return;
        }

        fetch("/api/usuarios/cambiar-clave", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                id_usuario: id,
                clave: clave,
                respuestaSecreta: respuestaSecreta
            })
        })
            .then(response => {
                if (response.ok) {
                    alert("Contraseña actualizada correctamente.");
                    window.location.href = '/login';
                } else {
                    return response.text().then(msg => {
                        alert("Error: " + msg);
                    });
                }
            })
            .catch(error => {
                mensajeDiv.textContent = "Error en la solicitud.";
                mensajeDiv.style.color = "red";
                console.error(error);
            });
    });
});