# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Repository Overview

FlowGram is a composable, visual workflow development framework built as a Rush-managed monorepo. It provides tools for building AI workflow platforms, including a flow canvas, node configuration forms, variable scope chains, and pre-built materials (LLM, Condition, Code Editor, etc.).

## Build System & Package Management

This monorepo uses **Rush 5.150.0** with **pnpm 10.6.5** as the package manager. Node.js version must be >=18.20.3 <19.0.0 || >=20.14.0 <23.0.0.

### Essential Commands

```bash
# Install dependencies (required first step)
rush install

# Build all packages
rush build

# Build specific package and its dependencies
rush build --to @flowgram.ai/core

# Lint all packages
rush lint

# Fix lint issues
rush lint:fix

# TypeScript type checking
rush ts-check

# Run unit tests
rush test

# Run tests with coverage
rush test:cov

# Build packages in watch mode (for development)
rush build:watch

# E2E tests with Playwright
rush e2e:test

# Update E2E screenshots
rush e2e:update-screenshot
```

### Development Commands for Demos

```bash
# Run docs site with hot reload
rush dev:docs

# Run specific demo apps with hot reload
rush dev:demo-free-layout
rush dev:demo-fixed-layout
rush dev:demo-fixed-layout-simple
rush dev:demo-free-layout-simple
rush dev:demo-nextjs
rush dev:demo-nextjs-antd
```

These commands use `concurrently` to run `rush build:watch` for dependencies alongside the demo's dev server.

### Single Package Development

To work on a single package in isolation:
```bash
cd packages/canvas-engine/core
rushx build        # Runs the build script for this package only
rushx test         # Runs tests for this package only
rushx ts-check     # Type checks this package only
```

## Monorepo Structure

### Core Organization

- **`packages/`** - Production libraries organized by functional area:
  - `canvas-engine/` - Canvas rendering and layout systems (core, document, renderer, fixed-layout-core, free-layout-core)
  - `node-engine/` - Node data and form management (node, form, form-core)
  - `variable-engine/` - Variable scoping and type inference (variable-core, variable-layout, json-schema)
  - `runtime/` - Workflow execution engines (interface, js-core, nodejs)
  - `plugins/` - Extensibility modules (23+ plugins for features like history, drag, snap, minimap, etc.)
  - `client/` - High-level React components (editor, fixed-layout-editor, free-layout-editor, playground-react)
  - `materials/` - Pre-built node materials (form-materials, form-antd-materials, fixed-semi-materials, coze-editor, type-editor)
  - `common/` - Shared utilities (utils, reactive, command, history, history-storage, i18n)

- **`apps/`** - Demos and documentation:
  - `docs/` - Main documentation site
  - `demo-*/` - Example applications (free-layout, fixed-layout, nextjs, vite, playground, etc.)
  - `create-app/`, `cli/` - CLI tools for scaffolding

- **`e2e/`** - End-to-end test suites (fixed-layout, free-layout)
- **`config/`** - Shared configuration (eslint-config, ts-config)
- **`common/`** - Rush tooling and scripts

### Architectural Layers

FlowGram is architected in distinct layers:

1. **Canvas Engine Layer** (`@flowgram.ai/core`, `@flowgram.ai/document`, `@flowgram.ai/renderer`)
   - Core abstractions for canvas rendering, document model, and viewport management
   - Supports two layout modes: free-layout (drag-anywhere) and fixed-layout (structured positioning)
   - Plugin-based architecture using dependency injection (inversify)

2. **Node Engine Layer** (`@flowgram.ai/node`, `@flowgram.ai/form`, `@flowgram.ai/form-core`)
   - Manages node data structures and lifecycle
   - Form engine with validation, side effects, linkage, and error capture
   - Uses `FormModelV2` (exported as `FormModel` from `@flowgram.ai/editor`)

3. **Variable Engine Layer** (`@flowgram.ai/variable-core`, `@flowgram.ai/json-schema`)
   - Provides variable scoping, structure inspection, and type inference
   - Manages data flow constraints across workflow nodes
   - Scope chain mechanism for variable resolution

4. **Runtime Layer** (`@flowgram.ai/runtime-js`, `@flowgram.ai/runtime-nodejs`, `@flowgram.ai/runtime-interface`)
   - Executes workflows in JavaScript/Node.js environments
   - Interface package defines runtime contracts
   - Separate implementations for browser and server

5. **Client/Editor Layer** (`@flowgram.ai/editor`, `@flowgram.ai/fixed-layout-editor`, `@flowgram.ai/free-layout-editor`)
   - High-level React components that integrate all subsystems
   - `@flowgram.ai/editor` is the main barrel export for fixed-layout workflows
   - Re-exports from core, form, variable, and plugin packages

