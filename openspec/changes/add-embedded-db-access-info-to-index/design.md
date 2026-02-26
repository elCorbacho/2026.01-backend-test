# Design: Add Embedded DB Access Links and Credentials to Index Single-Page

## Technical Approach

Integrate a dedicated embedded-DB access block directly into `src/main/resources/static/index.html` using existing one-page UI patterns.  
The block will expose local H2 access details from current local configuration (`application.properties`) and provide direct link(s) to `/h2-console`, while explicitly avoiding production credentials.

## Architecture Decisions

### Decision: Keep content static and local-config aligned

**Choice**: Render embedded DB access values as static UI copy aligned with the local H2 setup currently configured in `application.properties`.  
**Alternatives considered**:
- Dynamic injection of properties via backend endpoint.
- Runtime template rendering with environment-dependent values.  
**Rationale**: Current site is static single-page documentation; static aligned values are simplest, fastest, and consistent with current architecture.

### Decision: Place access info inside existing one-page flow

**Choice**: Add the embedded DB block under existing one-page sections (`setup` and/or `recursos`) instead of a standalone page.  
**Alternatives considered**:
- Create a new independent HTML page.
- Add only footer link without credentials block.  
**Rationale**: User requested one single-page format with links + credentials visible in context.

### Decision: Reuse index design primitives

**Choice**: Use existing classes (`card-base`, `feature-list`, `code-wrap`, `section-*`) and current dark/light support.  
**Alternatives considered**:
- Introduce separate CSS classes/theme for database credentials UI.  
**Rationale**: Prevent visual drift and keep the same UX language already used across `index.html`.

## Data Flow

```text
User opens /
   |
   v
Index renders embedded DB block (static local values)
   |
   +--> User clicks /h2-console link
   |       |
   |       v
   |    H2 Console opens
   |
   +--> User reads credentials/JDBC URL and logs in
```

No backend API/data flow changes are introduced.

## File Changes

| File | Action | Description |
|------|--------|-------------|
| `src/main/resources/static/index.html` | Modify | Add embedded DB access block with links and local login/connection data; keep one-page style. |
| `src/main/resources/application.properties` | Reference only | Source of truth for local H2 settings shown in UI. |

## Interfaces / Contracts

No API contract changes.

Front-end informational contract to add in `index.html`:
- Console URL: `/h2-console`
- JDBC URL (local): `jdbc:h2:mem:web2examen;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
- User: `sa`
- Password: *(vacía)*  

The UI MUST NOT include MySQL/RDS production credentials.

## Testing Strategy

| Layer | What to Test | Approach |
|-------|-------------|----------|
| Manual UI | Embedded DB block visible in one-page flow | Open `/` and verify presence in section layout. |
| Manual UI | H2 link correctness | Click `/h2-console` and verify page opens. |
| Manual UI | Credential accuracy | Compare displayed values against `application.properties` local H2 entries. |
| Manual UI | Security check | Verify no production credentials shown in UI text/code blocks. |
| Manual UI | Visual consistency | Validate cards/spacing/typography in light and dark theme. |

## Migration / Rollout

No migration required.  
Rollout is a static content update in `index.html`.

## Open Questions

- [ ] Confirm preferred placement: inside `#setup`, inside `#recursos`, or both (summary + quick link).
- [ ] Confirm if password should be shown explicitly as “(vacía)” or as masked placeholder for clarity.

