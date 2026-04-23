import type { Product } from "../../hooks/useProducts.ts";
import { useState } from "react";
import ProductControls from "./ProductControls.tsx";

interface ProductCardProps {
  product: Product;
}

function ProductCard({ product }: ProductCardProps) {
  const [expand, setExpand] = useState(false);

  return (
    <div className="product-card" onClick={() => setExpand((e) => !e)}>
      <div className="product-image">
        {expand ? (
          <>
            <div className="product-description">{product.description}</div>
            <ProductControls product={product} />
          </>
        ) : (
          <img src={product.imageUrl} alt={product.name} />
        )}
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
