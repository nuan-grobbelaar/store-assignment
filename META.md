# Assignment Notes

Context on architecture, decisions, trade-offs, and things left on the cutting room floor.

---

## Architecture

The backend follows **hexagonal architecture** (ports & adapters). The domain layer — entities (`Product`, `Order`) and use cases (`GetProductsUseCase`, `CreateOrderUseCase`) — has zero knowledge of Spring, HTTP, or file I/O. Infrastructure adapters implement the domain's ports: one reads products from a bundled JSON file, another persists orders to a JSON file on disk. Controllers sit in a separate adapter layer and translate HTTP to use case calls.

The frontend is a standard React/TypeScript app built with Vite. State is split: cart state lives in React Context (synced to `localStorage`), server data is fetched via custom hooks. Components are broken into layout, product, and cart concerns.

---

## Decisions & trade-offs

**Hexagonal architecture on the backend.**
More boilerplate than a plain Spring CRUD app, but the domain stays clean and swapping the JSON repository for a real database is a one-file config change, not a rewrite.

**JSON files instead of a database.**
The data model is simple and the brief said logging a purchase was sufficient. Flat files kept the focus on API design and domain structure rather than schema and migrations.

**`localStorage` for the cart.**
No server-side session needed, cart persists across page refreshes, zero infrastructure. Downside: per-browser, not tied to a user account.

**React Context for cart state.**
Redux would be overkill here. Context + `localStorage` sync covers the requirements without the overhead.

---

## What I'd do next

- **Real database.** Orders write to `orders.json` inside the container and vanish on rebuild. Swapping in PostgreSQL would fix that — the hexagonal architecture makes it a clean swap.
- **Auth.** The orders page shows every order from every "user". JWT-based auth would let the frontend filter by the logged-in customer's email — `customerEmail` is already on the model, so it's half-done.
- **Stock management.** Nothing stops you from ordering 9,000 of something. A real storefront would decrement stock on order and block purchases when sold out.
- **Product details page.** The product card has quite limited space and the solution I went for to include the description isn't the best. A dedicated product page would let you show more info and a bigger image.
- **Order status.** A status field (`pending → confirmed → shipped → delivered`) would make the orders page actually useful.
- **Frontend search/filter UI.** The backend already supports search and category filtering. The UI doesn't expose it yet.
- **Better validation feedback.** The frontend handles loading and error states but leans on generic messages. Checkout in particular deserves more specific feedback.

---

## Known shortcuts

- **Orders are global.** No auth means the orders page shows everyone's orders. `customerEmail` is collected at checkout but not used for filtering.
- **No stock limits.** Quantity is unconstrained.
- **Orders lost on rebuild.** `orders.json` lives inside the container — `docker compose down` and back up means a fresh slate.
- **No input validation on the backend.** The API trusts whatever the frontend sends. A production service would validate and sanitize.
- **Cart Controls.** The store UI only supports adding items from the product listing. A real app would let you adjust quantities and remove items from the cart sidebar itself.

---

## Challenges

- **Hexagonal architecture.** It took a bit of trial and error to find a structure that felt clean without being over-engineered.
- **Fitting all the product information in the grid items.** The product cards had to balance showing enough info (name, price, description, image) without becoming too cluttered.

---

## Assumptions

- The brief said "logging a line for the purchase is sufficient" — took that as a green light to build a proper orders endpoint and orders page instead, since it felt more complete.
- Docker wasn't in the brief. Added it to make the app easy to run without needing Java or Node installed locally.
