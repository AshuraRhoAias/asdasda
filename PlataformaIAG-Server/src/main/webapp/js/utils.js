// Utilidades generales

// Formatear fecha
function formatearFecha(timestamp) {
    const fecha = new Date(timestamp);
    const opciones = { year: 'numeric', month: 'long', day: 'numeric' };
    return fecha.toLocaleDateString('es-ES', opciones);
}

// Mostrar alerta
function mostrarAlerta(mensaje, tipo = 'success') {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${tipo} fixed top-4 right-4 p-4 rounded-lg shadow-lg z-50 max-w-md`;
    
    const bgColor = tipo === 'success' ? 'bg-green-100 border-green-500' : 'bg-red-100 border-red-500';
    const textColor = tipo === 'success' ? 'text-green-700' : 'text-red-700';
    
    alertDiv.className = `${bgColor} border-l-4 ${textColor} p-4 fixed top-4 right-4 rounded shadow-lg z-50 max-w-md`;
    alertDiv.innerHTML = `
        <div class="flex items-center justify-between">
            <p class="font-medium">${mensaje}</p>
            <button onclick="this.parentElement.parentElement.remove()" class="ml-4 text-xl">×</button>
        </div>
    `;
    
    document.body.appendChild(alertDiv);
    
    setTimeout(() => {
        alertDiv.remove();
    }, 5000);
}

// Extraer parámetro de URL
function getUrlParameter(name) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(name);
}

// Validar email
function validarEmail(email) {
    const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return re.test(email);
}

// Obtener color de categoría
function obtenerColorCategoria(color) {
    return color || '#3B82F6';
}

// Crear badge de categoría
function crearBadgeCategoria(categoria) {
    return `
        <span class="inline-block px-3 py-1 text-xs font-semibold rounded-full mr-2" 
              style="background-color: ${categoria.color}20; color: ${categoria.color}">
            ${categoria.icono} ${categoria.nombre}
        </span>
    `;
}

// Crear badge de tipo de contenido
function crearBadgeTipo(tipo) {
    const colores = {
        'artículo': 'bg-blue-100 text-blue-800',
        'tip': 'bg-yellow-100 text-yellow-800',
        'noticia': 'bg-red-100 text-red-800',
        'tutorial': 'bg-green-100 text-green-800',
        'recurso': 'bg-purple-100 text-purple-800'
    };
    
    return `<span class="px-2 py-1 text-xs font-semibold rounded ${colores[tipo] || 'bg-gray-100 text-gray-800'}">${tipo}</span>`;
}

// Crear badge de estado
function crearBadgeEstado(estado) {
    const estilos = estado === 'publicado' 
        ? 'bg-green-100 text-green-800' 
        : 'bg-yellow-100 text-yellow-800';
    
    return `<span class="px-2 py-1 text-xs font-semibold rounded ${estilos}">${estado}</span>`;
}

// Loading spinner
function mostrarLoading() {
    const spinner = document.createElement('div');
    spinner.id = 'loading-spinner';
    spinner.className = 'fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50';
    spinner.innerHTML = `
        <div class="bg-white p-6 rounded-lg shadow-xl">
            <div class="animate-spin rounded-full h-16 w-16 border-b-2 border-blue-600 mx-auto"></div>
            <p class="mt-4 text-gray-700">Cargando...</p>
        </div>
    `;
    document.body.appendChild(spinner);
}

function ocultarLoading() {
    const spinner = document.getElementById('loading-spinner');
    if (spinner) {
        spinner.remove();
    }
}
