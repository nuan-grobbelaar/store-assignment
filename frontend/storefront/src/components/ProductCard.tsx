import type { Product } from "../hooks/useProducts.ts";
import { useCart } from "../context/CartContext.tsx";

interface ProductCardProps {
  product: Product;
}

function ProductCard({ product }: ProductCardProps) {
  const { addToCart } = useCart();

  return (
    <div
      className="productCard"
      key={product.id}
      onClick={() => addToCart(product)}
    >
      <div className="productImage">
        <img src={product.imageUrl} alt={product.name} />
      </div>
      <div className="productInfo">
        <h3>{product.name}</h3>
        {product.price && <p className="price">${product.price}</p>}
        {product.brand && <p className="brand">{product.brand}</p>}
      </div>
    </div>
  );
}

export default ProductCard;
