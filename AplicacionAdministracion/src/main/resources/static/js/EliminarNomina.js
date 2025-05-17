$(document).ready(function() {
    $('.eliminar-btn').click(function() {
        const id = $(this).data('id');
        const row = $(this).closest('tr');

        if(confirm('¿Está seguro de que desea eliminar esta nómina? Esta acción no se puede deshacer.')) {
            $.ajax({
                url: '/api/admin/del/nomina/' + id,
                type: 'DELETE',
                success: function(result) {
                    row.fadeOut(400, function() {
                        row.remove();
                    });
                    alert('Nómina eliminada correctamente');
                },
                error: function(xhr) {
                    alert('Error al eliminar la nómina: ' + xhr.responseText);
                }
            });
        }
    });
});