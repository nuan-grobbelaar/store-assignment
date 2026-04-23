import { useQueryClient } from "@tanstack/react-query";
import { useCart } from "../../context/CartContext.tsx";
import CartItem from "./CartItem.tsx";
import CartSidebarFooter from "./CartSidebarFooter.tsx";

function CartSidebar() {
  const { items, isOpen, status, setStatus, closeCart, clearCart } = useCart();
  const queryClient = useQueryClient();

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
      queryClient.invalidateQueries({ queryKey: ["orders"] });
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
            <CartItem key={item.product.id} item={item} />
          ))}
        </ul>
        <CartSidebarFooter
          total={total}
          status={status}
          onOrder={handleOrder}
          disabled={status === "loading" || items.length === 0}
        />
      </aside>
    </>
  );
}

export default CartSidebar;
