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
                                <td>${new Date(nomina.fecha).toLocaleDateString()}</td>
                                <td>${nomina.total?.toFixed(2) || '0.00'} €</td>
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
                                                    <th>Importe</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                ${nomina.lineas.map(linea => `
                                                    <tr>
                                                        <td>${linea.concepto}</td>
                                                        <td>${linea.cantidad?.toFixed(2) || '0.00'} €</td>
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
        container.innerHTML = `<div style="color:red;">Error: ${error.message}</div>`;
    });

function toggleLineas(nominaId) {
    const lineasRow = document.getElementById(`lineas-${nominaId}`);
    lineasRow.style.display = lineasRow.style.display === 'none' ? 'table-row' : 'none';
}