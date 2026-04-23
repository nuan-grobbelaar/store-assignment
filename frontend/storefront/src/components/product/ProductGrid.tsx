import type { Product } from "../../hooks/useProducts.ts";
import ProductCard from "./ProductCard.tsx";

interface ProductGridProps {
  products: Product[];
  isLoading: boolean;
  error: Error | null;
}

function ProductGrid({ products, isLoading, error }: ProductGridProps) {
  return (
    <div className="product-grid">
      {isLoading && <p>Loading products...</p>}
      {error && <p>Error loading products: {(error as Error).message}</p>}
      {products && products.length > 0
        ? products.map((product) => <ProductCard product={product} key={product.id} />)
        : !isLoading && <p>No products available</p>}
    </div>
  );
}

export default ProductGrid;
