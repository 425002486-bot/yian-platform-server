# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

**ruoyi-vue-pro (YuDAO)** is a Java enterprise rapid-development platform built on Spring Boot 3.5 + JDK 17. Current branch is `master-jdk17`. The base package is `cn.iocoder.yudao`.

## Build & Run Commands

```bash
# Full build (skip tests for speed)
mvn clean install -DskipTests

# Build a specific module
mvn clean install -DskipTests -pl yian-module-system -am

# Run all tests
mvn test

# Run tests for a specific module
mvn test -pl yian-module-system

# Run a single test class
mvn test -pl yian-module-system -Dtest=AdminUserServiceImplTest

# Run the application (port 48080)
mvn spring-boot:run -pl yian-server
```

The application entry point is `YianServerApplication` in `yian-server`. Default profile is `local`, connecting to MySQL on `127.0.0.1:3306` and Redis on `127.0.0.1:6379`.

## Architecture

```
yian-dependencies/          # BOM - all dependency versions managed here
yian-framework/             # Reusable Spring Boot starters
  yian-common/              # Base classes, utils, enums, ErrorCode definitions
  yian-spring-boot-starter-mybatis/     # MyBatis Plus + multi-DB support
  yian-spring-boot-starter-redis/       # Redis + Redisson
  yian-spring-boot-starter-web/         # REST conventions, Jackson, Swagger
  yian-spring-boot-starter-security/    # Auth (Spring Security + Token + Redis)
  yian-spring-boot-starter-biz-tenant/  # SaaS multi-tenancy
  yian-spring-boot-starter-biz-data-permission/  # Row-level data permissions
  yian-spring-boot-starter-test/        # Test base classes & utilities
  ... (job, mq, excel, monitor, protection, websocket, biz-ip)
yian-module-system/         # Core: users, depts, roles, permissions, dict, OAuth2, SMS, mail
yian-module-infra/          # Infrastructure: config, file, codegen, job, API logs
yian-module-*/              # Business modules (bpm, pay, mall, crm, erp, iot, mes, ai, member, mp, report)
yian-server/                # Aggregator - assembles modules into a runnable Spring Boot app
```

### Enabling Modules

Most modules are commented out in the root `pom.xml` and `yian-server/pom.xml`. Uncomment the `<module>` and `<dependency>` entries to enable them. By default only `system` and `infra` are active.

### Module Internal Structure

Each business module follows a consistent layered package structure under `cn.iocoder.yudao.module.{moduleName}`:

| Package | Purpose |
|---------|---------|
| `controller/admin/` | Admin-facing REST endpoints (`/admin-api/...`) |
| `controller/app/` | App/user-facing REST endpoints (`/app-api/...`) |
| `controller/**/vo/` | Request/Response Value Objects for controllers |
| `service/` | Business logic (interfaces + `Impl`) |
| `dal/dataobject/` | Data Objects (DB entities), suffixed `DO` |
| `dal/mysql/` | MyBatis Plus Mapper interfaces |
| `dal/redis/` | Redis DAO operations |
| `convert/` | MapStruct converters (VO <-> DO <-> DTO) |
| `api/` | Cross-module API interfaces + DTOs (for other modules to call) |
| `enums/` | Module-specific enums and error codes |
| `framework/` | Module-specific Spring configurations |
| `job/` | Scheduled tasks |
| `mq/` | Message producers/consumers |

### Inter-Module Communication

Modules call each other via `*Api` interfaces in the `api/` package (e.g., `AdminUserApi`). The implementation (`*ApiImpl`) lives in the same module. This provides a clean contract without circular dependencies.

For the mall module specifically, `yian-module-trade-api` exists as a separate submodule to break a circular dependency between `trade` and `promotion`.

## Testing Conventions

- **Framework:** JUnit 5 + Mockito + H2 in-memory DB + Jedis Mock
- **Base classes** (in `yian-spring-boot-starter-test`):
  - `BaseMockitoUnitTest` - pure mock tests
  - `BaseDbUnitTest` - tests requiring H2 database (auto-cleans via `/sql/clean.sql`)
  - `BaseRedisUnitTest` - tests requiring mock Redis
  - `BaseDbAndRedisUnitTest` - both DB and Redis
- **Test data:** Use `randomPojo(XxxDO.class, o -> o.setField(...))` from `RandomUtils` to generate random test objects
- **Assertions:** Use `AssertUtils.assertServiceException()` for business exception verification, `AssertUtils.assertPojoEquals()` for DO/VO comparison
- **Mocks:** Use `@MockitoBean` for Spring context mocks

## Key Conventions

- **Error codes:** Defined as `ErrorCode` constants in each module's `enums/ErrorCode*.java`. Format: `new ErrorCode(code, "message")`
- **API responses:** Wrapped in `CommonResult<T>` via `CommonResult.success(data)`
- **Pagination:** Use `PageResult<T>` for paginated queries, `PageParam` as base request
- **Data Objects:** Extend `BaseDO` which provides `id`, `createTime`, `updateTime`, `creator`, `updater`, `deleted` fields
- **Logic delete:** Field `deleted` (0=active, 1=deleted), handled automatically by MyBatis Plus
- **Multi-tenancy:** Handled transparently by the tenant framework; `tenant_id` column auto-filtered
- **Object conversion:** MapStruct interfaces in `convert/` package, named `XxxConvert` with `INSTANCE` singleton
- **Lombok:** Used throughout - `@Data`, `@Builder`, `@AllArgsConstructor` etc.
- **Config files:** `application.yaml` (base) + `application-local.yaml` (local dev) in `yian-server/src/main/resources/`

## SQL

Database scripts are in `sql/`. The main schema file is `sql/mysql/ruoyi-vue-pro.sql`.
