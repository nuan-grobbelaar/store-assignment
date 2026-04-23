import { useOrders } from "../hooks/useOrders.ts";
import OrderCard from "../components/order/OrderCard.tsx";

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
          <OrderCard key={order.id} order={order} />
        ))}
      </ul>
    </section>
  );
}

export default OrdersPage;
