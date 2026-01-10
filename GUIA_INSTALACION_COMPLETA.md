# 📘 GUÍA COMPLETA DE INSTALACIÓN - PLATAFORMA IAG

## 🎯 RESUMEN DEL PROYECTO

Este proyecto consta de **2 aplicaciones separadas**:

1. **PlataformaIAG-Server** - Backend Java Jakarta EE (API REST)
2. **PlataformaIAG-Web** - Frontend HTML/CSS/JavaScript

---

## 📦 PROYECTO 1: PlataformaIAG-Server (Backend)

### ✅ Requisitos Previos

- **Java JDK 11+**
- **Apache NetBeans 12+** o cualquier IDE compatible
- **Apache Tomcat 10** o **GlassFish 6** (servidor Jakarta EE)
- **MySQL 8.0+**
- **Maven 3.6+**

### 📂 Estructura del Proyecto

```
PlataformaIAG-Server/
├── src/main/java/
│   └── com/iag/
│       ├── JaxRsApplication.java
│       ├── config/
│       │   └── DatabaseConfig.java
│       ├── models/
│       │   ├── Usuario.java
│       │   ├── Contenido.java
│       │   └── Categoria.java
│       ├── dao/
│       │   ├── UsuarioDAO.java
│       │   ├── ContenidoDAO.java
│       │   └── CategoriaDAO.java
│       ├── resources/
│       │   ├── AuthResource.java
│       │   ├── ContenidoResource.java
│       │   ├── CategoriaResource.java
│       │   └── UsuarioResource.java
│       ├── filters/
│       │   └── CorsFilter.java
│       └── utils/
│           ├── HashUtils.java
│           └── JWTUtil.java
├── src/main/webapp/WEB-INF/
│   └── web.xml
├── database/
│   └── schema.sql
├── pom.xml
└── README.md
```

---

## 🚀 INSTALACIÓN DEL SERVIDOR (Backend)

### Paso 1: Crear Base de Datos

1. Abre **MySQL Workbench** o tu cliente MySQL
2. Ejecuta el script `database/schema.sql`:

```sql
CREATE DATABASE plataforma_iag;
USE plataforma_iag;
-- El resto del script está en schema.sql
```

### Paso 2: Configurar Conexión a BD

Edita `src/main/java/com/iag/config/DatabaseConfig.java`:

```java
private static final String URL = "jdbc:mysql://localhost:3306/plataforma_iag";
private static final String USER = "root";
private static final String PASSWORD = "tu_password"; // CAMBIAR AQUÍ
```

### Paso 3: Crear Proyecto en NetBeans

1. **Abrir NetBeans**
2. `File` → `New Project`
3. Seleccionar: `Java with Maven` → `Web Application`
4. Nombre del proyecto: `PlataformaIAG-Server`
5. Click en `Finish`

### Paso 4: Copiar Archivos

1. Copia todos los archivos de `src/main/java/com/iag/` al proyecto
2. Copia `src/main/webapp/WEB-INF/web.xml`
3. Reemplaza el `pom.xml` con el proporcionado

### Paso 5: Agregar Dependencias Maven

NetBeans descargará automáticamente las dependencias al abrir el `pom.xml`:
- Jakarta EE API
- MySQL Connector
- Gson
- JWT (jjwt)

### Paso 6: Configurar Servidor de Aplicaciones

1. En NetBeans: Click derecho en el proyecto → `Properties`
2. `Run` → `Server`: Selecciona **Apache Tomcat** o **GlassFish**
3. `OK`

### Paso 7: Compilar y Desplegar

1. Click derecho en el proyecto → `Clean and Build`
2. Click derecho → `Run`
3. El servidor se desplegará en: `http://localhost:8080/plataforma-iag-server/`

### 🔌 Verificar API REST

Prueba los endpoints:

```
GET  http://localhost:8080/plataforma-iag-server/api/categorias
GET  http://localhost:8080/plataforma-iag-server/api/contenidos?estado=publicado
POST http://localhost:8080/plataforma-iag-server/api/auth/login
```

---

## 📦 PROYECTO 2: PlataformaIAG-Web (Frontend)

### ✅ Requisitos Previos

- **Navegador web moderno** (Chrome, Firefox, Edge)
- **Servidor web local** (opcional): Live Server, XAMPP, WAMP, o similar

### 📂 Estructura del Proyecto

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
├── js/
│   ├── config.js
│   ├── api.js
│   └── utils.js
├── css/
│   └── styles.css
└── README.md
```

---

## 🚀 INSTALACIÓN DEL FRONTEND

### Paso 1: Configurar URL del Backend

Edita `js/config.js`:

```javascript
const CONFIG = {
    API_URL: 'http://localhost:8080/plataforma-iag-server/api',
    // Si desplegaste en un puerto diferente, cámbialo aquí
};
```

### Paso 2: Opción A - Servidor Web Local (Recomendado)

#### Usando Visual Studio Code con Live Server:
1. Instala la extensión **Live Server**
2. Click derecho en `web/index.html`
3. Selecciona `Open with Live Server`

#### Usando XAMPP/WAMP:
1. Copia la carpeta `PlataformaIAG-Web` a `htdocs` (XAMPP) o `www` (WAMP)
2. Abre: `http://localhost/PlataformaIAG-Web/web/index.html`

### Paso 3: Opción B - Abrir Directamente en el Navegador

1. Navega a la carpeta del proyecto
2. Doble click en `web/index.html`

> **Nota**: Algunos navegadores pueden bloquear peticiones AJAX desde archivos locales. Se recomienda usar un servidor web local.

---

## 🔐 CREDENCIALES DE PRUEBA

