document.getElementById('filtroForm').addEventListener('submit', function(e) {
    e.preventDefault();

    const descripcion = document.getElementById('descripcion').value;
    const proveedor = document.getElementById('proveedor').value;
    const categorias = document.getElementById('categorias').value
        .split(',').map(c => c.trim()).filter(c => c);
    const esPerecedero = document.getElementById('esPerecedero').value;
    const payload = {
        descripcion,
        proveedor,
        categorias,
        esPerecedero: esPerecedero === "" ? null : (esPerecedero === "true")
    };

    fetch('/api/admin/buscar', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
        .then(res => res.json())
        .then(data => {
            const tbody = document.querySelector("#resultados tbody");
            tbody.innerHTML = ""; // Limpia la tabla antes de añadir nuevos resultados

            data.forEach(p => {

                const row = `
                        <tr>
                          <td onclick="window.location.href='/detalle/producto/${p.id_producto}'" style="cursor:pointer;">${p.descripcion}</td>
                          <td>€ ${parseFloat(p.precio).toFixed(2)}</td>
                          <td>${p.categoriaPrincipal}</td>
                          <td>${p.unidades}</td>
                          <td>${p.proveedor}</td>
                          <td class="acciones-celda">
                            <button class="btn btn-danger eliminar-btn" data-id="${p.id_producto}">
                            <i class="fas fa-trash"></i>Eliminar</button>
                            <button class="btn btn-primary editar-btn" data-id="${p.id_producto}">
                            <i class="fas fa-pen-to-square"></i>Editar</button>
                          </td>
                        </tr>
                    `;
                    tbody.innerHTML += row;

            });
        })
        .catch(error => {
            console.error('Error al buscar productos:', error);
            alert('Hubo un error al buscar los productos. Por favor, inténtalo de nuevo.');
        });
});