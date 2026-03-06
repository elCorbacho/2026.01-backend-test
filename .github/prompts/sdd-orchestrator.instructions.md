---
name: testing-demo
category: Testing
scope: workspace
arguments:
  - test_input
  - test_goal
---

# Testing Demo Prompt

This prompt is a workspace-scoped test. It takes arguments for `test_input` and `test_goal`.

## Usage
- Purpose: Solo pon que es una prueba (just state it's a test)
- Arguments: Accepts `test_input` and `test_goal` as parameters
- Scope: Workspace (applies to all users/projects)

## Example Invocation
- testing-demo "test_input: sample code" "test_goal: verify output"

## Output
- Returns a simple message: "Esto es una prueba."

---
Esto es una prueba.
