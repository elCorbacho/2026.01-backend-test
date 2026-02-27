## Context

The frontend currently renders content without a dedicated footer block, which means contact details are buried in documentation or external channels. The application uses a shared layout component (e.g., `AppLayout` or `MainLayout`) that wraps every route. Any footer needs to respect the existing CSS variables, spacing conventions, and the responsive grid encoded by `app.scss`.

## Goals / Non-Goals

**Goals:**
- Introduce a persistent footer across the main layout that surfaces the developer name and email.
- Keep the footer visually consistent with the current design system (colors, typography, spacing).
- Ensure the footer is accessible (focusable link, semantic markup) and works on mobile.

**Non-Goals:**
- Redesign the overall page layout or introduce new UI patterns beyond the footer block.
- Add backend contact logic or dynamic user-specific contact information.

## Decisions
- **Location**: Embed the footer inside the shared layout component so all existing pages inherit it automatically instead of modifying each view separately. This keeps duplication low and ensures consistent positioning.
- **Markup**: Use a `<footer>` element containing a `<p>` for the name and an `<a>` with `href="mailto:histopat.cor@gmail.com"` for the email. Keep text semantics simple to avoid extra behavior.
- **Styling**: Create a dedicated CSS class (e.g., `.footer-contact-info`) in the layout stylesheet that uses current `--color-neutral` variables, ensures adequate padding, and wraps flexibly. Adjust the mobile media query to stack the text vertically with a small gap.
- **Responsive behavior**: At widths <= 640px apply `flex-direction: column` and `gap: 8px`; for larger screens use `flex-direction: row` with `justify-content: center` and `gap: 16px`.

## Risks / Trade-offs
- [Risk] Footer content overlapping existing sticky elements → Mitigation: place the footer after the main content container and add `margin-top: auto` where needed to push it down when content is short.
- [Risk] Link styling may conflict with global link colors → Mitigation: use a scoped class that resets `color` to the neutral primary variable and set `text-decoration: underline` only on hover.

## Migration Plan

1. Update the shared layout component to include the new `<footer>` block.
2. Add the corresponding CSS to the main stylesheet and verify responsive behavior.
3. Review a handful of pages manually to ensure the footer renders and the layout spacing stays intact.

## Open Questions
- None at the moment.
