import type { Product } from "../../hooks/useProducts.ts";
import { useCart } from "../../context/CartContext.tsx";

interface ProductControlsProps {
  product: Product;
}

function ProductControls({ product }: ProductControlsProps) {
  const { addToCart, decrement, items } = useCart();
  const quantity = items.find((i) => i.product.id === product.id)?.quantity ?? 0;

  return (
    <div className="product-controls" onClick={(e) => e.stopPropagation()}>
      {quantity === 0 ? (
        <button
          type="button"
          className="product-controls__buy"
          onClick={() => addToCart(product)}
          aria-label={`Add ${product.name} to cart`}
        >
          <p>Add to cart</p>
        </button>
      ) : (
        <>
          <button
            type="button"
            onClick={() => decrement(product)}
            aria-label={`Remove one ${product.name}`}
          >
            −
          </button>
          <span aria-live="polite">{quantity}</span>
          <button
            type="button"
            onClick={() => addToCart(product)}
            aria-label={`Add another ${product.name}`}
          >
            +
          </button>
        </>
      )}
    </div>
  );
}

export default ProductControls;
