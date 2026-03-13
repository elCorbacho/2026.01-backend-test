# Fix Instructions for Code Review Issues

## High Priority

### 1. Fix Column Name Mismatch in Album.java
**File:** `src/main/java/ipss/web2/examen/models/Album.java`
**Line:** 38-39

**Current:**
```java
@Column(name = "release_year", nullable = false)
private Integer year;
```

**Fix:** Rename field to match column convention or add explicit column mapping:
```java
@Column(name = "release_year", nullable = false)
private Integer releaseYear;
```
Then update all references in:
- `AlbumMapper.java`
- `AlbumRequestDTO.java` (if year is used)
- Any service or test files referencing this field

---

## Medium Priority

### 2. Fix Boolean Initialization in Album.java
**File:** `src/main/java/ipss/web2/examen/models/Album.java`
**Lines:** 52-54

**Current:**
```java
@Builder.Default
@Column(name = "is_active")
private Boolean active = true;
```

**Fix:** Change to primitive boolean OR ensure initialization in all constructors:
```java
@Builder.Default
@Column(name = "is_active")
private boolean active = true;
```

### 3. Fix ToString in GanadorAlbum.java
**File:** `src/main/java/ipss/web2/examen/models/GanadorAlbum.java`
**Line:** 19

**Current:**
```java
@ToString
```

**Fix:** Exclude bidirectional relationships:
```java
@ToString(exclude = {"album"})
```

### 4. Remove Class-Level @SuppressWarnings("null")
**File:** `src/main/java/ipss/web2/examen/services/AlbumService.java`
**Line:** 25

**Current:**
```java
@SuppressWarnings("null")
@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {
```

**Fix:** Remove class-level suppression and add only to specific methods that need it:
```java
@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {
    
    // Add @SuppressWarnings("null") only to specific method if truly needed
    public AlbumResponseDTO crearAlbum(AlbumRequestDTO requestDTO) {
        // ...
    }
```

Apply the same pattern to other services with class-level suppressions.

### 5. Add @JsonIgnore to Bidirectional Relationships
**File:** `src/main/java/ipss/web2/examen/models/Album.java`
**Lines:** 57-62

**Current:**
```java
@Builder.Default
@OneToMany(mappedBy = "album", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
private List<Lamina> laminas = new ArrayList<>();

@Builder.Default
@OneToMany(mappedBy = "album", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
private List<LaminaCatalogo> laminasCatalogo = new ArrayList<>();
```

**Fix:** Add `@JsonIgnore`:
```java
@Builder.Default
@JsonIgnore
@OneToMany(mappedBy = "album", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
private List<Lamina> laminas = new ArrayList<>();

@Builder.Default
@JsonIgnore
@OneToMany(mappedBy = "album", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
private List<LaminaCatalogo> laminasCatalogo = new ArrayList<>();
```

Apply the same to other entities with bidirectional relationships.

### 6. Add Database Indexes for Foreign Keys
**File:** `src/main/java/ipss/web2/examen/models/Lamina.java`

**Current:**
```java
@Table(name = "lamina")
```

**Fix:** Add index on foreign key column:
```java
@Table(name = "lamina", indexes = {
    @Index(name = "idx_lamina_album_id", columnList = "album_id")
})
```

Apply to other entities with foreign keys that are frequently queried.

---

## Low Priority

### 7. Fix Controller Indentation
**File:** `src/main/java/ipss/web2/examen/controllers/api/AlbumController.java`
**Lines:** 44-55

**Current:**
```java
@GetMapping("/{id}")
    @Operation(summary = "Obtener album por id")
    @ApiResponses({
```

**Fix:** Align annotations properly:
```java
@GetMapping("/{id}")
@Operation(summary = "Obtener album por id")
@ApiResponses({
```

### 8. Add Missing Controller Tests
Add tests for PUT and DELETE endpoints in `AlbumControllerWebMvcTest.java`:

```java
@Test
@DisplayName("PUT /api/albums/{id} debe actualizar album exitosamente")
void actualizarAlbumDebeResponderOk() throws Exception {
    when(albumService.actualizarAlbum(eq(1L), any(AlbumRequestDTO.class)))
            .thenReturn(AlbumResponseDTO.builder()
                    .id(1L)
                    .nombre("Album Actualizado")
                    .year(2025)
                    .descripcion("Descripcion actualizada")
                    .build());

    mockMvc.perform(put("/api/albums/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(Map.of(
                            "nombre", "Album Actualizado",
                            "year", 2025,
                            "descripcion", "Descripcion actualizada"
                    ))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.data.nombre").value("Album Actualizado"));
}

@Test
@DisplayName("DELETE /api/albums/{id} debe marcar como inactivo")
void eliminarAlbumDebeResponderOk() throws Exception {
    mockMvc.perform(delete("/api/albums/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success").value(true))
            .andExpect(jsonPath("$.message").value("Álbum con ID: 1 ha sido marcado como inactivo"));
}
```

---

## Optional Enhancements

### 9. Add @Version for Optimistic Locking
Add to entities that are frequently updated (e.g., `Album.java`):
```java
@Version
@Column(name = "version")
private Long version;
```

### 10. Consider Adding PATCH Support
For partial updates, create a `AlbumPatchRequestDTO` with `@JsonInclude(NON_NULL)` and update the controller:
```java
@PatchMapping("/{id}")
public ResponseEntity<ApiResponseDTO<AlbumResponseDTO>> actualizarAlbumParcial(
        @PathVariable Long id,
        @Valid @RequestBody AlbumPatchRequestDTO requestDTO) {
    AlbumResponseDTO response = albumService.actualizarAlbumParcial(id, requestDTO);
    return okResponse(response, "Álbum actualizado parcialmente");
}
```
