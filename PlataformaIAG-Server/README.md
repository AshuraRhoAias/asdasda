# 🖥️ Plataforma IAG - Server (Backend API REST)

## 📋 Descripción
Backend Java Jakarta EE que proporciona API REST para la Plataforma IAG.

## 🛠️ Tecnologías
- Java 11+
- Jakarta EE 9
- JAX-RS (REST API)
- MySQL 8.0
- Maven
- JDBC

## 📂 Estructura del Proyecto
```
PlataformaIAG-Server/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── iag/
│       │           ├── config/
│       │           ├── models/
│       │           ├── dao/
│       │           ├── resources/
│       │           ├── filters/
│       │           └── utils/
│       └── resources/
├── pom.xml
└── README.md
```

## ⚙️ Configuración

### 1. Base de datos
- Importar script: `database/schema.sql`
- Configurar credenciales en `DatabaseConfig.java`

### 2. Ejecutar
```bash
mvn clean install
mvn exec:java
```

## 🔌 Endpoints API

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/registro` - Registrar usuario
- `POST /api/auth/logout` - Cerrar sesión

### Contenidos
- `GET /api/contenidos` - Listar todos
- `GET /api/contenidos/publicados` - Solo publicados
- `GET /api/contenidos/{id}` - Obtener por ID
- `POST /api/contenidos` - Crear (admin)
- `PUT /api/contenidos/{id}` - Actualizar (admin)
- `DELETE /api/contenidos/{id}` - Eliminar (admin)
- `GET /api/contenidos/categoria/{id}` - Filtrar por categoría

### Categorías
- `GET /api/categorias` - Listar todas
- `GET /api/categorias/{id}` - Obtener por ID

### Usuarios
- `GET /api/usuarios` - Listar todos (admin)
- `GET /api/usuarios/count` - Contar usuarios

## 🔐 Seguridad
- Autenticación JWT
- Passwords con SHA-256
- CORS habilitado
- Filtros de autorización

## 📝 Credenciales de prueba
- **Admin:** admin@iag.com / admin123
- **Usuario:** usuario@iag.com / usuario123
