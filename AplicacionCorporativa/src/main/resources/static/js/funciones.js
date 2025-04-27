function seleccionarTodo(){
    var musicas = document.getElementById("musicasSeleccionadas");
    for (let i = 0; i < musicas.options.length; i++) {
        musicas.options[i].selected = true;
    }
}
function deseleccionarTodo(){
    var musicas = document.getElementById("musicasSeleccionadas");
    for (let i = 0; i < musicas.options.length; i++) {
        musicas.options[i].selected = false;
    }
}