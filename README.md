# Concept CRM

Sistema de gestión de relaciones con clientes (CRM) con backend en Spring Boot y frontend en Next.js.

## Stack tecnológico

| Capa | Tecnología |
|------|-----------|
| Frontend | Next.js 15 · TypeScript · Tailwind CSS |
| Backend | Spring Boot 4 · Java 17 · Spring Data JPA |
| Base de datos | PostgreSQL 16 |
| Infraestructura | Docker · Docker Compose |

## Estructura del proyecto

```
crm/
├── server/          # API REST — Spring Boot
├── web/             # Aplicación web — Next.js
├── porpeties/       # Variables de entorno compartidas
└── docker-compose.yml
```

## Módulos del CRM

| Módulo | Descripción |
|--------|------------|
| Contactos | Personas vinculadas a cuentas (clientes, prospectos, socios) |
| Cuentas | Empresas u organizaciones |
| Leads | Prospectos en etapas de calificación |
| Oportunidades | Negociaciones activas con monto y probabilidad de cierre |
| Actividades | Registro de llamadas, emails, reuniones y demos |
| Tareas | Acciones pendientes con prioridad y vencimiento |

## Requisitos previos

- Java 17+
- Node.js 20+
- Docker y Docker Compose

## Inicio rápido

### Con Docker (recomendado)

```bash
docker compose up
```

Servicios disponibles:
- Frontend: http://localhost:3000
- API: http://localhost:8080
- PostgreSQL: localhost:5432

### En desarrollo (sin Docker)

**1. Base de datos**

```bash
docker compose up postgres -d
```

**2. Backend**

```bash
cd server
./mvnw spring-boot:run
```

**3. Frontend**

```bash
cd web
npm install
npm run dev
```

## Variables de entorno

Copia `porpeties/.env` y ajusta los valores según tu entorno:

| Variable | Valor por defecto | Descripción |
|----------|------------------|-------------|
| `DB_HOST` | `localhost` | Host de PostgreSQL |
| `DB_PORT` | `5432` | Puerto de PostgreSQL |
| `DB_NAME` | `crm_db` | Nombre de la base de datos |
| `DB_USER` | `crm_user` | Usuario de la base de datos |
| `DB_PASSWORD` | `crm_pass` | Contraseña de la base de datos |
| `NEXT_PUBLIC_API_URL` | `http://localhost:8080` | URL base de la API |

## API REST

Todos los endpoints siguen el patrón `/api/{recurso}`:

```
GET    /api/contacts
POST   /api/contacts
GET    /api/contacts/{id}
PUT    /api/contacts/{id}
DELETE /api/contacts/{id}

GET    /api/leads
GET    /api/leads/status/{status}
GET    /api/leads/stats

GET    /api/opportunities
GET    /api/opportunities/stage/{stage}
GET    /api/opportunities/pipeline

GET    /api/tasks
GET    /api/tasks/overdue
GET    /api/tasks/stats

GET    /api/activities
GET    /api/activities/pending
GET    /api/activities/range?from=...&to=...

GET    /api/accounts
GET    /api/accounts/search?q=...
```

## Estructura del servidor

```
server/src/main/java/com/concept/crm/
├── config/        CorsConfig, JpaConfig
├── shared/        BaseEntity (id, createdAt, updatedAt, active)
├── enums/         LeadStatus, OpportunityStage, ActivityType,
│                  TaskStatus, TaskPriority, ContactType, UserRole
├── entity/        Entidades JPA
├── repository/    Interfaces Spring Data JPA
├── dto/           Objetos de transferencia de datos
├── service/       Lógica de negocio
└── controller/    Controladores REST
```
