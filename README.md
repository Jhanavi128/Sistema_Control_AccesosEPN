# Sistema de Control de Accesos EPN

## 🚀 Instrucciones para Ejecutar en Cualquier PC Windows

### Requisitos Previos
- **Java 16 o superior** instalado en el sistema
- Windows 7 o superior

### 📦 Instalación

1. **Copiar toda la carpeta** `Sistema_Control_AccesosEPN-jhanavi` a cualquier ubicación en tu PC
   - Puede ser: `C:\Usuarios\TuNombre\Documentos\`
   - O cualquier otra carpeta

2. **NO modificar la estructura interna** de carpetas:
   ```
   Sistema_Control_AccesosEPN-jhanavi/
   ├── SistemaAccesos.bat  ← Ejecutar este archivo
   ├── bin/                ← Clases compiladas
   ├── accesoEPN/
   │   ├── lib/            ← Librerías (.jar)
   │   └── Storage/
   │       └── DataBase/   ← Base de datos SQLite
   └── Storage/
       └── Logs/           ← Archivos de log
   ```

### ▶️ Ejecución

**Opción 1: Doble clic en `SistemaAccesos.bat`**
- Navega a la carpeta principal
- Doble clic en `SistemaAccesos.bat`
- El script verificará automáticamente:
  - ✅ Que Java esté instalado
  - ✅ Que todas las carpetas necesarias existan
  - ✅ Que la base de datos esté presente

**Opción 2: Desde CMD/PowerShell**
```cmd
cd ruta\a\Sistema_Control_AccesosEPN-jhanavi
SistemaAccesos.bat
```

### ❌ Solución de Problemas

#### Error: "Java no está instalado"
**Solución:**
1. Descargar e instalar Java: https://www.oracle.com/java/technologies/downloads/
2. Durante la instalación, marcar "Add to PATH"
3. Reiniciar la terminal

#### Error: "No se encuentra la carpeta 'bin'"
**Solución:**
- Asegúrate de copiar **toda la carpeta completa**, no solo algunos archivos

#### Error: "No se encuentra la base de datos"
**Solución:**
- Verifica que existe: `accesoEPN\Storage\DataBase\bd_acceso_epn.sqlite`
- Si falta, restaura desde una copia de respaldo

### 🌐 Servidor Web

La aplicación inicia automáticamente un servidor web en:
- **Puerto:** 8080
- **URL:** http://localhost:8080

### 🖥️ Interfaz Desktop

La interfaz gráfica se abre automáticamente al ejecutar el .bat

### 📝 Logs

Los errores se guardan en:
- `Storage/Logs/AppErrors.log`

### ⚠️ Advertencias SQLite

Si ves advertencias como:
```
WARNING: java.lang.System::load has been called
WARNING: Restricted methods will be blocked in a future release
```

**No te preocupes**: Son advertencias normales de SQLite JDBC. El script `.bat` ya incluye el argumento `--enable-native-access=ALL-UNNAMED` para evitarlas.

---

## 🔧 Para Desarrolladores

### Compilar el proyecto
```cmd
javac -d bin -cp "accesoEPN/lib/*" accesoEPN/src/**/*.java
```

### Ejecutar sin el .bat
```cmd
java --enable-native-access=ALL-UNNAMED -cp "bin;accesoEPN/lib/*" App
```

### Estructura del Proyecto
- **DataAccess**: DAOs y DTOs para acceso a datos
- **BusinessLogic**: Lógica de negocio
- **App**: Interfaces (Desktop y Web)
- **Infrastructure**: Configuración y utilidades

---

**Versión**: 1.0  
**Autor**: Sistema de Control de Accesos EPN
