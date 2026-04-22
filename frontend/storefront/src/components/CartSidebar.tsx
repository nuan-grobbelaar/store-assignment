import { useCart } from "../context/CartContext.tsx";

function CartSidebar() {
  const { items, isOpen, status, setStatus, closeCart, clearCart } = useCart();

  if (!isOpen) return null;

  const total = items.reduce((sum, i) => sum + i.product.price * i.quantity, 0);

  async function handleOrder() {
    setStatus("loading");
    try {
      const res = await fetch("/api/orders", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          customerEmail: "hello@nuan.dev",
          items: items.map((i) => ({ productId: i.product.id, quantity: i.quantity })),
        }),
      });
      if (!res.ok) throw new Error();
      clearCart();
      setStatus("success");
    } catch {
      setStatus("error");
    }
  }

  return (
    <>
      <div className="cart-sidebar__overlay" onClick={closeCart} />
      <aside className="cart-sidebar" aria-label="Shopping cart">
        <header className="cart-sidebar__header">
          <h2>Your Cart</h2>
          <button
            type="button"
            className="cart-sidebar__close"
            onClick={closeCart}
            aria-label="Close cart"
          >
            ×
          </button>
        </header>
        <ul className="cart-sidebar__list">
          {items.map((item) => (
            <li key={item.product.id} className="cart-sidebar__item">
              <div className="cart-sidebar__thumb">
                <img src={item.product.imageUrl} alt={item.product.name} />
              </div>
              <div className="cart-sidebar__details">
                <p className="cart-sidebar__name">{item.product.name}</p>
                <p className="cart-sidebar__meta">
                  {item.quantity} × ${item.product.price.toFixed(2)}
                </p>
              </div>
              <p className="cart-sidebar__line-total">
                ${(item.product.price * item.quantity).toFixed(2)}
              </p>
            </li>
          ))}
        </ul>
        <footer className="cart-sidebar__footer">
          <div className="cart-sidebar__total">
            <span>Total</span>
            <span>${total.toFixed(2)}</span>
          </div>
          {status === "success" ? (
            <p className="cart-sidebar__status cart-sidebar__status--success">Order placed!</p>
          ) : (
            <>
              {status === "error" && (
                <p className="cart-sidebar__status cart-sidebar__status--error">
                  Order failed. Try again.
                </p>
              )}
              <button
                type="button"
                className="cart-sidebar__order-btn"
                onClick={handleOrder}
                disabled={status === "loading" || items.length === 0}
              >
                {status === "loading" ? "Placing order…" : "Order"}
              </button>
            </>
          )}
        </footer>
      </aside>
    </>
  );
}

export default CartSidebar;
