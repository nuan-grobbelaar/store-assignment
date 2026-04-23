import type { Order } from "../../hooks/useOrders.ts";

interface OrderCardProps {
  order: Order;
}

function OrderCard({ order }: OrderCardProps) {
  return (
    <li className="orders-page__item">
      <header className="orders-page__item-header">
        <span className="orders-page__item-id">Order #{order.id}</span>
        <span className="orders-page__item-date">
          {order.createdAt ? new Date(order.createdAt).toLocaleString() : ""}
        </span>
      </header>
      <p className="orders-page__item-email">{order.customerEmail}</p>
      <ul className="orders-page__item-lines">
        {order.items.map((line) => (
          <li key={line.productId}>
            {line.productName} &times; {line.quantity}
          </li>
        ))}
      </ul>
      <div className="orders-page__item-total">Total: €{order.total.toFixed(2)}</div>
    </li>
  );
}

export default OrderCard;
