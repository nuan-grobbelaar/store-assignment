import { Route, Routes } from "react-router-dom";
import StorePage from "./pages/StorePage.tsx";
import OrdersPage from "./pages/OrdersPage.tsx";
import ProductPage from "./pages/ProductPage.tsx";
import Layout from "./components/layout/Layout.tsx";

function App() {
  return (
    <Routes>
      <Route element={<Layout />}>
        <Route index element={<StorePage />} />
        <Route path="orders" element={<OrdersPage />} />
        <Route path="product/:id" element={<ProductPage />} />
      </Route>
    </Routes>
  );
}

export default App;
