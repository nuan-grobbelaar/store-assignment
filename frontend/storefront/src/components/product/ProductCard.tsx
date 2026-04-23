import type { Product } from "../../hooks/useProducts.ts";
import { useCart } from "../../context/CartContext.tsx";

interface ProductCardProps {
  product: Product;
}

function ProductCard({ product }: ProductCardProps) {
  const { addToCart, decrement, items } = useCart();
  const quantity = items.find((i) => i.product.id === product.id)?.quantity ?? 0;

  return (
    <div className="product-card" onClick={() => addToCart(product)}>
      <div className="product-image">
        <img src={product.imageUrl} alt={product.name} />
        {quantity > 0 && (
          <div className="product-controls" onClick={(e) => e.stopPropagation()}>
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
          </div>
        )}
      </div>
      <div className="product-info">
        <h3>{product.name}</h3>
        {product.price && <p className="price">${product.price}</p>}
        {product.brand && <p className="brand">{product.brand}</p>}
      </div>
    </div>
  );
}

export default ProductCard;
