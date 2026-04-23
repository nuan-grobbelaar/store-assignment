import { NavLink, useLocation } from "react-router-dom";
import CartButton from "../cart/CartButton.tsx";

function Navbar() {
  const location = useLocation();
  return (
    <nav className="navbar">
      <h1 className="navbar__title">STORE.NUAN.DEV</h1>
      <div className="navbar__links">
        {location.pathname == "/orders" ? (
          <NavLink to="/" end className="navbar__link">
            Store
          </NavLink>
        ) : (
          <NavLink to="/orders" className="navbar__link">
            Orders
          </NavLink>
        )}
      </div>
      <div className="navbar__cart">
        <CartButton />
      </div>
    </nav>
  );
}

export default Navbar;
