const empleadoId = window.location.pathname.split('/').pop();

document.getElementById('btn-volver').href = `/admin/detalle/${empleadoId}`;

fetch(`/api/admin/nomina/${empleadoId}`)
    .then(response => {
        if (!response.ok) throw new Error("Error al cargar nóminas");
        return response.json();
    })
    .then(nominas => {
        const container = document.getElementById("contenido-nomina");

        if (nominas.length === 0) {
            container.innerHTML = "<p>No hay nóminas registradas para este empleado.</p>";
            return;
        }

        container.innerHTML = `
                <table>
                    <thead>
                        <tr>
                            <th>ID Nómina</th>
                            <th>Fecha</th>
                            <th>Total</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${nominas.map(nomina => `
                            <tr>
                                <td>${nomina.id}</td>
                                <td>${formatDate(nomina.fecha)}</td>
                                <td>${nomina.total ? parseFloat(nomina.total).toFixed(2) : '0.00'} €</td>
                                <td>
                                    <button class="btn-toggle" onclick="toggleLineas('${nomina.id}')">
                                        Ver líneas
                                    </button>
                                </td>
                            </tr>
                            <tr id="lineas-${nomina.id}" style="display: none;">
                                <td colspan="4">
                                    <div class="lineas-nomina">
                                        <h4>Líneas de nómina:</h4>
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>Concepto</th>
                                                    <th>Tipo</th>
                                                    <th>Valor</th>
                                                    <th>Importe</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                ${nomina.lineas.map(linea => `
                                                    <tr class="${linea.esDevengo ? 'devengo' : 'deduccion'}">
                                                        <td>${linea.concepto}</td>
                                                        <td>${linea.esDevengo ? 'Devengo' : 'Deducción'}</td>
                                                        <td>
                                                            ${linea.porcentaje ? linea.porcentaje + '%' : ''}
                                                            ${linea.importeFijo ? linea.importeFijo + '€' : ''}
                                                        </td>
                                                        <td>${linea.cantidad ? parseFloat(linea.cantidad).toFixed(2) : '0.00'} €</td>
                                                    </tr>
                                                `).join('')}
                                            </tbody>
                                        </table>
                                    </div>
                                </td>
                            </tr>
                        `).join('')}
                    </tbody>
                </table>
            `;
    })
    .catch(error => {
        document.getElementById('contenido-nomina').innerHTML =
            `<div style="color:red;">Error: ${error.message}</div>`;
    });

function toggleLineas(nominaId) {
    const lineasRow = document.getElementById(`lineas-${nominaId}`);
    lineasRow.style.display = lineasRow.style.display === 'none' ? 'table-row' : 'none';
}

function formatDate(dateString) {
    if (!dateString) return 'Sin fecha';
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES');
}