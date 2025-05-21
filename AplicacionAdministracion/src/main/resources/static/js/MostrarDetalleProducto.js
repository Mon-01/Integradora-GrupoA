document.addEventListener('DOMContentLoaded', function() {
    //obtenemos el id del producto desde el path del navegador
    const idProducto = window.location.pathname.split('/').pop();

    const loadingDiv = document.getElementById('loading');
    const errorDiv = document.getElementById('error');
    const productContentDiv = document.getElementById('productContent');

    // cogemos los span para meter los datos de los productos
    const productIdSpan = document.getElementById('productId');
    const productNombreSpan = document.getElementById('productNombre');
    const productPrecioSpan = document.getElementById('productPrecio');
    const productDescripcionSpan = document.getElementById('productDescripcion');
    const productValoracionSpan = document.getElementById('productValoracion');
    const productMarcaSpan = document.getElementById('productMarca');
    const productEsPerecederoSpan = document.getElementById('productEsPerecedero');
    const productFechaFabricacionSpan = document.getElementById('productFechaFabricacion'); // Actualizado ID
    const productCategoriasSpan = document.getElementById('productCategorias');

    // secciones de detalles específicos
    const libroDetailsDiv = document.getElementById('libroDetails');
    const ropaDetailsDiv = document.getElementById('ropaDetails');
    const muebleDetailsDiv = document.getElementById('muebleDetails');

    // //en caso de que no se proporcione id
    // if (!idProducto) {
    //     loadingDiv.style.display = 'none';
    //     errorDiv.textContent = 'No se proporcionó un ID de producto.';
    //     errorDiv.style.display = 'block';
    //     return;
    // }

    const apiUrl = `/api/admin/producto/${idProducto}`;

    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error HTTP: ${response.status} - ${response.statusText}`);
            }
            return response.json();
        })
        .then(product => {
            loadingDiv.style.display = 'none';
            productContentDiv.style.display = 'block';

            // rellenar detalles comunes del DTO
            productIdSpan.textContent = product.id_producto || 'N/A';
            productNombreSpan.textContent = product.nombre || 'N/A';
            productPrecioSpan.textContent = product.precio !== null ? ` ${parseFloat(product.precio).toFixed(2)} €` : 'N/A';
            productDescripcionSpan.textContent = product.descripcion || 'N/A';
            productValoracionSpan.textContent = product.valoracion !== null ? product.valoracion : 'N/A';
            productMarcaSpan.textContent = product.marca || 'N/A';
            productEsPerecederoSpan.textContent = product.esPerecedero ? 'Sí' : 'No';

            // mostrar fecha de fabricación (viene como LocalDate -> string "YYYY-MM-DD")
            if (product.fechaFabricacion) {
                try {
                    const parts = product.fechaFabricacion.split('-');
                    if (parts.length === 3) {
                        // viene cómo localDate, la pasamos a date
                        const dateObj = new Date(parseInt(parts[0]), parseInt(parts[1]) - 1, parseInt(parts[2]));
                        productFechaFabricacionSpan.textContent = dateObj.toLocaleDateString();
                    } else {
                        // Mostrar como viene si no es el formato esperado
                        productFechaFabricacionSpan.textContent = product.fechaFabricacion;
                    }
                } catch (e) {
                    console.warn("Error al parsear fechaFabricacion:", product.fechaFabricacion, e);
                    productFechaFabricacionSpan.textContent = product.fechaFabricacion; // Mostrar como viene en caso de error
                }
            } else {
                productFechaFabricacionSpan.textContent = 'N/A';
            }

            // Mostrar categorías (List<String> en el DTO)
            productCategoriasSpan.innerHTML = ''; // Limpiar
            if (product.categorias && product.categorias.length > 0) {
                product.categorias.forEach(catNombre => {
                    const catTag = document.createElement('span');
                    catTag.textContent = catNombre;
                    catTag.classList.add('category-tag'); // Para aplicar estilos
                    productCategoriasSpan.appendChild(catTag);
                });
            } else {
                productCategoriasSpan.textContent = 'Sin categorías';
            }

            // Rellenar y mostrar detalles específicos basados en product.tipoProducto
            if (product.tipoProducto === 'LIBRO') {
                libroDetailsDiv.style.display = 'block';
                document.getElementById('libroTitulo').textContent = product.titulo || 'N/A';
                document.getElementById('libroAutor').textContent = product.autor || 'N/A';
                document.getElementById('libroEditorial').textContent = product.editorial || 'N/A';
                document.getElementById('libroTapa').textContent = product.tapa || 'N/A';
                document.getElementById('libroNumeroPaginas').textContent = product.numeroPaginas !== null ? product.numeroPaginas : 'N/A';
                document.getElementById('libroSegundaMano').textContent = product.segundaMano ? 'Sí' : 'No';
            } else if (product.tipoProducto === 'ROPA') {
                ropaDetailsDiv.style.display = 'block';
                document.getElementById('ropaTalla').textContent = product.talla || 'N/A';
                document.getElementById('ropaMaterial').textContent = product.material || 'N/A';
                document.getElementById('ropaEstacion').textContent = product.estacion || 'N/A';
            } else if (product.tipoProducto === 'MUEBLE') {
                muebleDetailsDiv.style.display = 'block';
                document.getElementById('muebleAncho').textContent = product.ancho !== null ? `${product.ancho} cm` : 'N/A';
                document.getElementById('muebleProfundo').textContent = product.profundo !== null ? `${product.profundo} cm` : 'N/A';
                document.getElementById('muebleAlto').textContent = product.alto !== null ? `${product.alto} cm` : 'N/A';

                const muebleColoresSpan = document.getElementById('muebleColores');
                muebleColoresSpan.innerHTML = ''; // Limpiar
                if (product.colores && product.colores.length > 0) {
                    const colorList = document.createElement('ul');
                    colorList.classList.add('color-list');
                    product.colores.forEach(color => {
                        const colorItem = document.createElement('li');
                        colorItem.textContent = color;
                        colorList.appendChild(colorItem);
                    });
                    muebleColoresSpan.appendChild(colorList);
                } else {
                    muebleColoresSpan.textContent = 'Sin colores especificados';
                }
            }

        })
        .catch(error => {
            console.error('Error fetching product details:', error);
            loadingDiv.style.display = 'none';
            productContentDiv.style.display = 'none'; // Ocultar contenido si hay error
            errorDiv.textContent = `Error al cargar el detalle del producto. ${error.message}. Intenta de nuevo más tarde.`;
            errorDiv.style.display = 'block';
        });
});