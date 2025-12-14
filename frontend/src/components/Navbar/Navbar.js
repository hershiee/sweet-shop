// frontend/src/components/Navbar/Navbar.js
import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { authService } from "../../services/auth";
import "./Navbar.css";

const Navbar = () => {
  const navigate = useNavigate();
  const user = authService.getCurrentUser();
  const isAuthenticated = authService.isAuthenticated();
  const isAdmin = authService.isAdmin();

  const handleLogout = () => {
    authService.logout();
    navigate("/login");
  };

  return (
    <nav className="navbar">
      <div className="navbar-content">
        <Link to="/" className="navbar-brand">
          🍬 Sweet Shop
        </Link>

        <div className="navbar-links">
          {isAuthenticated ? (
            <>
              <Link to="/dashboard" className="nav-link">
                Dashboard
              </Link>

              {isAdmin && (
                <Link to="/admin" className="nav-link">
                  Admin Panel
                </Link>
              )}

              <div className="user-info">
                <span className="user-name">{user?.name}</span>
                <span className="user-role">{user?.role}</span>
              </div>

              <button onClick={handleLogout} className="btn-logout">
                Logout
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="nav-link">
                Login
              </Link>
              <Link to="/register" className="btn-register">
                Register
              </Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
