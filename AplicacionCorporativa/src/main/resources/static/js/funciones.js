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