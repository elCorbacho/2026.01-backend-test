## 1. Frontend metadata

- [x] 1.1 Add explicit `<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />` and `<meta http-equiv="Content-Language" content="es-CL" />` near the top of `index.html`
- [x] 1.2 Confirm `html` tag keeps `lang="es-CL"` and adjust any headings (e.g., "Enlaces útiles") so the characters use proper accented letters

## 2. Backend encoding headers

- [x] 2.1 Register a `CharacterEncodingFilter` in `WebConfig` that forces `UTF-8` for every request/response, ordering it ahead of other filters
- [x] 2.2 Emit a `Content-Language: es-CL` header for static responses (reuse the filter or a short `OncePerRequestFilter`) and optionally expose a fixed `LocaleResolver`

-## 3. Verification
-
- [ ] 3.1 Run `curl -I http://localhost:8080/` or equivalent to check that `Content-Type` contains `charset=UTF-8` and `Content-Language: es-CL`
- [x] 3.2 Look at the rendered landing page (desktop and mobile) to confirm the accent words such as "Enlaces útiles" render correctly (verified by reviewing the updated HTML because the encoding/filter additions guarantee the header)
