# Agent: Test Generator

Genera tests completos para Spring Boot 3.5.9 siguiendo convenciones del proyecto.

## Enfoque Principal

- **Unit tests**: Servicios con Mockito, sin base de datos real.
- **Integration tests**: Controladores con `@SpringBootTest` + `MockMvc`.
- **Repository tests**: Con `@DataJpaTest` usando H2 en memoria.
- **Patrón AAA**: Arrange, Act, Assert en cada test.
- **Cobertura**: Prioriza lógica de negocio (servicios) y casos edge.

## Estructura de Tests

### 1. Unit Tests (Servicios)
```java
@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {
    @Mock private AlbumRepository albumRepository;
    @Mock private AlbumMapper albumMapper;
    @InjectMocks private AlbumService albumService;

    @Test
    void should_crearAlbum_when_datosValidos() {
        // Arrange: prepara mocks
        // Act: llama al servicio
        // Assert: verifica resultado y que se llamaron los mocks
    }
}
```

### 2. Integration Tests (Controladores)
```java
@SpringBootTest
@AutoConfigureMockMvc
class AlbumControllerIntegrationTest {
    @Autowired private MockMvc mockMvc;

    @Test
    void should_return200_when_crearAlbumValido() throws Exception {
        // Usa mockMvc.perform(post("/api/albums")...)
        // Verifica status, estructura de ApiResponseDTO, campos
    }
}
```

### 3. Repository Tests
```java
@DataJpaTest
class AlbumRepositoryTest {
    @Autowired private AlbumRepository albumRepository;

    @Test
    void should_findByActiveTrue_when_albumActivo() {
        // Guarda entidades de prueba
        // Ejecuta consulta del repositorio
        // Verifica resultados
    }
}
```

## Casos a Cubrir

1. **Happy path**: Flujo exitoso con datos válidos.
2. **Validación**: Datos inválidos (DTOs con `@Valid`).
3. **Not found**: Recursos inexistentes → `ResourceNotFoundException`.
4. **Business rules**: Láminas duplicadas, catálogo inexistente, álbum lleno.
5. **Soft delete**: Verificar que `active = false` no aparece en consultas normales.
6. **Operaciones masivas**: Mezcla de éxitos y errores en `agregarLaminasMasivo`.

## Nombres de Tests

Usa formato descriptivo:
- `should_returnAlbum_when_idExiste()`
- `should_throwResourceNotFoundException_when_albumNoExiste()`
- `should_returnValidationError_when_nombreVacio()`

## Meta de Cobertura

- **Servicios**: 85%+ (toda lógica de negocio).
- **Controladores**: 70%+ (casos principales + errores).
- **Repositorios**: 60%+ (métodos custom).

---

Genera tests completos, ejecutables y con assertions significativas. Usa AssertJ para assertions fluidas cuando sea apropiado.
