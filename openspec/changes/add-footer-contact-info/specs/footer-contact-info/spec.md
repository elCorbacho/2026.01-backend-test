## ADDED Requirements

### Requirement: Footer contact info is visible on every public page
El frontend SHALL render a footer strip at the bottom of each view that includes the text "Developer Andrés Corbacho" and the email `histopat.cor@gmail.com`.

#### Scenario: Desktop page rendering footer
- **WHEN** a user opens any desktop page under the main layout
- **THEN** the footer appears at the bottom of the viewport with the contact text and the email shown in a single line

### Requirement: Email link behaves correctly
The email address SHALL be wrapped in an `<a href="mailto:histopat.cor@gmail.com">` and styled according to the existing link palette.

#### Scenario: Email link interaction
- **WHEN** a user clicks the email address in the footer
- **THEN** the system opens the operating system’s default mail client pre-populated with `histopat.cor@gmail.com` as the recipient

### Requirement: Footer is responsive
On mobile layouts (<= 640px width), the footer SHALL stack the name and the email vertically with sufficient padding and maintain legible font sizes.

#### Scenario: Mobile render adapts layout
- **WHEN** a user views the page on a screen width of 640px or less
- **THEN** the footer displays the developer name above the email with a vertical gap of at least 8px and readable font size per the current design system
