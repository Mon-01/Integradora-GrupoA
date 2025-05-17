//Convierte la imagen en base64 para poder almacenarla en la base de datos

document.getElementById('imagenFile').addEventListener('change', function () {
    const file = this.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onloadend = function () {
            document.getElementById('imagen').value = reader.result;
        };
        reader.readAsDataURL(file);
    }
});