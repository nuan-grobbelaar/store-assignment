import type { Product } from "../hooks/useProducts.ts";

interface ProductCardProps {
  product: Product;
}

function ProductCard({ product }: ProductCardProps) {
  return (
    <div className="productCard" key={product.id}>
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
