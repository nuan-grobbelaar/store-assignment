import Navbar from "./Navbar.tsx";
import { Outlet } from "react-router-dom";
import CartSidebar from "../cart/CartSidebar.tsx";

function Layout() {
  return (
    <>
      <Navbar />
      <Outlet />
      <CartSidebar />
    </>
  );
}

export default Layout;
