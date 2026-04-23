# The Storefront

> A little shop. Products. A cart. Orders. That's it. That's the whole thing.

---

## What is this?

Imagine a store. Now imagine that store, but on your computer. You can browse products, throw things into a cart like you're in a hurry, and place orders. No checkout anxiety. No loyalty points. You don't even need to spend real money!

---

## Architecture

**Backend:** Spring Boot (Java 21), structured with hexagonal architecture. Domain logic (use cases, entities) is fully decoupled from infrastructure. Products are loaded from a bundled JSON file; orders are persisted to a JSON file on disk. Two REST controllers handle products and orders.

**Frontend** — React + TypeScript, built with Vite. Cart state lives in React Context and syncs to `localStorage`. Server data is fetched via custom hooks. Components are split into layout, product, and cart concerns.

For the full breakdown of decisions, trade-offs, shortcuts, and challenges, see [META.md](META.md).

---

## Running it

### Docker (easiest)

Requires [Docker](https://www.docker.com/get-started). No Java or Node needed.

```bash
docker compose up --build
```

| Thing | Where |
|-------|-------|
| The shop | http://localhost |
| The API | http://localhost:8080 |

```bash
docker compose down  # to stop
```

### Locally

**Backend** — requires Java 21+

```bash
cd backend
./mvnw spring-boot:run
```

API runs at http://localhost:8080

**Frontend** — requires Node 18+

```bash
cd frontend/storefront
npm install
npm run dev
```

App runs at http://localhost:5173

---

## Poking the API

There's a [Bruno](https://www.usebruno.com/) collection in `backend/bruno/storefront-collection` with all endpoints ready to go.
