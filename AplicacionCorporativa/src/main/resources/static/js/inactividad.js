let timeout;
//definimos el tiempo de inactividad
const INACTIVITY_LIMIT = 5 * 1000;

//cada vez que se ejecuta uno de los eventos del array
['click', 'mousemove', 'keypress'].forEach(event => {
    //llamamos a resetTimer
    document.addEventListener(event, resetTimer);
});

resetTimer();

function resetTimer() {
    //cancelamos el temporizador anterior
    clearTimeout(timeout);
    console.log("temporizador reiniciado");
    //ejecuta logout si no hay actividad en el tiempo predefinido
    timeout = setTimeout(logout, INACTIVITY_LIMIT);
}

function logout() {
    console.log("iniciando logout");
    //con fetch hacemos una petición al getMapping logout
    fetch('/logout', { method: 'GET' })
        //con el then redirigimos al usuario al login
        .then(() => window.location.href = '/login');
    console.log("se ha redirigido");
}

