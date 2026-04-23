type OrderStatus = "idle" | "loading" | "success" | "error";

interface CartSidebarFooterProps {
  total: number;
  status: OrderStatus;
  onOrder: () => void;
  disabled: boolean;
}

function CartSidebarFooter({ total, status, onOrder, disabled }: CartSidebarFooterProps) {
  return (
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
            onClick={onOrder}
            disabled={disabled}
          >
            {status === "loading" ? "Placing order…" : "Order"}
          </button>
        </>
      )}
    </footer>
  );
}

export default CartSidebarFooter;
