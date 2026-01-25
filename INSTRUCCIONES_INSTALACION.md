# Guía de Instalación y Ejecución - Control de Accesos

Esta aplicación es "portable", lo que significa que no necesita instalación de Java ni configuración compleja en la otra máquina. Ya incluye todo lo necesario.

## Paso 1: Copiar Archivos
Debes copiar la carpeta completa `ControlAccesos` que se generó (ubicada dentro de `dist`).

1. Copia la carpeta `ControlAccesos` a un USB o envíala a la otra máquina.
   - Asegúrate de copiar **toda la carpeta**, no solo el `.exe`.
   - La carpeta debe contener:
     - `ControlAccesos.exe`
     - `acceso.db` (La base de datos)
     - `assets` (Carpeta con imágenes y web)
     - `runtime` (Carpeta oculta con Java)

## Paso 2: Ejecutar en la Otra Máquina
1. Abre la carpeta `ControlAccesos` en la nueva PC.
2. Haz doble clic en **`ControlAccesos.exe`**.

## ¿Qué pasará?
Se abrirán dos cosas:
1. **Una ventana negra (Consola)**: Esta es el servidor. **NO LA CIERRES** mientras uses el programa.
   - Debería decir: `Servidor iniciado en puerto 8081`.
2. **El Panel Administrativo (Ventana con botones)**: Aquí puedes agregar estudiantes y ver la lista.

## Paso 3: Usar la Web
Para ver la interfaz de acceso (Login/Scanner):
1. Abre Google Chrome o Edge.
2. Escribe en la barra de direcciones:
   ```
   http://localhost:8081
   ```
   (O usa `ngrok` si vas a acceder desde internet, apuntando al puerto 8081).

## Solución de Problemas
- **Pantalla en blanco o error de puerto**: Si el puerto 8081 está ocupado, reinicia la máquina o cierra aplicaciones que usen ese puerto.
- **Base de Datos**: Los datos se guardan en el archivo `acceso.db` dentro de la misma carpeta. Si borras la carpeta, pierdes los datos.
