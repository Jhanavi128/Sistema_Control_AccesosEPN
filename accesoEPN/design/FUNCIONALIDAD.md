# Funcionalidad del Sistema de Control de Accesos EPN

## 🎯 Descripción General

Sistema de control de accesos para la Escuela Politécnica Nacional que permite:
- Gestión de estudiantes por parte de administradores
- Generación de credenciales digitales con QR para estudiantes
- Validación de acceso mediante código QR

---

## 📱 Interfaz Desktop (Administrador)

### Funcionalidades Implementadas:

#### ✅ Gestión Completa de Estudiantes
- **Crear** nuevo estudiante con todos sus datos
- **Editar** información de estudiantes existentes
- **Eliminar** estudiante (borrado lógico - estado: 'X')
- **Buscar** estudiantes por nombre, apellido o código

#### ✅ Datos del Estudiante
- Código Único (9 dígitos obligatorios)
- Nombre y Apellido
- Cédula de identidad
- Fecha de nacimiento
- Sexo
- Periodo académico (carrera y semestre)
- Fotografía del estudiante
- PIN de acceso (4 dígitos)

#### ✅ Validaciones Implementadas
- Código único debe tener exactamente 9 dígitos
- No se permiten códigos únicos duplicados (en estudiantes activos)
- No se permiten cédulas duplicadas (en estudiantes activos)
- Todos los campos obligatorios deben estar llenos
- La foto debe copiarse a la carpeta Web/Public para visualización

---

## 🌐 Interfaz Web (Estudiantes)

### Funcionalidades Implementadas:

#### ✅ Autenticación
- **Login** con código único y PIN
- Validación de credenciales contra la base de datos
- Solo usuarios con estado 'A' (activo) pueden ingresar

#### ✅ Credencial Digital
- **Generación automática de QR** con el código único
- **Visualización de datos del estudiante**:
  - Foto del estudiante
  - Nombre completo
  - Carrera
  - Periodo académico
  - Código único (para verificación)
  
#### ✅ Navegación
- Botón "Volver al Inicio" para regresar al login
- Al volver, se limpia automáticamente la sesión

### Tecnología Web
- Servidor HTTP en puerto **8081**
- Páginas HTML/CSS/JavaScript estáticas
- Sin frameworks pesados - código puro
- QR generado con librería QRCode.js

---

## 💾 Base de Datos (SQLite)

### Tablas Principales:

#### ✅ Usuario
```sql
- IdUsuario (PK)
- CodigoUnico (único, 9 dígitos)
- Contrasena (PIN encriptado)
- Rol (Estudiante, Administrador, Guardia)
- Estado (A = Activo, X = Eliminado)
- FechaCreacion
- FechaModifica
```

#### ✅ Estudiante
```sql
- IdEstudiante (PK)
- IdUsuario (FK)
- IdPeriodo (FK)
- Nombre
- Apellido
- Cedula
- CodigoUnico
- FechaNacimiento
- Sexo
- FotoPath (ruta relativa a la imagen)
- Estado (A/X)
- FechaCreacion
- FechaModifica
```

#### ✅ Periodo
```sql
- IdPeriodo (PK)
- Nombre (ej: "2024-A")
- Carrera (ej: "Ingeniería en Software")
- Estado (A/X)
```

#### ✅ QRAcceso
```sql
- IdQRAcceso (PK)
- IdUsuario (FK)
- CodigoQR (código que se escanea)
- FechaGeneracion
- FechaExpiracion
- Estado (1 = activo, 0 = inactivo)
```

#### ✅ RegistroIngreso
```sql
- IdRegistroIngreso (PK)
- IdUsuario (FK)
- FechaHoraIngreso
- FechaHoraSalida
- Estado
```

---

## 🔄 Flujo de Funcionamiento

### 1️⃣ Administrador Registra Estudiante (Desktop)
1. Abre la aplicación desktop
2. Llena el formulario con datos del estudiante
3. Carga foto del estudiante
4. Asigna PIN de 4 dígitos
5. Sistema valida datos
6. Crea registro en tablas Usuario y Estudiante

### 2️⃣ Estudiante Genera su Credencial (Web)
1. Accede a http://localhost:8081
2. Ingresa código único y PIN
3. Sistema valida credenciales
4. Si válido → Muestra credencial digital con:
   - QR del código único
   - Foto
   - Datos personales y académicos

### 3️⃣ Estudiante Usa su Credencial
1. Muestra el QR en su teléfono
2. Personal de seguridad puede escanear el QR
3. El código QR contiene el código único del estudiante
4. Sistema puede validar el acceso consultando la BD

---

## 📂 Estructura de Archivos

```
Sistema_Control_AccesosEPN-jhanavi/
├── SistemaAccesos.bat          # Script de inicio
├── bin/                        # Clases compiladas
├── accesoEPN/
│   ├── src/
│   │   ├── App.java           # Punto de entrada
│   │   ├── DataAccess/        # DAOs y DTOs
│   │   ├── BusinessLogic/     # Lógica de negocio
│   │   ├── Infrastructure/    # Configuración
│   │   └── Web/
│   │       ├── Server/        # Handlers HTTP
│   │       └── Public/        # HTML, CSS (login, credencial)
│   ├── lib/                   # JARs (SQLite, Gson, QR, etc.)
│   └── Storage/
│       └── DataBase/          # bd_acceso_epn.sqlite
└── Storage/
    └── Logs/                  # AppErrors.log
```

---

## 🚀 Ejecución

### Iniciar el Sistema
```cmd
SistemaAccesos.bat
```

El script:
1. Verifica que Java esté instalado
2. Valida estructura de directorios
3. Inicia servidor web (puerto 8081)
4. Abre interfaz desktop de administrador

### Acceder desde el Navegador
```
http://localhost:8081
```

---

## ✅ Características Técnicas

### Arquitectura N-Capas
- **Presentación**: Desktop (Swing) + Web (HTML/JS)
- **Lógica de Negocio**: Validaciones, reglas de negocio
- **Acceso a Datos**: DAOs con patrón genérico
- **Persistencia**: SQLite

### Portabilidad
- ✅ Rutas relativas (no absolutas)
- ✅ Base de datos SQLite embebida
- ✅ Sin dependencias del sistema operativo
- ✅ Funciona en cualquier PC con Java 16+

### Manejo de Errores
- ✅ Logs centralizados en `Storage/Logs/AppErrors.log`
- ✅ Validaciones con mensajes claros al usuario
- ✅ Excepciones personalizadas (`AppException`)

---

## 🔒 Seguridad Básica

- Contraseñas almacenadas (mejorable con hash)
- Validación de estado de usuario (activo/inactivo)
- Borrado lógico (no se eliminan datos físicamente)
- Validación de unicidad de códigos y cédulas

---

## 📊 Requerimientos del Sistema

### Software Necesario
- Java 16 o superior
- Windows 7 o superior

### Hardware Mínimo
- 2 GB RAM
- 100 MB de espacio en disco
- Procesador de 1 GHz o superior

---

## 🎨 Interfaz de Usuario

### Desktop
- Basada en Java Swing
- Estilo personalizado (PatButton, PatLabel, etc.)
- Formularios validados
- Tabla de búsqueda de estudiantes

### Web
- Login simple y limpio
- Credencial digital responsive
- QR generado en tiempo real
- Diseño minimalista

---

**Versión**: 1.0  
**Fecha de Actualización**: 29 de Enero de 2026