6. **Plugin Ecosystem**
   - 20+ plugins providing features like drag-and-drop, history/undo, snap-to-grid, minimap, auto-layout, etc.
   - Plugins are registered via dependency injection containers
   - Naming convention: `free-*-plugin` for free-layout, `fixed-*-plugin` for fixed-layout, or generic plugins

## Key Design Patterns

### Dependency Injection
The codebase heavily uses **inversify** for dependency injection. Services are decorated with `@injectable()` and injected via `@inject()`. Container modules organize related services.

### Reactive State Management
Uses a custom reactive system (`@flowgram.ai/reactive`) with React hooks:
- `ReactiveState` and `ReactiveBaseState` for observable state
- `useReactiveState`, `useReadonlyReactiveState`, `useObserve` hooks
- `Tracker` for dependency tracking

### Command Pattern
`@flowgram.ai/command` provides a command/command registry system for undo/redo operations. Re-exported by `@flowgram.ai/core`.

### Plugin Architecture
Plugins extend functionality via:
- `Plugin` interface from `@flowgram.ai/core`
- Registration through container modules
- Lifecycle hooks (`onInit`, `onDestroy`, etc.)

## Testing

- **Unit tests**: Use Vitest, located in `__tests__/` folders or `*.test.ts` files
- **E2E tests**: Use Playwright, located in `e2e/*/tests/` directories
- Run all tests: `rush test`
- Run E2E tests for specific package: `rush e2e:test --to @flowgram.ai/e2e-free-layout`
- Update Playwright snapshots: `rush e2e:update-screenshot`

## Code Quality

### Linting & Type Checking
- ESLint configuration in `config/eslint-config` enforces 2-space indentation, semicolons, and import order
- TypeScript config in `config/ts-config`
- Always run `rush lint:fix` before committing
- Run `rush ts-check` to validate TypeScript across all packages

### Naming Conventions
- **React components/classes**: PascalCase
- **Variables/functions**: camelCase
- **Constants**: SCREAMING_SNAKE_CASE (only for exported config)
- **File names**: kebab-case (e.g., `flow-node-form.tsx`)

### Pre-commit Hooks
- `rush lint-staged` - Runs linting on staged files
- `rush commitlint` - Validates commit message format (conventional commits)

### Dependency Checks
- `rush check-circular-dependency` - Detects circular dependencies
- `rush dep-check` - Validates dependency consistency

## Commit & Pull Request Standards

Follow **conventional commits** format: `type(scope): subject`

Examples from recent history:
- `fix(auto-layout): rankdir top to bottom`
- `feat(landing): hover logo node glowing`
- `docs(variable): optimize variable docs by codex`

Keep commit subjects imperative, ≤72 characters. Reference GitHub issues in PR descriptions.

## Working with Packages

### Adding a New Package
1. Create folder under appropriate category (`packages/<category>/<package-name>`)
2. Add entry to `rush.json` "projects" array with:
   - `packageName`: `@flowgram.ai/<package-name>`
   - `projectFolder`: relative path
   - `versionPolicyName`: typically "publishPolicy" for libraries, "appPolicy" for apps
   - `tags`: for categorization
3. Run `rush update` to link the package

### Publishing Workflow
Packages with `versionPolicyName: "publishPolicy"` are publishable to npm. Apps use `"appPolicy"`.

### Inter-package Dependencies
Use `workspace:^x.x.x` protocol in package.json for internal dependencies. Rush will link them locally during development.

## Common Issues

### Build Failures
- Ensure `rush install` was run after pulling changes
- Check Node.js version matches `nodeSupportedVersionRange` in rush.json
- Clear incremental build cache: delete `common/temp` and rebuild

### Type Errors in Editor Packages
The main editor packages (`@flowgram.ai/editor`, `@flowgram.ai/fixed-layout-editor`, `@flowgram.ai/free-layout-editor`) re-export many types. Look for type definitions in their upstream dependencies (@flowgram.ai/form, @flowgram.ai/core, @flowgram.ai/node).

### Plugin Registration
Plugins must be registered in a dependency injection container. Check demo apps for examples of container module setup.

### Rush Command Not Found
Ensure Rush is installed globally: `npm install -g @microsoft/rush`
Or use the install-run script: `node common/scripts/install-run-rush.js <command>`

## Additional Resources

- Documentation: https://flowgram.ai
- Issues: https://github.com/bytedance/flowgram.ai/issues
- Contributing: See CONTRIBUTING.md
