-- Base de datos Plataforma IAG
CREATE DATABASE IF NOT EXISTS bryan;
USE bryan;

-- Tabla de usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    rol ENUM('admin', 'usuario') DEFAULT 'usuario',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de categorías
CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    icono VARCHAR(10),
    color VARCHAR(7)
);

-- Tabla de contenidos
CREATE TABLE contenidos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    cuerpo TEXT NOT NULL,
    tipo ENUM('artículo', 'tip', 'noticia', 'tutorial', 'recurso') DEFAULT 'artículo',
    estado ENUM('borrador', 'publicado') DEFAULT 'borrador',
    vistas INT DEFAULT 0,
    fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    autor_id INT,
    FOREIGN KEY (autor_id) REFERENCES usuarios(id) ON DELETE SET NULL
);

-- Tabla relacional contenido-categorías
CREATE TABLE contenido_categorias (
    contenido_id INT,
    categoria_id INT,
    PRIMARY KEY (contenido_id, categoria_id),
    FOREIGN KEY (contenido_id) REFERENCES contenidos(id) ON DELETE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE CASCADE
);

-- Insertar usuario admin por defecto
INSERT INTO usuarios (nombre, email, password, rol) 
VALUES ('Administrador', 'admin@iag.com', 
        'c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd4', 
        'admin');

-- Insertar usuario normal de prueba
INSERT INTO usuarios (nombre, email, password, rol) 
VALUES ('Usuario Demo', 'usuario@iag.com', 
        '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 
        'usuario');

-- Insertar categorías
INSERT INTO categorias (nombre, descripcion, icono, color) VALUES
('Generación de Texto', 'Herramientas y modelos para crear contenido escrito', '✍️', '#3B82F6'),
('Generación de Imágenes', 'IA para crear arte y diseños visuales', '🎨', '#EC4899'),
('Audio y Video', 'Procesamiento y generación de multimedia', '🎵', '#8B5CF6'),
('Generación de Código', 'Asistentes de programación con IA', '💻', '#10B981');

-- Insertar contenidos de ejemplo
INSERT INTO contenidos (titulo, cuerpo, tipo, estado, autor_id) VALUES
('Introducción a GPT-4', 
 'GPT-4 es el último modelo de lenguaje de OpenAI, que representa un avance significativo en la comprensión y generación de texto. Este modelo puede realizar tareas complejas de razonamiento, programación, y creatividad con una precisión sin precedentes. Sus aplicaciones van desde la asistencia en educación hasta el desarrollo de software avanzado.',
 'artículo', 'publicado', 1),

('Tips para mejores prompts', 
 'Para obtener mejores resultados con modelos de IA generativa, es fundamental ser específico en tus instrucciones. Incluye contexto relevante, ejemplos cuando sea posible, y divide tareas complejas en pasos más pequeños. La claridad y la estructura son claves para respuestas de alta calidad.',
 'tip', 'publicado', 1),

('Claude 3 Sonnet lanzado', 
 'Anthropic ha lanzado Claude 3 Sonnet, un modelo equilibrado entre capacidad y velocidad. Este modelo destaca por su razonamiento avanzado, análisis de documentos largos, y capacidades de programación. Es ideal para aplicaciones empresariales que requieren respuestas rápidas y precisas.',
 'noticia', 'publicado', 1),

('Tutorial: Crear imágenes con DALL-E', 
 'DALL-E 3 permite crear imágenes impresionantes mediante descripciones textuales. En este tutorial aprenderás a formular prompts efectivos, ajustar estilos artísticos, y refinar tus resultados. Comenzaremos con conceptos básicos y avanzaremos hacia técnicas profesionales de generación de imágenes.',
 'tutorial', 'publicado', 1);

-- Asignar categorías a contenidos
INSERT INTO contenido_categorias (contenido_id, categoria_id) VALUES
(1, 1), (2, 1), (3, 1), (4, 2);
