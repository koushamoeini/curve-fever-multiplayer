# Run Curve Fever Client
$ErrorActionPreference = "Stop"

Set-Location $PSScriptRoot

Write-Host "Starting Curve Fever Client..." -ForegroundColor Green

# Find JavaFX jars in Gradle cache
$javafxBase = Get-ChildItem "$env:USERPROFILE\.gradle\caches\modules-2\files-2.1\org.openjfx\javafx-base\21.0.2" -Recurse -Filter "javafx-base-21.0.2-win.jar" | Select-Object -First 1
$javafxControls = Get-ChildItem "$env:USERPROFILE\.gradle\caches\modules-2\files-2.1\org.openjfx\javafx-controls\21.0.2" -Recurse -Filter "javafx-controls-21.0.2-win.jar" | Select-Object -First 1
$javafxFxml = Get-ChildItem "$env:USERPROFILE\.gradle\caches\modules-2\files-2.1\org.openjfx\javafx-fxml\21.0.2" -Recurse -Filter "javafx-fxml-21.0.2-win.jar" | Select-Object -First 1
$javafxGraphics = Get-ChildItem "$env:USERPROFILE\.gradle\caches\modules-2\files-2.1\org.openjfx\javafx-graphics\21.0.2" -Recurse -Filter "javafx-graphics-21.0.2-win.jar" | Select-Object -First 1

if (-not $javafxBase -or -not $javafxControls -or -not $javafxFxml -or -not $javafxGraphics) {
    Write-Host "JavaFX libraries not found. Running gradle to download them..." -ForegroundColor Yellow
    cmd /c "gradlew.bat build"
}

# Build module path
$modulePath = "build\classes\java\main"
if ($javafxBase) { $modulePath += ";$($javafxBase.DirectoryName)" }
if ($javafxControls) { $modulePath += ";$($javafxControls.DirectoryName)" }
if ($javafxFxml) { $modulePath += ";$($javafxFxml.DirectoryName)" }
if ($javafxGraphics) { $modulePath += ";$($javafxGraphics.DirectoryName)" }

Write-Host "Module path: $modulePath" -ForegroundColor Cyan

# Run the application
& java --module-path $modulePath --add-modules javafx.controls,javafx.fxml -m com.example.clientap6/com.example.clientap6.Curve
