
const API_URL = 'http://localhost:8080/api';


let usuarioLogueado = null;
let clientes = [];
let admins = [];

const loginScreen = document.getElementById('loginScreen');
const dashboardScreen = document.getElementById('dashboardScreen');
const loginForm = document.getElementById('loginForm');
const btnLogout = document.getElementById('btnLogout');
const usuarioLogueadoSpan = document.getElementById('usuarioLogueado');

const menuItems = document.querySelectorAll('.menu-item');
const sections = document.querySelectorAll('.section');


const btnNuevoCliente = document.getElementById('btnNuevoCliente');
const clientesTable = document.getElementById('clientesTbody');
const buscarCliente = document.getElementById('buscarCliente');
const filtroEstado = document.getElementById('filtroEstado');
const modalCliente = document.getElementById('modalCliente');
const formCliente = document.getElementById('formCliente');


const btnNuevoAdmin = document.getElementById('btnNuevoAdmin');
const adminsTable = document.getElementById('adminsTbody');
const modalAdmin = document.getElementById('modalAdmin');
const formAdmin = document.getElementById('formAdmin');


const btnActualizarStats = document.getElementById('btnActualizarStats');


loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const usuario = document.getElementById('usuario').value;
    const contraseña = document.getElementById('contraseña').value;
    const loginError = document.getElementById('loginError');
    
    try {
        const response = await fetch(`${API_URL}/admins/validar`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ usuario, contraseña })
        });
        
        const isValid = await response.json();
        
        if (isValid) {
            usuarioLogueado = usuario;
            loginError.textContent = '';
            mostrarDashboard();
        } else {
            loginError.textContent = 'Usuario o contraseña incorrectos';
        }
    } catch (error) {
        loginError.textContent = 'Error de conexión con el servidor';
        console.error(error);
    }
});

btnLogout.addEventListener('click', () => {
    usuarioLogueado = null;
    loginForm.reset();
    document.getElementById('usuario').focus();
    loginScreen.style.display = 'flex';
    dashboardScreen.style.display = 'none';
});


menuItems.forEach(item => {
    item.addEventListener('click', () => {
        
        menuItems.forEach(m => m.classList.remove('active'));
        sections.forEach(s => s.classList.remove('active'));
        
        
        item.classList.add('active');
        const section = item.getAttribute('data-section');
        document.getElementById(section + 'Section').classList.add('active');
        
    
        if (section === 'clientes') {
            cargarClientes();
        } else if (section === 'estadisticas') {
            cargarEstadisticas();
        } else if (section === 'admins') {
            cargarAdmins();
        }
    });
});


async function cargarClientes() {
    try {
        const response = await fetch(`${API_URL}/clientes`);
        clientes = await response.json();
        renderizarClientes(clientes);
    } catch (error) {
        console.error('Error al cargar clientes:', error);
        clientesTable.innerHTML = '<tr><td colspan="9" class="text-center">Error al cargar clientes</td></tr>';
    }
}

function renderizarClientes(clientesList) {
    if (clientesList.length === 0) {
        clientesTable.innerHTML = '<tr><td colspan="9" class="text-center">No hay clientes</td></tr>';
        return;
    }
    
    clientesTable.innerHTML = clientesList.map(cliente => `
        <tr>
            <td>${cliente.id}</td>
            <td>${cliente.nombre}</td>
            <td>${cliente.apellido}</td>
            <td>${cliente.celular}</td>
            <td>${cliente.direccion}</td>
            <td>${formatearVelocidad(cliente.velocidad)}</td>
            <td>$${cliente.montoPaga}</td>
            <td>
                <span class="${cliente.pagó ? 'success' : 'warning'}">
                    ${cliente.pagó ? 'Pagado' : 'Deuda'}
                </span>
            </td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-sm btn-secondary" onclick="editarCliente(${cliente.id})">Editar</button>
                    <button class="btn btn-sm btn-danger" onclick="eliminarCliente(${cliente.id})">Eliminar</button>
                </div>
            </td>
        </tr>
    `).join('');
}

function formatearVelocidad(velocidad) {
    const valor = String(velocidad ?? '').trim();
    return /mbps$/i.test(valor) ? valor : `${valor} Mbps`;
}

btnNuevoCliente.addEventListener('click', () => {
    document.getElementById('clienteId').value = '';
    document.getElementById('modalClienteTitle').textContent = 'Nuevo Cliente';
    formCliente.reset();
    document.getElementById('clientePagó').checked = false;
    abrirModal(modalCliente);
});

