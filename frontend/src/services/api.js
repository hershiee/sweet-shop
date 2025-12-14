// frontend/src/services/api.js
import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

// Add token to requests if it exists
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Auth APIs
export const authAPI = {
  register: (data) => api.post("/api/auth/register", data),
  login: (data) => api.post("/api/auth/login", data),
};

// Sweet APIs
export const sweetAPI = {
  getAll: () => api.get("/api/sweets"),
  getById: (id) => api.get(`/api/sweets/${id}`),
  create: (data) => api.post("/api/sweets", data),
  update: (id, data) => api.put(`/api/sweets/${id}`, data),
  delete: (id) => api.delete(`/api/sweets/${id}`),
};

// Order APIs
export const orderAPI = {
  create: (data) => api.post("/api/orders", data),
  getById: (id) => api.get(`/api/orders/${id}`),
};

export default api;
