// frontend/src/App.js
import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import Navbar from "./components/Navbar/Navbar";
import Login from "./components/Auth/Login";
import Register from "./components/Auth/Register";
import Dashboard from "./components/Dashboard/Dashboard";
import AdminPanel from "./components/Admin/AdminPanel";
import { authService } from "./services/auth";
import "./App.css";

// Protected Route Component
const ProtectedRoute = ({ children }) => {
  return authService.isAuthenticated() ? children : <Navigate to="/login" />;
};

// Admin Route Component
const AdminRoute = ({ children }) => {
  return authService.isAdmin() ? children : <Navigate to="/dashboard" />;
};

// Home Page Component
const Home = () => (
  <div className="home">
    <div className="hero">
      <h1>🍬 Welcome to Sweet Shop</h1>
      <p>Your one-stop destination for delicious sweets</p>
      <div className="hero-buttons">
        <a href="/register" className="btn-hero primary">
          Get Started
        </a>
        <a href="/login" className="btn-hero secondary">
          Login
        </a>
      </div>
    </div>
  </div>
);

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route
            path="/dashboard"
            element={
              <ProtectedRoute>
                <Dashboard />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin"
            element={
              <ProtectedRoute>
                <AdminRoute>
                  <AdminPanel />
                </AdminRoute>
              </ProtectedRoute>
            }
          />

          <Route path="*" element={<Navigate to="/" />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
