# Script para subir cambios a GitHub
$ErrorActionPreference = "Continue"

Write-Host "Iniciando proceso de subida..."

# 1. Inicializar Git si no existe
if (-not (Test-Path ".git")) {
    Write-Host "Inicializando repositorio Git..."
    git init
}

# 2. Configurar Remoto
Write-Host "Configurando remoto..."
git remote remove origin 2>$null
git remote add origin "https://github.com/Jhanavi128/Sistema_Control_AccesosEPN.git"

# 3. Preparar Rama
Write-Host "Cambiando a rama 'john'..."
git fetch origin john 2>$null
if ($LASTEXITCODE -eq 0) {
    git checkout john 2>$null
    if ($LASTEXITCODE -ne 0) { git checkout -b john }
    try { git pull origin john --allow-unrelated-histories } catch {}
}
else {
    git checkout -b john 2>$null
}

# 4. Agregar Archivos
Write-Host "Agregando archivos (esto puede tardar unos segundos)..."
# Asegurarnos de que dist/ControlAccesos no esté ignorado
if (Test-Path ".gitignore") {
    Add-Content -Path ".gitignore" -Value "!dist/ControlAccesos" -Force
}

git add .

# 5. Confirmar y Subir
Write-Host "Creando commit..."
git commit -m "Subir carpeta Control Accesos (Ejecutable)" 2>$null

Write-Host "Subiendo a GitHub... (Te pedirá autenticación si no estás logueado)"
git push -u origin john

Write-Host "¡Proceso Completado!"
Write-Host "Verifica en: https://github.com/Jhanavi128/Sistema_Control_AccesosEPN/tree/john"
