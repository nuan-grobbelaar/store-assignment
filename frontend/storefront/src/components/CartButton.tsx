import { useCart } from "../context/CartContext.tsx";
import CartIcon from "../icons/CartIcon.tsx";

function CartButton() {
  const { items } = useCart();
  const count = items.reduce((sum, i) => sum + i.quantity, 0);

  return (
    <div className="cart-button">
      <CartIcon />
      {count > 0 && <span className="cart-button__counter">{count}</span>}
    </div>
  );
}

export default CartButton;
