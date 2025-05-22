//con ready nos esperamos a que cargue el dom
$(document).ready(function() {
    //cogemos los resultados de la busqueda y localizamos el elemento por la clase
    $('#resultados').on('click', '.eliminar-btn', function() {
        console.log("this:", this);
        console.log("$(this):", $(this));
        console.log("event.target:", event.target);
        //le pasamos el id y el tr más cercano para borrarlo
        const id = $(this).data('id');
        const row = $(this).closest('tr');


        if(confirm('¿Está seguro de que desea eliminar este producto? Esta acción no se puede deshacer.')) {
            $.ajax({
                url: '/api/admin/del/producto/' + id,
                type: 'DELETE',
                success: function(result) {
                    row.fadeOut(400, function() {
                        row.remove();
                    });
                    alert('Producto eliminado correctamente');
                },
                error: function(xhr) {
                    alert('Error al eliminar el producto: ' + xhr.responseText);
                }
            });
        }
    });
});


//para eliminar productos masivamente
$(document).ready(function() {
    const tablaResultados = $('#resultados tbody');
    const seleccionarProductosEliminarBtn = $('#seleccionarProductosEliminar');
    const eliminarProductosSeleccionadosBtn = $('#eliminarProductosSeleccionados');
    let modoSeleccionMultiple = false;

    // añadimos evento al botón seleccionar para eliminar
    seleccionarProductosEliminarBtn.on('click', function() {
        modoSeleccionMultiple = !modoSeleccionMultiple;

        if (modoSeleccionMultiple) {
            // al pulsar el botón se mostrará el de eliminar
            eliminarProductosSeleccionadosBtn.show();

            // y se reemplazaran los botones eliminar y editar por checkbox
            tablaResultados.find('tr').each(function() {
                //cogemos los id de cada producto del botón eliminar
                const idProducto = $(this).find('.eliminar-btn').data('id');
                $(this).find('.acciones-celda').html(`
                    <!--le asignamos el id del producto al valor del checkbox-->
                    <input type="checkbox" class="producto-checkbox" data-id="${idProducto}">
                `);
            });
            seleccionarProductosEliminarBtn.text('Cancelar Selección');
        } else {
            // Ocultar el botón "Eliminar productos seleccionados"
            eliminarProductosSeleccionadosBtn.hide();

            // Restaurar los botones originales
            tablaResultados.find('tr').each(function() {
                const idProducto = $(this).find('.producto-checkbox').data('id');
                $(this).find('.acciones-celda').html(`
                    <button class="btn btn-danger eliminar-btn" data-id="${idProducto}">
                    <i class="fas fa-trash"></i>Eliminar</button>
                    <button class="btn btn-primary editar-btn" data-id="${idProducto}">
                    <i class="fas fa-pen-to-square"></i>Editar</button>
                `);
            });
            seleccionarProductosEliminarBtn.text('Seleccionar productos a eliminar');

            // volvemos a poner los eventos cómo estaban antes para que funcionen los botones individuales
            $('.eliminar-btn').on('click', function() {
                const id = $(this).data('id');
                const row = $(this).closest('tr');

                if(confirm('¿Está seguro de que desea eliminar este producto? Esta acción no se puede deshacer.')) {
                    $.ajax({
                        url: '/api/admin/del/producto/' + id,
                        type: 'DELETE',
                        success: function(result) {
                            row.fadeOut(400, function() {
                                row.remove();
                            });
                            alert('Producto eliminado correctamente');
                        },
                        error: function(xhr) {
                            alert('Error al eliminar el producto: ' + xhr.responseText);
                        }
                    });
                }
            });
        }
    });

    // Event listener para el botón "Eliminar productos seleccionados"
    eliminarProductosSeleccionadosBtn.on('click', function() {
        //creamos array para almacenar ids
        const productosSeleccionados = [];
        //filtramos los checkbos que estén seleccionados
        tablaResultados.find('.producto-checkbox:checked').each(function() {
            //y obtenemos el data-id de cada checkbox para añadirlo al array
            productosSeleccionados.push($(this).data('id'));
        });

        if (productosSeleccionados.length > 0) {
            if (confirm('¿Está seguro de que desea eliminar los productos seleccionados? Esta acción no se puede deshacer.')) {
                $.ajax({
                    url: '/api/admin/eliminarVarios/productos',
                    type: 'DELETE',
                    contentType: 'application/json',
                    //pasamos el array a json para que lo reciba el controlador
                    data: JSON.stringify(productosSeleccionados),
                    success: function(result) {
                        // Eliminar las filas de la tabla en la vista
                        productosSeleccionados.forEach(id => {
                            tablaResultados.find(`.producto-checkbox[data-id="${id}"]`).closest('tr').remove();
                        });
                        alert('Productos eliminados correctamente.');
                        seleccionarProductosEliminarBtn.click(); // Desactivar la selección múltiple
                    },
                    error: function(xhr) {
                        alert('Error al eliminar los productos: ' + xhr.responseText);
                    }
                });
            }
        } else {
            alert('Por favor, seleccione al menos un producto para eliminar.');
        }
    });

    // Inicializar los eventos de eliminar individuales
    $('.eliminar-btn').on('click', function() {
        const id = $(this).data('id');
        const row = $(this).closest('tr');

        if(confirm('¿Está seguro de que desea eliminar este producto? Esta acción no se puede deshacer.')) {
            $.ajax({
                url: '/api/admin/del/producto/' + id,
                type: 'DELETE',
                success: function(result) {
                    row.fadeOut(400, function() {
                        row.remove();
                    });
                    alert('Producto eliminado correctamente');
                },
                error: function(xhr) {
                    alert('Error al eliminar el producto: ' + xhr.responseText);
                }
            });
        }
    });
});