document.getElementById('adminLoginForm').addEventListener('submit', async function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;

    try {
        const response = await fetch('/api/admin/loginAdmin', {
            method: 'POST',
            //indica que se están enviando datos en formato json
            headers: { 'Content-Type': 'application/json' },
            //
            body: JSON.stringify({ email, clave: password })
        });

        if (response.ok) {
            window.location.href = "/admin/inicio"; // Redirigir al área personal
        } else {
            const error = await response.text();
            document.getElementById('error').innerText = error;
        }
    } catch (err) {
        document.getElementById('error').innerText = "Error inesperado: " + err.message;
    }
});