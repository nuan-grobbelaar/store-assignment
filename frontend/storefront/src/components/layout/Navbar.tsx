import { NavLink } from "react-router-dom";
import CartButton from "../cart/CartButton.tsx";

function Navbar() {
  return (
    <nav className="navbar">
      <h1 className="navbar__title">STORE.NUAN.DEV</h1>
      <div className="navbar__links">
        <NavLink to="/" end className="navbar__link">Store</NavLink>
        <NavLink to="/orders" className="navbar__link">Orders</NavLink>
      </div>
      <div className="navbar__cart">
        <CartButton />
      </div>
    </nav>
  );
}

export default Navbar;
