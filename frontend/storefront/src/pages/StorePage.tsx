import { useProducts } from "../hooks/useProducts.ts";
import ProductGrid from "../components/product/ProductGrid.tsx";

function StorePage() {
  const { data: products, isLoading, error } = useProducts();
  return <ProductGrid products={products || []} isLoading={isLoading} error={error} />;
}

export default StorePage;
