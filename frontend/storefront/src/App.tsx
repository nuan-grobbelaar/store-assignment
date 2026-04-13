import { useProducts } from "./hooks/useProducts.ts";
import ProductGrid from "./components/ProductGrid.tsx";

function App() {
  const { data: products, isLoading, error } = useProducts();

  return (
    <>
      <ProductGrid products={products || []} isLoading={isLoading} error={error} />
    </>
  );
}

export default App;
