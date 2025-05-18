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

        console.log("ID a eliminar:", id); // <--- AÑADE ESTA LÍNEA

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