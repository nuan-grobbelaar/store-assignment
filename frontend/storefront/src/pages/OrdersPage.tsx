import { useOrders } from "../hooks/useOrders.ts";

function OrdersPage() {
  const { data: orders, isLoading, error } = useOrders();

  if (isLoading) return <p className="orders-page__status">Loading orders...</p>;
  if (error) return <p className="orders-page__status">Error loading orders: {error.message}</p>;
  if (!orders || orders.length === 0) return <p className="orders-page__status">No orders yet</p>;

  return (
    <section className="orders-page">
      <h2 className="orders-page__title">Your Orders</h2>
      <ul className="orders-page__list">
        {orders.map((order) => (
          <li key={order.id} className="orders-page__item">
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
                  Product #{line.productId} &times; {line.quantity}
                </li>
              ))}
            </ul>
            <div className="orders-page__item-total">Total: €{order.total.toFixed(2)}</div>
          </li>
        ))}
      </ul>
    </section>
  );
}

export default OrdersPage;
