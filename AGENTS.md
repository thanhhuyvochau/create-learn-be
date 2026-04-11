# AGENTS.md — create-learn-be

Sub-project instruction file for the Spring Boot backend. Read the root
[`../AGENTS.md`](../AGENTS.md) and the linked technical docs first.

**Required skill:** Load **`spring-boot-executor`** before writing any Java
code. It contains all patterns (entity, DTO, mapper, service, controller,
repository, exception, test) adapted to this exact codebase.

**Technical docs:**
- [`../docs/architecture.md`](../docs/architecture.md) — package layout,
  `ApiWrapper`, URL handling, security, error handling
- [`../docs/entity-relations.md`](../docs/entity-relations.md) — all entities
  and relationships, including Recruitment
- [`../docs/test-guide.md`](../docs/test-guide.md) — JUnit 5 + Mockito
  patterns with full code templates

---

## Build & Run

```bash
mvn clean install              # Full build with tests
mvn clean install -DskipTests  # Build, skip tests
mvn spring-boot:run -Pdev      # Dev (local profile, port 8080)
mvn spring-boot:run -Pprod     # Production profile
mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
```

## Test Commands

```bash
mvn test                                         # All tests
mvn test -Dtest=ClassName                        # Single class
mvn test -Dtest=ClassName#methodName             # Single method
mvn test jacoco:report                           # With coverage report
```

---

## Critical Rules

- Package `serivce` is an **intentional typo** — never rename it to `service`
- Constructor injection only via `@RequiredArgsConstructor`; never `@Autowired`
- All controller responses wrapped in `ResponseEntity<ApiWrapper<T>>`
- Never return JPA entities from controllers — always map to response DTOs
- Use `UrlUtils.buildAbsolutePath()` for MinIO image URLs; never hardcode them
- Use `UrlUtils.stripMinioExternalUrl()` on every image field before persisting
- Never commit `application-prod.yaml` or any credentials
- `@Valid` on every `@RequestBody` that has validation annotations
- Every controller class needs `@Tag`; every method needs `@Operation`

---

## Package Structure (summary)

Root: `org.project.createlearnbe`

```
controllers/      # @RestController
serivce/          # @Service  ← intentional typo
repositories/     # JpaRepository interfaces
mapper/           # MapStruct mappers
entities/         # JPA @Entity classes
dto/request/      # Inbound DTOs
dto/response/     # Outbound DTOs
constant/         # Enums
utils/            # UrlUtils, JwtUtil, AuthUtil, ImageUtil, SecurityUtil
config/           # ApiWrapper, exceptions, security, MinIO, auditing
```

---

## Entities Overview

Full field and relationship detail is in
[`../docs/entity-relations.md`](../docs/entity-relations.md).

| Entity | Notes |
|--------|-------|
| `Account` | Staff only; roles: `ADMIN`, `OPERATOR` |
| `Clazz` | Central entity; soft-deleted (`isDeleted`); ManyToMany Subject/Grade; ManyToOne Teacher; OneToMany Schedule |
| `Subject` | `iconBase64` stored in DB |
| `Grade` | `iconBase64` stored in DB |
| `Teacher` | `profileImageUrl` in MinIO |
| `Schedule` | Free-text time slot per class |
| `Registration` | Statuses: `PROCESSING → PROCESSED / CLASS_DELETED` |
| `Consultation` | Statuses: `PROCESSING → PROCESSED / REJECTED` |
| `News` | `isDisplay` toggle; rich HTML content |
| `JobPosting` | Recruitment; has child `JobBenefit` and `JobResponsibility` lists |
| `JobBenefit` | OneToMany child of `JobPosting` |
| `JobResponsibility` | OneToMany child of `JobPosting` |

**Recruitment constants** (in `constant/` package):
- `JobType` — e.g. `FULL_TIME`, `PART_TIME`
- `BadgeVariant` — UI badge styling for job postings

---

## Existing Exception Types

| Type | HTTP | When |
|------|------|------|
| `ResourceNotFoundException` | 404 | Entity not found by ID |
| `UserNameDuplicateException` | 409 | Username already taken |
| `EmailDuplicateException` | 409 | Email already registered |
| `PhoneDuplicateException` | 409 | Phone already in use |
| `InvalidFileTypeException` | 400 | Wrong upload file type |

To add a new type: create class in `config/exception/types/`, extend
`RuntimeException`, register a handler in `RestExceptionHandler`.

---

## Config Profiles

| Profile | Flag | Notes |
|---------|------|-------|
| `local` | `-Pdev` | localhost:3307 DB, localhost:9000 MinIO, mock data enabled |
| `prod` | `-Pprod` | Production DB + MinIO; credentials via env vars only |
