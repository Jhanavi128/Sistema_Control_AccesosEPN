$ErrorActionPreference = "Stop"

# Configuration
$appName = "ControlAccesos"
$appVersion = "1.0"
$mainClass = "App"
$srcDir = "src"
$libDir = "lib"
$binDir = "bin"
$stagingDir = "staging"
$distDir = "dist"
$assetsDir = "assets"
$dbFile = "acceso.db"

# 1. Clean
Write-Host "Cleaning previous build..."
if (Test-Path $binDir) { Remove-Item -Recurse -Force $binDir }
if (Test-Path $stagingDir) { Remove-Item -Recurse -Force $stagingDir }
if (Test-Path $distDir) { Remove-Item -Recurse -Force $distDir }

# 2. Compile
Write-Host "Compiling Java sources..."
New-Item -ItemType Directory -Force -Path $binDir | Out-Null

# Construct classpath
$libs = Get-ChildItem -Path $libDir -Filter *.jar
$classPath = ($libs.FullName -join ";")

# Get all java files
$javaFiles = Get-ChildItem -Path $srcDir -Recurse -Filter *.java | Select-Object -ExpandProperty FullName

if ($javaFiles.Count -eq 0) {
    Write-Error "No Java files found in $srcDir"
}

# Compile
javac -d $binDir -cp $classPath $javaFiles

# 3. Package JAR
Write-Host "Packaging JAR..."
New-Item -ItemType Directory -Force -Path $stagingDir | Out-Null
New-Item -ItemType Directory -Force -Path "$stagingDir/lib" | Out-Null

# Copy libs to staging
Copy-Item "$libDir/*.jar" "$stagingDir/lib/"

# Create Manifest
$manifestPath = "$stagingDir/MANIFEST.MF"
$manifestContent = "Manifest-Version: 1.0`r`nMain-Class: $mainClass`r`nClass-Path: lib/slf4j-api.jar lib/slf4j-simple.jar lib/sqlite-jdbc.jar`r`n"
Set-Content -Path $manifestPath -Value $manifestContent -NoNewline

# Create JAR
$jarPath = "$stagingDir/$appName.jar"
jar cfm $jarPath $manifestPath -C $binDir .

# 4. Bundle with jpackage
Write-Host "Bundling with jpackage..."

# Note: --win-console needs to be used to see output, user did not specify but implied checking output.
# Actually user said "muestre la iformacion", likely GUI or web, but console is good for server logs.
# "ya me encargo yo de la parte de iniciar ngrok" implies server.
# Using --win-console to ensure server logs are visible if needed, or user can assume it runs in background.
# Given "create executable for interface... show info", usually a server runs in background.
# But for debugging "Servidor iniciado en puerto 8080" is useful. I will enable console.

jpackage `
  --name $appName `
  --input $stagingDir `
  --main-jar "$appName.jar" `
  --main-class $mainClass `
  --dest $distDir `
  --type app-image `
  --win-console

# 5. Finalize (Copy Assets and DB)
Write-Host "Copying assets and database..."
$appDistDir = "$distDir/$appName"

Copy-Item -Recurse -Force $assetsDir "$appDistDir/assets"
Copy-Item -Force $dbFile "$appDistDir/$dbFile"

Write-Host "Build Complete!"
Write-Host "Executable is located at: $appDistDir/$appName.exe"
