import { useProducts } from "./hooks/useProducts.ts";
import ProductGrid from "./components/product/ProductGrid.tsx";
import Navbar from "./components/layout/Navbar.tsx";
import CartSidebar from "./components/cart/CartSidebar.tsx";

function App() {
  const { data: products, isLoading, error } = useProducts();

  return (
    <>
      <Navbar />
      <ProductGrid products={products || []} isLoading={isLoading} error={error} />
      <CartSidebar />
    </>
  );
}

export default App;
