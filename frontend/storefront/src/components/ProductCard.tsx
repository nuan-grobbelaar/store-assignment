import type { Product } from "../hooks/useProducts.ts";

interface ProductCardProps {
  product: Product;
}

function ProductCard({ product }: ProductCardProps) {
  return (
    <div key={product.id}>
      <p>{product.name}</p>
    </div>
  );
}

export default ProductCard;
