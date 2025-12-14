// frontend/src/components/Dashboard/Dashboard.js
import React, { useState, useEffect } from "react";
import { sweetAPI, orderAPI } from "../../services/api";
import { authService } from "../../services/auth";
import SweetCard from "./SweetCard";
import "./Dashboard.css";

const Dashboard = () => {
  const [sweets, setSweets] = useState([]);
  const [filteredSweets, setFilteredSweets] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [categoryFilter, setCategoryFilter] = useState("All");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const user = authService.getCurrentUser();

  useEffect(() => {
    fetchSweets();
  }, []);

  useEffect(() => {
    filterSweets();
  }, [searchTerm, categoryFilter, sweets]);

  const fetchSweets = async () => {
    try {
      setLoading(true);
      const response = await sweetAPI.getAll();
      setSweets(response.data);
      setFilteredSweets(response.data);
    } catch (err) {
      setError("Failed to load sweets");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const filterSweets = () => {
    let filtered = sweets;

    // Filter by search term
    if (searchTerm) {
      filtered = filtered.filter((sweet) =>
        sweet.name.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    // Filter by category
    if (categoryFilter !== "All") {
      filtered = filtered.filter((sweet) => sweet.category === categoryFilter);
    }

    setFilteredSweets(filtered);
  };

  const handlePurchase = async (sweetId, quantity) => {
    try {
      const response = await orderAPI.create({
        sweetId: sweetId,
        quantity: quantity,
      });

      alert(`Order placed successfully! Total: ₹${response.data.totalAmount}`);
      fetchSweets(); // Refresh to update stock
    } catch (err) {
      alert(err.response?.data?.message || "Purchase failed");
    }
  };

  const categories = [
    "All",
    ...new Set(sweets.map((s) => s.category).filter(Boolean)),
  ];

  if (loading) {
    return <div className="loading">Loading sweets... 🍬</div>;
  }

  return (
    <div className="dashboard">
      <div className="dashboard-header">
        <h1>🍬 Sweet Shop</h1>
        <p>Welcome, {user?.name}!</p>
      </div>

      <div className="filters">
        <input
          type="text"
          placeholder="🔍 Search sweets..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="search-input"
        />

        <select
          value={categoryFilter}
          onChange={(e) => setCategoryFilter(e.target.value)}
          className="category-select"
        >
          {categories.map((cat) => (
            <option key={cat} value={cat}>
              {cat}
            </option>
          ))}
        </select>
      </div>

      {error && <div className="error">{error}</div>}

      <div className="sweets-grid">
        {filteredSweets.length === 0 ? (
          <p className="no-results">No sweets found 😞</p>
        ) : (
          filteredSweets.map((sweet) => (
            <SweetCard
              key={sweet.id}
              sweet={sweet}
              onPurchase={handlePurchase}
            />
          ))
        )}
      </div>
    </div>
  );
};

export default Dashboard;
