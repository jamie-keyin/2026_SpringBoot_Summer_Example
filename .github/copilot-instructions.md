# Copilot instructions for 2026_SpringBoot_Summer_Example

Build, test, and lint commands
- Build: mvn -U clean package
- Run (dev): mvn spring-boot:run
- Run packaged JAR: java -jar target/2026_SpringBoot_Summer_Example-1.0-SNAPSHOT.jar
- Tests: mvn test
- Run a single test class: mvn -Dtest=ClassName test
- Run a single test method: mvn -Dtest=ClassName#methodName test
- Lint/format: No linter or formatter is configured in the pom.xml. Add Maven plugins (checkstyle/spotless) if desired.

High-level architecture (big picture)
- Spring Boot application (main class: com.keyin.RestServiceApplication).
- Package: com.keyin.domain contains the primary domain model and web layer.
  - Trail (entity) — JPA entity (jakarta.persistence).
  - TrailRepository — Spring Data CrudRepository<Trail,Long> for persistence.
  - TrailService — @Service layer that encapsulates business logic and uses the repository.
  - TrailController — @RestController exposing HTTP endpoints.
- Persistence: spring-boot-starter-data-jpa with H2 (runtime) and MySQL connector present. application.properties configures the datasource and spring.jpa.hibernate.ddl-auto=update.
- HTTP endpoints of note:
  - POST /trail -> create a Trail
  - GET /trail/{id} -> retrieve a Trail by id

Key conventions and patterns (project-specific)
- Layering: Controller -> Service -> Repository. Keep controller thin; place logic in the service layer.
- Repositories extend CrudRepository and return Optional for findById; services often unwrap with orElse(null) (current pattern).
- JPA entity uses jakarta.persistence annotations and standard JavaBean getters/setters.
- CrossOrigin is enabled at controller level for simple CORS allowance.
- Java target is Java 26 (see pom.xml <maven.compiler.source/target>).
- Spring Boot parent version: 4.0.6 (see pom.xml).
- application.properties contains local DB configuration; credentials may be present in tree — do not commit production secrets. Prefer Spring profiles or environment variables for overriding.

AI assistant and other tooling files checked
- No CLAUDE.md, .cursorrules, AGENTS.md, .windsurfrules, CONVENTIONS.md, AIDER_CONVENTIONS.md, or other known AI assistant config files were found in the repository root.

Notes for Copilot sessions
- Focus on the com.keyin.domain package to understand most of the app behavior.
- Tests live under src/test (not present currently) — use mvn -Dtest=... to iterate quickly.
- There is no CI/lint configuration in the repo; mention this when making PR suggestions.

