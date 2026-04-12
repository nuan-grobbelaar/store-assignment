# Fullstack Developer Assessment: Mini Webshop

## Overview

In this task you are required to build a small webshop application. The goal is to assess your ability to structure a fullstack web application with a **React** frontend and a **Spring Boot** (Java or Kotlin) backend exposing a REST API.

---

## Task Breakdown

1. Create a new public GitHub repository and set up the project structure.
2. Build a product listing page that fetches and renders products from the backend.
3. Implement a shopping basket that allows users to add and remove products.
4. Implement the ability to purchase the items in your basket, this should be done by submitting the purchase to the backend (Logging a line for the purchase in the backend is sufficient).

---

## Provided Assets

- **`products.json`** — A JSON file containing 12 tech/electronics products. Use this as the data source in your backend.

---

## Functional Requirements

### Product Listing

- Expose a REST endpoint from the Spring Boot backend that returns the list of products.
- Fetch the products from the backend.
- Render the products dynamically as product cards. Each card should display at minimum:
    - Product name
    - Price
    - A short description
    - An "Add to basket" button
    - Rendering an image of the item

### Shopping Basket

- Users can add products to the basket from the product listing page.
- The basket should show the selected items and the total price.
- Users can remove individual items from the basket.
- The basket contents should **persist across page reloads** using `localStorage` or `sessionStorage`.

---

## Technical Requirements

### Backend (Spring Boot)

- Use **Java** or **Kotlin** with **Spring Boot**.
- Load the `products.json` file and serve it through a REST API.
- At minimum, implement the following endpoint:

  | Method | Path | Description |
    |--------|------|-------------|
  | `GET` | `/api/products` | Returns all products |

- Follow REST conventions and return appropriate HTTP status codes.
- The backend should handle **CORS** so the frontend can communicate with it during local development.

### Frontend (React)

- Use **React** with **TypeScript**.
- Use a component-based architecture — break the UI into logical, reusable components.
- Manage state appropriately (React's built-in state management via `useState`/`useContext` is sufficient; Redux or Zustand are welcome if you prefer).
- Use a modern build tool such as **Vite** or Create React App.

---

## Primary Criteria

- **Clean and structured code** — modular, readable, and following modern best practices.
- **REST API design** — well-thought-out endpoint structure with correct HTTP semantics.
- **React component architecture** — logical separation of concerns.
- **TypeScript** — use types throughout the frontend codebase.
- **Basket persistence** — basket contents survive a page reload.

---

## Secondary Criteria (Bonus)

- **Additional endpoints** — for example `GET /api/products/{id}` for a product detail view.
- **Search or filter** — filter the product list by name or category (filtering logic on the backend).
- **Responsive design** — the webshop works well on both desktop and mobile.
- **Advanced CSS** — Flexbox, Grid, CSS variables, or a preprocessor like Sass.
- **Error handling** — graceful handling of API errors on the frontend.
- **Testing** — unit or integration tests on either the backend (JUnit) or frontend (Jest / React Testing Library).

---

## Additional Considerations

- **Code quality** — use meaningful names, avoid duplication, and keep components focused.
- **Version control** — use Git with a meaningful commit history. Small, focused commits are preferred over one large commit.
- **It is okay not to finish everything** — what matters most is how you structure and communicate your decisions.

---

## Submission

Push your code to a public GitHub repository and share the link.

### Include a `README.md` in the repository that covers

- The overall architecture and important design decisions.
- How to run the application locally (backend and frontend separately).
- Any trade-offs or shortcuts taken, and what you would do differently with more time.
- Any challenges you ran into and how you resolved them.

---

## Evaluation Criteria

| Area | What we look at |
|------|----------------|
| **Code structure** | Clean, modular, well-organized code following best practices |
| **Backend** | REST API design, Spring Boot conventions, data loading |
| **Frontend** | React component architecture, TypeScript usage, state management |
| **UI/UX** | A functional and user-friendly product listing and basket experience |
| **Persistence** | Basket correctly survives page reloads |
| **Communication** | Quality of the README and commit history |
| **Bonus** | Testing, filtering, responsive design, error handling |

---

Good luck — we look forward to seeing your solution!
