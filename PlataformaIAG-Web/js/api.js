// Módulo para consumir la API REST
const API = {
    // Autenticación
    login: async (email, password) => {
        const response = await fetch(`${CONFIG.API_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ email, password })
        });
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
        return await response.json();
    },

    validateToken: async () => {
        const response = await fetch(`${CONFIG.API_URL}/auth/validate`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${getToken()}`
            }
        });
        return await response.json();
    },

    // Contenidos
    obtenerContenidos: async (estado = null) => {
        let url = `${CONFIG.API_URL}/contenidos`;
        if (estado) {
            url += `?estado=${estado}`;
        }

        const response = await fetch(url);
        return await response.json();
    },

    obtenerContenidoPorId: async (id) => {
        const response = await fetch(`${CONFIG.API_URL}/contenidos/${id}`);
        return await response.json();
    },

    obtenerContenidosPorCategoria: async (categoriaId) => {
        const response = await fetch(`${CONFIG.API_URL}/contenidos/categoria/${categoriaId}`);
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
        return await response.json();
    },

    eliminarContenido: async (id) => {
        const response = await fetch(`${CONFIG.API_URL}/contenidos/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${getToken()}`
            }
        });
        return await response.json();
    },

    contarContenidos: async () => {
        const token = localStorage.getItem("token");
        

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


    // Categorías
    obtenerCategorias: async () => {
        const response = await fetch(`${CONFIG.API_URL}/categorias`);
        return await response.json();
    },

    obtenerCategoriaPorId: async (id) => {
        const response = await fetch(`${CONFIG.API_URL}/categorias/${id}`);
        return await response.json();
    },

    contarCategorias: async () => {
        const response = await fetch(`${CONFIG.API_URL}/categorias/count`);
        return await response.json();
    },

    // Usuarios
    contarUsuarios: async () => {
        const response = await fetch(`${CONFIG.API_URL}/usuarios/count`);
        return await response.json();
    }
};
