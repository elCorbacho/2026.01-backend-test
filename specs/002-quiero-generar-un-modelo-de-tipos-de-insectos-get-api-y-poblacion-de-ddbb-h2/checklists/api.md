# API Requirements Quality Checklist: Catalogo de tipos de insectos

**Purpose**: Validar la calidad de redaccion de requisitos de API y errores antes de implementacion/revision de PR
**Created**: 2026-03-11
**Feature**: [spec.md](../spec.md)

## Requirement Completeness

- [ ] CHK001 Are request and response requirements defined for both consulta general and consulta por identificador flows? [Completeness, Spec §User Story 1, Spec §User Story 2, Spec §FR-002, Spec §FR-003]
- [ ] CHK002 Are error requirements defined for all identified failure modes (identificador invalido, inexistente, inactivo)? [Completeness, Spec §FR-004, Spec §FR-008, Spec §Edge Cases]
- [ ] CHK003 Are response envelope requirements fully specified for both success and error outcomes, including required fields? [Completeness, Spec §FR-005, Plan §Constitution Check III]

## Requirement Clarity

- [ ] CHK004 Is "formato consistente para exito y error" described with explicit, verifiable structure and field expectations? [Clarity, Spec §FR-005, Ambiguity]
- [ ] CHK005 Is "identificador invalido" precisely defined (range, type, and boundary conditions) to avoid interpretation gaps? [Clarity, Spec §FR-008, Spec §Edge Cases]
- [ ] CHK006 Is "mensaje entendible" translated into objective wording standards for API error responses? [Clarity, Spec §SC-003, Ambiguity]

## Requirement Consistency

- [ ] CHK007 Are not-found requirements consistent between user scenarios, functional requirements, and success criteria? [Consistency, Spec §User Story 2, Spec §FR-004, Spec §SC-003]
- [ ] CHK008 Do API read-only scope statements align with all listed scenarios and assumptions without introducing write-operation expectations? [Consistency, Spec §Assumptions, Spec §User Scenarios]

## Acceptance Criteria Quality

- [ ] CHK009 Are API success criteria measurable with explicit thresholds and observable outcome definitions? [Measurability, Spec §SC-001, Spec §SC-002, Spec §SC-003]
- [ ] CHK010 Are acceptance scenarios mapped clearly to each functional requirement relevant to API behavior? [Traceability, Spec §Acceptance Scenarios, Spec §FR-002, Spec §FR-003, Spec §FR-004, Spec §FR-008]

## Scenario Coverage

- [ ] CHK011 Are alternate scenarios documented for empty dataset responses and still aligned with expected business value? [Coverage, Spec §User Story 1 Scenario 2]
- [ ] CHK012 Are exception-flow requirements documented for malformed path input and out-of-range identifier requests? [Coverage, Spec §Edge Cases, Spec §FR-008]
- [ ] CHK013 Are recovery expectations defined when initial seed data is partially available or inconsistent at startup? [Recovery, Gap, Spec §FR-006, Spec §FR-007]

## Edge Case Coverage

- [ ] CHK014 Are requirements explicit about behavior when all records are inactive and clients still call list endpoint? [Edge Case, Spec §Edge Cases, Spec §FR-002]
- [ ] CHK015 Are duplicate-seed prevention rules detailed enough to determine how existing data is identified and preserved? [Edge Case, Spec §FR-007, Ambiguity]

## Non-Functional Requirements

- [ ] CHK016 Are performance requirements for API reads defined with scope conditions (normal load context, dataset size assumptions)? [Non-Functional, Spec §SC-001, Assumption]
- [ ] CHK017 Are API reliability expectations specified for startup timing dependency between seed completion and first read request? [Non-Functional, Spec §SC-004, Gap]

## Dependencies & Assumptions

- [ ] CHK018 Are dependencies on in-memory environment assumptions clearly bounded so requirement intent remains valid across environments? [Dependencies, Spec §FR-006, Spec §Assumptions, Plan §Technical Context]

## Ambiguities & Conflicts

- [ ] CHK019 Does the spec define whether list responses require deterministic ordering to keep consumer expectations stable? [Ambiguity, Gap, Spec §FR-002]
- [ ] CHK020 Is there any conflict between "coleccion vacia" success behavior and potential expectation of seed availability at startup? [Conflict, Spec §User Story 1 Scenario 2, Spec §User Story 3]

## Notes

- Use this checklist as PR review gate for requirements quality, not implementation behavior.
- Mark completed items with `[x]` and capture findings inline.
