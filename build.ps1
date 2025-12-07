# Simple build and test script for when Gradle wrapper has issues
# Use this if gradlew.bat doesn't work

Write-Host "IMG2ASCII Build and Test Script" -ForegroundColor Green
Write-Host "================================" -ForegroundColor Green
Write-Host ""

# Check if using IntelliJ
Write-Host "Recommended: Use IntelliJ IDEA's built-in Gradle support:" -ForegroundColor Yellow
Write-Host "  1. Right-click on build.gradle → 'Load Gradle Project'" -ForegroundColor Yellow
Write-Host "  2. Use the Gradle tool window on the right side" -ForegroundColor Yellow
Write-Host "  3. Navigate to: IMG2ASCII → Tasks → verification → test" -ForegroundColor Yellow
Write-Host "  4. Double-click 'test' to run all tests" -ForegroundColor Yellow
Write-Host ""
Write-Host "Or compile and test manually:" -ForegroundColor Cyan
Write-Host ""

# Create output directories
Write-Host "Creating output directories..." -ForegroundColor White
New-Item -ItemType Directory -Force -Path "build\classes\main" | Out-Null
New-Item -ItemType Directory -Force -Path "build\classes\test" | Out-Null

# Find JUnit JAR location from Maven repository
$junitJar = "$env:USERPROFILE\.m2\repository\org\junit\jupiter\junit-jupiter-api\5.9.3\junit-jupiter-api-5.9.3.jar"
$junitEngine = "$env:USERPROFILE\.m2\repository\org\junit\jupiter\junit-jupiter-engine\5.9.3\junit-jupiter-engine-5.9.3.jar"

if (-not (Test-Path $junitJar)) {
    Write-Host "JUnit JAR not found in Maven repository." -ForegroundColor Red
    Write-Host "Please use IntelliJ IDEA to sync Gradle, which will download dependencies." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "In IntelliJ:" -ForegroundColor Cyan
    Write-Host "  1. Open the Gradle tool window (View → Tool Windows → Gradle)" -ForegroundColor Cyan
    Write-Host "  2. Click the refresh icon to sync" -ForegroundColor Cyan
    Write-Host "  3. Then run tests from: IMG2ASCII → Tasks → verification → test" -ForegroundColor Cyan
    exit 1
}

Write-Host "Compiling source files..." -ForegroundColor White
javac -d build\classes\main -sourcepath src\main\java (Get-ChildItem -Path src\main\java -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)

if ($LASTEXITCODE -ne 0) {
    Write-Host "Compilation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "Compiling test files..." -ForegroundColor White  
javac -cp "build\classes\main;$junitJar" -d build\classes\test -sourcepath src\test\java (Get-ChildItem -Path src\test\java -Recurse -Filter *.java | Select-Object -ExpandProperty FullName)

if ($LASTEXITCODE -ne 0) {
    Write-Host "Test compilation failed!" -ForegroundColor Red
    exit 1
}

Write-Host "Build successful!" -ForegroundColor Green
Write-Host ""
Write-Host "To run the application:" -ForegroundColor Cyan
Write-Host "  java -cp build\classes\main AppRunner" -ForegroundColor White
Write-Host ""
Write-Host "To run tests, use IntelliJ IDEA's test runner or Gradle tool window." -ForegroundColor Yellow