| Rol      | Email              | Contraseña |
|----------|--------------------|------------|
| Admin    | admin@iag.com      | admin123   |
| Usuario  | usuario@iag.com    | usuario123 |

---

## 🧪 PRUEBAS DE FUNCIONALIDAD

### 1. Probar Registro e Inicio de Sesión

1. Abre `http://localhost/PlataformaIAG-Web/web/index.html`
2. Click en "Registrarse"
3. Completa el formulario
4. Inicia sesión con las credenciales creadas

### 2. Probar Como Usuario Normal

- Ver contenidos publicados
- Filtrar por categoría
- Ver detalle de contenido
- Ver incremento de vistas

### 3. Probar Como Administrador

Inicia sesión con `admin@iag.com`:

1. Accede al Dashboard Admin
2. Crea un nuevo contenido
3. Asigna múltiples categorías
4. Edita un contenido existente
5. Elimina un contenido

---

## 🌐 ENDPOINTS API DISPONIBLES

### Autenticación
- `POST /api/auth/login` - Iniciar sesión
- `POST /api/auth/registro` - Registrar usuario
- `POST /api/auth/validate` - Validar token

### Contenidos
- `GET /api/contenidos` - Listar todos
- `GET /api/contenidos?estado=publicado` - Solo publicados
- `GET /api/contenidos/{id}` - Obtener por ID
- `GET /api/contenidos/categoria/{id}` - Filtrar por categoría
- `POST /api/contenidos` - Crear (admin)
- `PUT /api/contenidos/{id}` - Actualizar (admin)
- `DELETE /api/contenidos/{id}` - Eliminar (admin)
- `GET /api/contenidos/count` - Contar total

### Categorías
- `GET /api/categorias` - Listar todas
- `GET /api/categorias/{id}` - Obtener por ID
- `GET /api/categorias/count` - Contar total

### Usuarios
- `GET /api/usuarios/count` - Contar total

---

## 🐛 SOLUCIÓN DE PROBLEMAS COMUNES

### Error: "Cannot connect to database"

**Solución:**
1. Verifica que MySQL esté corriendo
2. Revisa las credenciales en `DatabaseConfig.java`
3. Asegúrate de que la base de datos existe

### Error: "ClassNotFoundException: com.mysql.cj.jdbc.Driver"

**Solución:**
1. Asegúrate de que `mysql-connector-java` esté en `pom.xml`
2. Ejecuta `mvn clean install`
3. Reinicia el servidor

### Error: "CORS policy error"

**Solución:**
- El `CorsFilter.java` ya está configurado para permitir todas las peticiones
- Verifica que el filtro esté correctamente anotado con `@WebFilter("/*")`

### Error: "404 Not Found" en endpoints

**Solución:**
1. Verifica que el servidor esté corriendo
2. Revisa la URL base en `js/config.js`
3. Asegúrate de que la ruta incluya `/api/` después del contexto

### Frontend no carga datos

**Solución:**
1. Abre la Consola del Navegador (F12)
2. Verifica errores en la pestaña "Console"
3. Revisa las peticiones en la pestaña "Network"
4. Confirma que la URL del backend sea correcta en `config.js`

---

## 📋 CHECKLIST DE VERIFICACIÓN

### Backend (PlataformaIAG-Server)
- [ ] Base de datos creada e importado `schema.sql`
- [ ] Credenciales de BD configuradas en `DatabaseConfig.java`
- [ ] Dependencias Maven descargadas
- [ ] Proyecto compila sin errores
- [ ] Servidor desplegado correctamente
- [ ] Endpoints responden (probar con Postman/browser)

### Frontend (PlataformaIAG-Web)
- [ ] URL del backend configurada en `config.js`
- [ ] Archivos copiados correctamente
- [ ] Página index.html abre sin errores
- [ ] Login funciona correctamente
- [ ] Registro funciona correctamente
- [ ] Home muestra contenidos
- [ ] Dashboard admin accesible

---

## 🎓 FLUJO DE LA APLICACIÓN

```
1. Usuario visita index.html
   ↓
2. Click en "Explorar" → home.html (muestra contenidos publicados)
   ↓
3. Click en contenido → contenido-detalle.html (incrementa vistas)
   ↓
4. Click en "Iniciar Sesión" → login.html
   ↓
5. Si es Admin → admin/dashboard.html
   Si es Usuario → home.html
   ↓
6. Admin puede:
   - Crear contenido (admin/contenido-nuevo.html)
   - Editar contenido (admin/contenido-editar.html)
   - Eliminar contenido
   - Ver estadísticas
```

---

## 📞 SOPORTE

Si encuentras algún problema:

1. **Revisa los logs del servidor** (consola de NetBeans)
2. **Revisa la consola del navegador** (F12)
3. **Verifica que ambos proyectos estén corriendo**
4. **Comprueba las credenciales de BD**

---

## ✅ FUNCIONALIDADES IMPLEMENTADAS

- [x] Registro de usuarios
- [x] Login con validación JWT
- [x] Ver todos los contenidos publicados
- [x] Filtrar contenidos por categoría
- [x] Ver detalle de contenido
- [x] Contador de vistas automático
- [x] Panel admin con estadísticas
- [x] Crear nuevo contenido (admin)
- [x] Editar contenido existente (admin)
- [x] Eliminar contenido (admin)
- [x] Asignar múltiples categorías
- [x] Control de acceso por rol
- [x] API REST completa
- [x] Hash de contraseñas (SHA-256)
- [x] CORS configurado
- [x] Validación de tokens
- [x] Interfaz responsive

---

¡Listo! Tu aplicación **Plataforma IAG** está completamente configurada y funcionando. 🎉
