&nbsp; Prompt to Fix Request URL Disclosure Vulnerability



&nbsp; Fix the information disclosure vulnerability in GlobalExceptionHandler.java where request URLs and resource paths are

&nbsp; exposed in error response bodies.



&nbsp; VULNERABILITY DETAILS:

&nbsp; - File: src/main/java/ipss/web2/examen/exceptions/GlobalExceptionHandler.java

&nbsp; - Lines affected: 136, 141, 155, 160

&nbsp; - Issue: Request URLs containing query parameters (with potential API keys/tokens) are returned to clients in error

&nbsp; responses



&nbsp; REQUIRED CHANGES:



&nbsp; 1. In handleNoHandlerFound() method (lines 128-145):

&nbsp;    - REMOVE: errorDetails.put("ruta", ex.getRequestURL());

&nbsp;    - REMOVE: the URL concatenation from error message - change from "Endpoint inválido: " + ex.getHttpMethod() + " " +

&nbsp;  ex.getRequestURL() to just "El endpoint solicitado no existe"

&nbsp;    - KEEP: errorDetails.put("metodo", ex.getHttpMethod());

&nbsp;    - KEEP: Server-side logging with the URL (log.warn is safe)



&nbsp; 2. In handleNoResourceFoundException() method (lines 148-164):

&nbsp;    - REMOVE: errorDetails.put("ruta", ex.getResourcePath());

&nbsp;    - REMOVE: the path from error message - change from "Endpoint inválido: " + ex.getResourcePath() to "El recurso

&nbsp; solicitado no fue encontrado"

&nbsp;    - Keep the error details map, but don't include the actual path



&nbsp; 3. In handleEndpointNotFoundException() method (if it exists):

&nbsp;    - Check if ex.getPath() is being returned in errorDetails or message

&nbsp;    - If yes, remove it similar to above



&nbsp; TESTING:

&nbsp; After changes, test with:

&nbsp;   curl "http://localhost:8080/api/nonexistent?apiToken=secret123"



&nbsp; Verify the response does NOT contain the query parameter value or the full path.



&nbsp; ---

&nbsp; Or if you want to apply it directly, I can help you edit the file. Would you like me to:



&nbsp; 1. Show you the exact edits to make (line by line)

&nbsp; 2. Apply the fixes directly to the file for you

&nbsp; 3. Create a separate guide with code snippets

