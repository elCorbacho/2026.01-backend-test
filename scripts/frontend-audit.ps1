param(
  [string]$StaticPath = 'src/main/resources/static'
)

$ErrorActionPreference = 'Stop'

if (-not (Test-Path $StaticPath)) {
  throw "Path not found: $StaticPath"
}

$htmlFiles = Get-ChildItem $StaticPath -Filter *.html | Sort-Object Name
$cssFiles = Get-ChildItem $StaticPath -Filter *.css -File
$jsFiles  = Get-ChildItem $StaticPath -Filter *.js -File

$invalidNames = Get-ChildItem $StaticPath -File | Where-Object {
  $_.Name -match '_' -and $_.Extension -in '.html','.css','.js'
}

$rows = foreach ($f in $htmlFiles) {
  $content = Get-Content $f.FullName -Raw
  $links = [regex]::Matches($content, '(?is)<link[^>]*href=["''][^"'']+["''][^>]*>')
  $scripts = [regex]::Matches($content, '(?is)<script[^>]*src=["''][^"'']+["''][^>]*>')

  $localCss = @()
  foreach ($m in $links) {
    if ($m.Value -match '(?is)rel=["'']stylesheet["'']' -and $m.Value -match 'href=["'']([^"'']+)["'']') {
      $href = $matches[1]
      if ($href -notmatch '^(https?:)?//') { $localCss += $href }
    }
  }

  $localJs = @()
  foreach ($m in $scripts) {
    if ($m.Value -match 'src=["'']([^"'']+)["'']') {
      $src = $matches[1]
      if ($src -notmatch '^(https?:)?//') { $localJs += $src }
    }
  }

  $hasRelative = ($localCss + $localJs | Where-Object { $_ -notmatch '^/' }).Count -gt 0
  $hasAbsolute = ($localCss + $localJs | Where-Object { $_ -match '^/' }).Count -gt 0

  [PSCustomObject]@{
    File = $f.Name
    InlineStyleTags = ([regex]::Matches($content,'(?is)<style\b')).Count
    InlineScriptTags = ([regex]::Matches($content,'(?is)<script(?![^>]*\bsrc=)')).Count
    LocalCssRefs = ($localCss -join ', ')
    LocalJsRefs  = ($localJs -join ', ')
    MixedLocalRouteStyle = ($hasRelative -and $hasAbsolute)
  }
}

$localCssOutside = @($cssFiles | ForEach-Object { $_.FullName.Replace((Resolve-Path '.').Path + '\\','') } | Where-Object { $_ -notmatch '^src\\main\\resources\\static\\css\\' })
$localJsOutside  = @($jsFiles  | ForEach-Object { $_.FullName.Replace((Resolve-Path '.').Path + '\\','') } | Where-Object { $_ -notmatch '^src\\main\\resources\\static\\js\\' })

Write-Output '# Frontend Audit Report'
Write-Output ''
Write-Output "- Audited at: $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss')"
Write-Output "- Static path: $StaticPath"
Write-Output "- HTML files: $($htmlFiles.Count)"
Write-Output ''
Write-Output '## Invalid file names (underscore in html/css/js)'
if($invalidNames.Count -eq 0){ Write-Output '- None' } else { $invalidNames | ForEach-Object { Write-Output ('- ' + $_.Name) } }
Write-Output ''
Write-Output '## Local CSS/JS outside standard folders'
if($localCssOutside.Count -eq 0){ Write-Output '- CSS outside /css: None' } else { Write-Output '- CSS outside /css:'; $localCssOutside | ForEach-Object { Write-Output ('  - ' + $_) } }
if($localJsOutside.Count -eq 0){ Write-Output '- JS outside /js: None' } else { Write-Output '- JS outside /js:'; $localJsOutside | ForEach-Object { Write-Output ('  - ' + $_) } }
Write-Output ''
Write-Output '## Per-file inline and local route analysis'
$rows | Format-Table -AutoSize | Out-String | Write-Output
Write-Output '## Files with mixed local route style (relative + absolute)'
$mixed = $rows | Where-Object { $_.MixedLocalRouteStyle }
if($mixed.Count -eq 0){ Write-Output '- None' } else { $mixed | ForEach-Object { Write-Output ('- ' + $_.File) } }
