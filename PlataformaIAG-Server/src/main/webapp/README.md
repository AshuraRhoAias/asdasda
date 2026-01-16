# 🌐 Plataforma IAG - Web (Frontend)

## 📋 Descripción
Frontend web que consume la API REST del servidor PlataformaIAG-Server.

## 🛠️ Tecnologías
- HTML5
- CSS3 (TailwindCSS vía CDN)
- JavaScript (Vanilla JS)
- Fetch API para consumir REST
- LocalStorage para JWT

## 📂 Estructura del Proyecto
```
PlataformaIAG-Web/
├── web/
│   ├── index.html
│   ├── login.html
│   ├── registro.html
│   ├── home.html
│   ├── contenido-detalle.html
│   └── admin/
│       ├── dashboard.html
│       ├── contenido-nuevo.html
│       └── contenido-editar.html
├── css/
│   └── styles.css
└── js/
    ├── config.js
    ├── auth.js
    ├── api.js
    └── utils.js
```

## ⚙️ Configuración

### 1. Configurar URL del servidor
Editar `js/config.js` con la URL de tu servidor:
```javascript
const API_URL = 'http://localhost:8080/plataforma-iag-server/api';
```

### 2. Ejecutar
- Abrir con un servidor local (Live Server, XAMPP, etc.)
- O simplemente abrir `index.html` en el navegador

## 🔌 Consume los siguientes endpoints

### Autenticación
- POST /api/auth/login
- POST /api/auth/registro
- POST /api/auth/validate

### Contenidos
- GET /api/contenidos
- GET /api/contenidos/{id}
- POST /api/contenidos (admin)
- PUT /api/contenidos/{id} (admin)
- DELETE /api/contenidos/{id} (admin)

### Categorías
- GET /api/categorias

## 🔐 Autenticación
- JWT almacenado en localStorage
- Header: `Authorization: Bearer {token}`
- Auto-redirect si no autenticado

## 📝 Credenciales de prueba
- **Admin:** admin@iag.com / admin123
- **Usuario:** usuario@iag.com / usuario123
