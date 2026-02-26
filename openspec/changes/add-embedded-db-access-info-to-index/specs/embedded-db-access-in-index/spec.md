# Embedded DB Access in Index Specification

## Purpose

Define the expected behavior for showing embedded database access links and local login data inside `index.html` as part of the one single-page experience.

## Requirements

### Requirement: Embedded DB access section in one-page index

The system MUST show embedded DB access information directly in `index.html` without requiring a separate standalone page.

#### Scenario: User finds embedded DB info in index

- GIVEN the user opens `/`
- WHEN the user navigates through the single-page content
- THEN the page SHALL include a visible block/section with embedded DB access information

### Requirement: Show H2 Console access link

The system MUST provide a direct link to the embedded database console endpoint used in local development.

#### Scenario: H2 Console link is present

- GIVEN the embedded DB info block is rendered
- WHEN the user reviews available access actions
- THEN the page SHALL include a link to `/h2-console`

### Requirement: Show local development credentials and connection data

The system MUST display the local embedded DB login data needed to access the console, and MUST use only local development values.

#### Scenario: Required local data is displayed

- GIVEN the user opens the embedded DB info block
- WHEN the user checks connection details
- THEN the page SHALL show local credentials/connection values (user, password, JDBC URL)
- AND the displayed data SHALL correspond to the local embedded DB setup

### Requirement: Do not expose production secrets

The system MUST NOT expose production database credentials in the single-page UI.

#### Scenario: Production secrets are absent

- GIVEN the user reviews the embedded DB information
- WHEN scanning displayed credentials and examples
- THEN only local embedded DB values SHALL be shown
- AND production credentials SHALL NOT appear

### Requirement: Keep visual consistency with current index design

The embedded DB block SHOULD follow the same visual language already used by `index.html`.

#### Scenario: Style consistency

- GIVEN the embedded DB block is rendered alongside existing sections
- WHEN comparing layout and components
- THEN the block SHOULD reuse current index UI patterns (cards, spacing, typography, code blocks)

