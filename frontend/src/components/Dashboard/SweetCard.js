// frontend/src/components/Dashboard/SweetCard.js
import React, { useState } from "react";
import "./Dashboard.css";

const SweetCard = ({ sweet, onPurchase }) => {
  const [quantity, setQuantity] = useState(1);
  const [purchasing, setPurchasing] = useState(false);

  const handlePurchase = async () => {
    if (quantity <= 0) {
      alert("Please enter a valid quantity");
      return;
    }

    if (quantity > sweet.stock) {
      alert(`Only ${sweet.stock} items available`);
      return;
    }

    setPurchasing(true);
    await onPurchase(sweet.id, quantity);
    setPurchasing(false);
    setQuantity(1);
  };

  const isOutOfStock = sweet.stock === 0;

  return (
    <div className={`sweet-card ${isOutOfStock ? "out-of-stock" : ""}`}>
      <div className="sweet-icon">🍬</div>
      <h3>{sweet.name}</h3>

      {sweet.category && (
        <span className="category-badge">{sweet.category}</span>
      )}

      <div className="sweet-details">
        <div className="price">₹{sweet.price}</div>
        <div className={`stock ${isOutOfStock ? "text-danger" : ""}`}>
          Stock: {sweet.stock}
        </div>
      </div>

      {!isOutOfStock ? (
        <div className="purchase-section">
          <div className="quantity-control">
            <button
              onClick={() => setQuantity(Math.max(1, quantity - 1))}
              className="qty-btn"
            >
              -
            </button>
            <input
              type="number"
              value={quantity}
              onChange={(e) =>
                setQuantity(Math.max(1, parseInt(e.target.value) || 1))
              }
              min="1"
              max={sweet.stock}
              className="qty-input"
            />
            <button
              onClick={() => setQuantity(Math.min(sweet.stock, quantity + 1))}
              className="qty-btn"
            >
              +
            </button>
          </div>

          <button
            onClick={handlePurchase}
            disabled={purchasing}
            className="btn-purchase"
          >
            {purchasing
              ? "Processing..."
              : `Buy ₹${(sweet.price * quantity).toFixed(2)}`}
          </button>
        </div>
      ) : (
        <button className="btn-purchase" disabled>
          Out of Stock
        </button>
      )}
    </div>
  );
};

export default SweetCard;