formCliente.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const id = document.getElementById('clienteId').value;
    const cliente = {
        nombre: document.getElementById('clienteNombre').value,
        apellido: document.getElementById('clienteApellido').value,
        celular: document.getElementById('clienteCelular').value,
        direccion: document.getElementById('clienteDireccion').value,
        velocidad: document.getElementById('clienteVelocidad').value,
        montoPaga: parseInt(document.getElementById('clienteMonto').value),
        pagó: document.getElementById('clientePagó').checked
    };
    
    try {
        if (id) {
            
            await fetch(`${API_URL}/clientes/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(cliente)
            });
        } else {
            
            await fetch(`${API_URL}/clientes`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(cliente)
            });
        }
        
        cerrarModal(modalCliente);
        cargarClientes();
    } catch (error) {
        console.error('Error al guardar cliente:', error);
    }
});

async function editarCliente(id) {
    const cliente = clientes.find(c => c.id === id);
    if (!cliente) return;
    
    document.getElementById('clienteId').value = cliente.id;
    document.getElementById('clienteNombre').value = cliente.nombre;
    document.getElementById('clienteApellido').value = cliente.apellido;
    document.getElementById('clienteCelular').value = cliente.celular;
    document.getElementById('clienteDireccion').value = cliente.direccion;
    document.getElementById('clienteVelocidad').value = cliente.velocidad;
    document.getElementById('clienteMonto').value = cliente.montoPaga;
    document.getElementById('clientePagó').checked = cliente.pagó;
    
    document.getElementById('modalClienteTitle').textContent = 'Editar Cliente';
    abrirModal(modalCliente);
}

async function eliminarCliente(id) {
    if (!confirm('¿Está seguro de que desea eliminar este cliente?')) return;
    
    try {
        await fetch(`${API_URL}/clientes/${id}`, { method: 'DELETE' });
        cargarClientes();
    } catch (error) {
        console.error('Error al eliminar cliente:', error);
    }
}


buscarCliente.addEventListener('keyup', filtrarClientes);
filtroEstado.addEventListener('change', filtrarClientes);

function filtrarClientes() {
    const busqueda = buscarCliente.value.toLowerCase();
    const estado = filtroEstado.value;
    
    let filtered = clientes;
    

    if (busqueda) {
        filtered = filtered.filter(c =>
            c.nombre.toLowerCase().includes(busqueda) ||
            c.apellido.toLowerCase().includes(busqueda) ||
            c.celular.includes(busqueda)
        );
    }

    if (estado === 'pagado') {
        filtered = filtered.filter(c => c.pagó);
    } else if (estado === 'deuda') {
        filtered = filtered.filter(c => !c.pagó);
    }
    
    renderizarClientes(filtered);
}

async function cargarEstadisticas() {
    try {
        const response = await fetch(`${API_URL}/clientes/estadisticas/pagos`);
        const stats = await response.json();
        
        document.getElementById('totalClientes').textContent = stats.totalClientes;
        document.getElementById('clientesPagados').textContent = stats.clientesPagados;
        document.getElementById('clientesDeuda').textContent = stats.clientesConDeuda;
        document.getElementById('porcentajePago').textContent = stats.porcentajePagado + '%';
        
        cargarClientesDeuda();
    } catch (error) {
        console.error('Error al cargar estadísticas:', error);
    }
}

async function cargarClientesDeuda() {
    try {
        const response = await fetch(`${API_URL}/clientes/deuda/todos`);
        const deudores = await response.json();
        
        const lista = document.getElementById('clientesDeudaList');
        
        if (deudores.length === 0) {
            lista.innerHTML = '<p class="text-center">¡Todos los clientes están al día!</p>';
            return;
        }
        
        lista.innerHTML = deudores.map(cliente => `
            <div class="cliente-item">
                <div class="cliente-info">
                    <h4>${cliente.nombre} ${cliente.apellido}</h4>
                    <p>📱 ${cliente.celular} | 💰 $${cliente.montoPaga}</p>
                </div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error al cargar clientes con deuda:', error);
    }
}

btnActualizarStats.addEventListener('click', cargarEstadisticas);


async function cargarAdmins() {
    try {
        const response = await fetch(`${API_URL}/admins`);
        admins = await response.json();
        renderizarAdmins(admins);
    } catch (error) {
        console.error('Error al cargar admins:', error);
        adminsTable.innerHTML = '<tr><td colspan="3" class="text-center">Error al cargar administradores</td></tr>';
    }
}

function renderizarAdmins(adminsList) {
    if (adminsList.length === 0) {
        adminsTable.innerHTML = '<tr><td colspan="3" class="text-center">No hay administradores</td></tr>';
        return;
    }
    
    adminsTable.innerHTML = adminsList.map(admin => `
        <tr>
            <td>${admin.id}</td>
            <td>${admin.usuario}</td>
            <td>
                <div class="action-buttons">
                    <button class="btn btn-sm btn-danger" onclick="eliminarAdmin(${admin.id})">Eliminar</button>
                </div>
            </td>
        </tr>
    `).join('');
}

btnNuevoAdmin.addEventListener('click', () => {
    document.getElementById('adminId').value = '';
    document.getElementById('modalAdminTitle').textContent = 'Nuevo Administrador';
    formAdmin.reset();
    abrirModal(modalAdmin);
});

formAdmin.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const id = document.getElementById('adminId').value;
    const admin = {
        usuario: document.getElementById('adminUsuario').value,
        contraseña: document.getElementById('adminContraseña').value
    };
    
    try {
        if (id) {
            await fetch(`${API_URL}/admins/${id}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(admin)
            });
        } else {
            await fetch(`${API_URL}/admins`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(admin)
            });
        }
        
        cerrarModal(modalAdmin);
        cargarAdmins();
    } catch (error) {
        console.error('Error al guardar admin:', error);
    }
});

async function eliminarAdmin(id) {
    if (!confirm('¿Está seguro de que desea eliminar este administrador?')) return;
    
    try {
        await fetch(`${API_URL}/admins/${id}`, { method: 'DELETE' });
        cargarAdmins();
    } catch (error) {
        console.error('Error al eliminar admin:', error);
    }
}
function abrirModal(modal) {
    modal.classList.add('active');
}

function cerrarModal(modal) {
    modal.classList.remove('active');
}

document.querySelectorAll('.modal-close').forEach(btn => {
    btn.addEventListener('click', (e) => {
        e.target.closest('.modal').classList.remove('active');
    });
});

document.querySelectorAll('.modal-close-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
        e.preventDefault();
        e.target.closest('.modal').classList.remove('active');
    });
});

window.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal')) {
        e.target.classList.remove('active');
    }
});

function mostrarDashboard() {
    loginScreen.style.display = 'none';
    dashboardScreen.style.display = 'flex';
    usuarioLogueadoSpan.textContent = usuarioLogueado;
    cargarClientes();
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('usuario').focus();
});