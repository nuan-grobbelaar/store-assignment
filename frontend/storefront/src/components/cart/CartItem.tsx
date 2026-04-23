import type { CartItem as CartItemType } from "../../context/CartContext.tsx";

interface CartItemProps {
  item: CartItemType;
}

function CartItem({ item }: CartItemProps) {
  return (
    <li className="cart-sidebar__item">
      <div className="cart-sidebar__thumb">
        <img src={item.product.imageUrl} alt={item.product.name} />
      </div>
      <div className="cart-sidebar__details">
        <p className="cart-sidebar__name">{item.product.name}</p>
        <p className="cart-sidebar__meta">
          {item.quantity} × ${item.product.price.toFixed(2)}
        </p>
      </div>
      <p className="cart-sidebar__line-total">€{(item.product.price * item.quantity).toFixed(2)}</p>
    </li>
  );
}

export default CartItem;
