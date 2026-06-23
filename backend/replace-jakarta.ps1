$files = Get-ChildItem -Path "src\main\java" -Recurse -Filter "*.java"
foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $content = $content -replace 'jakarta\.', 'javax.'
    Set-Content -Path $file.FullName -Value $content -NoNewline
}
Write-Host "Replaced jakarta with javax in all Java files"
