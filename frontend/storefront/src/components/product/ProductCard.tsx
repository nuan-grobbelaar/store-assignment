import type { Product } from "../../hooks/useProducts.ts";
import { useNavigate } from "react-router-dom";

interface ProductCardProps {
  product: Product;
}

function ProductCard({ product }: ProductCardProps) {
  const navigate = useNavigate();
  return (
    <div className="product-card" onClick={() => navigate(`/product/${product.id}`)}>
      <div className="product-image">
        <img src={product.imageUrl} alt={product.name} />
      </div>
      <div className="product-info">
        <h3>{product.name}</h3>
        {product.price && <p className="price">€{product.price}</p>}
        {product.brand && <p className="brand">{product.brand}</p>}
      </div>
    </div>
  );
}

export default ProductCard;
