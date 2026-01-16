// Módulo para consumir la API REST
const API = {
    // ========== AUTENTICACIÓN ==========
    login: async (email, password) => {
        const response = await fetch(`${CONFIG.API_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Error en el login');
        }
        
        return await response.json();
    },

    registro: async (nombre, email, password) => {
        const response = await fetch(`${CONFIG.API_URL}/auth/registro`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ nombre, email, password })
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Error en el registro');
        }
        
        return await response.json();
    },

    validateToken: async () => {
        const response = await fetch(`${CONFIG.API_URL}/auth/validate`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${getToken()}`
            }
        });
        
        if (!response.ok) {
            throw new Error('Token inválido');
        }
        
        return await response.json();
    },

    // ========== CONTENIDOS ==========
    obtenerContenidos: async (estado = null) => {
        let url = `${CONFIG.API_URL}/contenidos`;
        if (estado) {
            url += `?estado=${estado}`;
        }

        const response = await fetch(url);
        
        if (!response.ok) {
            throw new Error(`Error al obtener contenidos: ${response.status}`);
        }
        
        return await response.json();
    },

    obtenerContenidoPorId: async (id) => {
        const response = await fetch(`${CONFIG.API_URL}/contenidos/${id}`);
        
        if (!response.ok) {
            throw new Error(`Error al obtener contenido: ${response.status}`);
        }
        
        return await response.json();
    },

    obtenerContenidosPorCategoria: async (categoriaId) => {
        const response = await fetch(`${CONFIG.API_URL}/contenidos/categoria/${categoriaId}`);
        
        if (!response.ok) {
            throw new Error(`Error al obtener contenidos por categoría: ${response.status}`);
        }
        
        return await response.json();
    },

    crearContenido: async (contenido) => {
        const response = await fetch(`${CONFIG.API_URL}/contenidos`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
            body: JSON.stringify(contenido)
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Error al crear contenido');
        }
        
        return await response.json();
    },

    actualizarContenido: async (id, contenido) => {
        const response = await fetch(`${CONFIG.API_URL}/contenidos/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
            body: JSON.stringify(contenido)
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Error al actualizar contenido');
        }
        
        return await response.json();
    },

    eliminarContenido: async (id) => {
        const response = await fetch(`${CONFIG.API_URL}/contenidos/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${getToken()}`
            }
        });
        
        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Error al eliminar contenido');
        }
        
        return await response.json();
    },

    contarContenidos: async () => {
        const token = getToken();

        const response = await fetch(`${CONFIG.API_URL}/contenidos/count`, {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`,
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || "Error al contar contenidos");
        }

        return await response.json();
    },

    // ========== CATEGORÍAS ==========
    obtenerCategorias: async () => {
        const response = await fetch(`${CONFIG.API_URL}/categorias`);
        
        if (!response.ok) {
            throw new Error(`Error al obtener categorías: ${response.status}`);
        }
        
        return await response.json();
    },

    obtenerCategoriaPorId: async (id) => {
        const response = await fetch(`${CONFIG.API_URL}/categorias/${id}`);
        
        if (!response.ok) {
            throw new Error(`Error al obtener categoría: ${response.status}`);
        }
        
        return await response.json();
    },

    contarCategorias: async () => {
        const response = await fetch(`${CONFIG.API_URL}/categorias/count`);
        
        if (!response.ok) {
            throw new Error(`Error al contar categorías: ${response.status}`);
        }
        
        return await response.json();
    },

    // ========== USUARIOS ==========
    contarUsuarios: async () => {
        const response = await fetch(`${CONFIG.API_URL}/usuarios/count`);
        
        if (!response.ok) {
            throw new Error(`Error al contar usuarios: ${response.status}`);
        }
        
        return await response.json();
    }
};