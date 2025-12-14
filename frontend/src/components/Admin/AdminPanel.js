// frontend/src/components/Admin/AdminPanel.js
import React, { useState, useEffect } from "react";
import { sweetAPI } from "../../services/api";
import "./Admin.css";

const AdminPanel = () => {
  const [sweets, setSweets] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [editingSweet, setEditingSweet] = useState(null);
  const [formData, setFormData] = useState({
    name: "",
    category: "",
    price: "",
    stock: "",
  });

  useEffect(() => {
    fetchSweets();
  }, []);

  const fetchSweets = async () => {
    try {
      const response = await sweetAPI.getAll();
      setSweets(response.data);
    } catch (err) {
      alert("Failed to load sweets");
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const sweetData = {
      ...formData,
      price: parseFloat(formData.price),
      stock: parseInt(formData.stock),
    };

    try {
      if (editingSweet) {
        await sweetAPI.update(editingSweet.id, sweetData);
        alert("Sweet updated successfully!");
      } else {
        await sweetAPI.create(sweetData);
        alert("Sweet created successfully!");
      }

      resetForm();
      fetchSweets();
    } catch (err) {
      alert(err.response?.data?.message || "Operation failed");
    }
  };

  const handleEdit = (sweet) => {
    setEditingSweet(sweet);
    setFormData({
      name: sweet.name,
      category: sweet.category || "",
      price: sweet.price.toString(),
      stock: sweet.stock.toString(),
    });
    setShowForm(true);
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Are you sure you want to delete this sweet?")) {
      return;
    }

    try {
      await sweetAPI.delete(id);
      alert("Sweet deleted successfully!");
      fetchSweets();
    } catch (err) {
      alert(err.response?.data?.message || "Delete failed");
    }
  };

  const resetForm = () => {
    setFormData({
      name: "",
      category: "",
      price: "",
      stock: "",
    });
    setEditingSweet(null);
    setShowForm(false);
  };

  return (
    <div className="admin-panel">
      <div className="admin-header">
        <h1>🛠️ Admin Panel</h1>
        <button onClick={() => setShowForm(!showForm)} className="btn-add">
          {showForm ? "Cancel" : "+ Add Sweet"}
        </button>
      </div>

      {showForm && (
        <div className="form-card">
          <h2>{editingSweet ? "Edit Sweet" : "Add New Sweet"}</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-row">
              <div className="form-group">
                <label>Sweet Name *</label>
                <input
                  type="text"
                  name="name"
                  value={formData.name}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-group">
                <label>Category</label>
                <input
                  type="text"
                  name="category"
                  value={formData.category}
                  onChange={handleChange}
                  placeholder="e.g., Traditional"
                />
              </div>
            </div>

            <div className="form-row">
              <div className="form-group">
                <label>Price (₹) *</label>
                <input
                  type="number"
                  name="price"
                  value={formData.price}
                  onChange={handleChange}
                  min="0"
                  step="0.01"
                  required
                />
              </div>

              <div className="form-group">
                <label>Stock *</label>
                <input
                  type="number"
                  name="stock"
                  value={formData.stock}
                  onChange={handleChange}
                  min="0"
                  required
                />
              </div>
            </div>

            <div className="form-actions">
              <button type="submit" className="btn-submit">
                {editingSweet ? "Update Sweet" : "Create Sweet"}
              </button>
              <button type="button" onClick={resetForm} className="btn-cancel">
                Cancel
              </button>
            </div>
          </form>
        </div>
      )}

      <div className="sweets-table">
        <table>
          <thead>
            <tr>
              <th>ID</th>
              <th>Name</th>
              <th>Category</th>
              <th>Price</th>
              <th>Stock</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {sweets.map((sweet) => (
              <tr key={sweet.id}>
                <td>{sweet.id}</td>
                <td>{sweet.name}</td>
                <td>{sweet.category || "-"}</td>
                <td>₹{sweet.price}</td>
                <td>{sweet.stock}</td>
                <td>
                  <button
                    onClick={() => handleEdit(sweet)}
                    className="btn-edit"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => handleDelete(sweet.id)}
                    className="btn-delete"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        {sweets.length === 0 && (
          <p className="no-data">
            No sweets available. Add one to get started!
          </p>
        )}
      </div>
    </div>
  );
};

export default AdminPanel;
