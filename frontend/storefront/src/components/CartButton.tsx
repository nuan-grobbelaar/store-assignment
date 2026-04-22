import { useCart } from "../context/CartContext.tsx";
import CartIcon from "../icons/CartIcon.tsx";

function CartButton() {
  const { items, openCart } = useCart();
  const count = items.reduce((sum, i) => sum + i.quantity, 0);

  return (
    <button
      type="button"
      className="cart-button"
      onClick={() => count > 0 && openCart()}
      aria-label="Open cart"
    >
      <CartIcon />
      {count > 0 && <span className="cart-button__counter">{count}</span>}
    </button>
  );
}

export default CartButton;
