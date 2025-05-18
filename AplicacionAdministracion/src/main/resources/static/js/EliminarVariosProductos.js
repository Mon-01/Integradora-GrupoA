$(document).ready(function() {
    const tablaResultados = $('#resultados tbody');
    const seleccionarProductosEliminarBtn = $('#seleccionarProductosEliminar');
    const eliminarProductosSeleccionadosBtn = $('#eliminarProductosSeleccionados');
    let modoSeleccionMultiple = false;

    // Event listener para el botón "Seleccionar productos a eliminar"
    seleccionarProductosEliminarBtn.on('click', function() {
        modoSeleccionMultiple = !modoSeleccionMultiple;

        if (modoSeleccionMultiple) {
            // Mostrar el botón "Eliminar productos seleccionados"
            eliminarProductosSeleccionadosBtn.show();

            // Modificar la tabla para mostrar checkboxes
            tablaResultados.find('tr').each(function() {
                const idProducto = $(this).find('.eliminar-btn').data('id');
                $(this).find('.acciones-celda').html(`
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
                    <button class="btn btn-danger eliminar-btn" data-id="${idProducto}">Eliminar</button>
                    <button class="btn btn-primary editar-btn" data-id="${idProducto}">Editar</button>
                `);
            });
            seleccionarProductosEliminarBtn.text('Seleccionar productos a eliminar');

            // Re-enlazar los eventos de eliminar individuales (importante!)
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
        const productosSeleccionados = [];
        tablaResultados.find('.producto-checkbox:checked').each(function() {
            productosSeleccionados.push($(this).data('id'));
        });

        if (productosSeleccionados.length > 0) {
            if (confirm('¿Está seguro de que desea eliminar los productos seleccionados? Esta acción no se puede deshacer.')) {
                $.ajax({
                    url: '/api/admin/eliminar-multiples',
                    type: 'DELETE',
                    contentType: 'application/json',
                    data: JSON.stringify(productosSeleccionados),
                    success: function(result) {
                        // Eliminar las filas de la tabla
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