function deseleccionarGenero(){
    const radios = document.getElementsByName("genero");
    radios.forEach(radio => radio.checked = false);
}

function seleccionarPrimerGenero(){
    const radios = document.getElementsByName("genero");
    radios[0].checked = true;
}


function seleccionarTodo(){
    var espec = document.getElementsByName("especialidades");
    for (var i = 0; i < espec.length; i++) {
        espec[i].checked = false;
    }

}
function deseleccionarTodo(){
    var espec = document.getElementsByName("especialidades");
    for (let i = 0; i < espec.length; i++){
        espec[i].checked = false;
    }

}