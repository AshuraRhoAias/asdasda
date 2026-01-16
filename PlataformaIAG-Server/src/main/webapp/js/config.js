// Configuración global de la aplicación
const CONFIG = {
    API_URL: 'http://localhost:8080/api',
    TOKEN_KEY: 'iag_token',
    USER_KEY: 'iag_user'
};

// Verificar si el usuario está autenticado
function isAuthenticated() {
    return localStorage.getItem(CONFIG.TOKEN_KEY) !== null;
}

// Obtener token
function getToken() {
    return localStorage.getItem(CONFIG.TOKEN_KEY);
}

// Obtener usuario
function getUser() {
    const userJson = localStorage.getItem(CONFIG.USER_KEY);
    return userJson ? JSON.parse(userJson) : null;
}

// Guardar sesión
function saveSession(token, user) {
    localStorage.setItem(CONFIG.TOKEN_KEY, token);
    localStorage.setItem(CONFIG.USER_KEY, JSON.stringify(user));
}

// Cerrar sesión
function logout() {
    localStorage.removeItem(CONFIG.TOKEN_KEY);
    localStorage.removeItem(CONFIG.USER_KEY);
    window.location.href = '/web/login.html';
}

function isAdmin() {
    const user = getUser();
    if (!user || !user.rol) {
        return false;
    }
    return user.rol.toLowerCase().trim() === 'admin';
}