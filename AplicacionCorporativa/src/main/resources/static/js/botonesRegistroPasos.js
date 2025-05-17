function deseleccionarGenero(){
    const radios = document.getElementsByName("genero");
    radios.forEach(radio => radio.checked = false);
}

function seleccionarPrimerGenero(){
    const radios = document.getElementsByName("genero");
    radios[0].checked = true;
}


function seleccionarTodo(){
    var espec = document.getElementsByName("especializaciones");
    for (var i = 0; i < espec.length; i++) {
        espec[i].checked = true;
    }

}
function deseleccionarTodo(){
    var espec = document.getElementsByName("especializaciones");
    for (let i = 0; i < espec.length; i++){
        espec[i].checked = false;
    }

}

function blanquear() {

    const inputs = document.getElementsByTagName("input");

    for (let i = 0; i < inputs.length; i++) {
        const tipo = inputs[i].type;
        if (tipo === "text" || tipo === "number" || tipo === "textarea") {
            inputs[i].value = "";
        } else if (tipo === "radio" || tipo === "checkbox") {
            inputs[i].checked = false;
        }
    }
}

//Convierte la imagen en base64 para poder almacenarla en la base de datos
document.getElementById('imagen').addEventListener('change', function () {
    const file = this.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onloadend = function () {
            document.getElementById('imagenBase64').value = reader.result.split(',')[1]; // solo la parte base64
        };
        reader.readAsDataURL(file);
    }
});

