import { useProducts } from "./hooks/useProducts.ts";
import ProductGrid from "./components/ProductGrid.tsx";
import Navbar from "./components/Navbar.tsx";

function App() {
  const { data: products, isLoading, error } = useProducts();

  return (
    <>
      <Navbar />
      <ProductGrid products={products || []} isLoading={isLoading} error={error} />
    </>
  );
}

export default App;
