import './App.css'
import AdminDashboard from './pages/AdminDashboard';
import AdminLogin from './pages/AdminLogin'
import DeleteUser from './pages/DeleteUser';
import ManageUsers from './pages/ManageUsers';
import RegisterUser from './pages/RegisterUser';

import { Routes, Route, Navigate } from "react-router-dom";
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import RegisterAdmin from './pages/RegisterAdmin';



function App() {

  return (
    <>
    <ToastContainer position="top-right" autoClose={3000}/>
    <Routes>
      <Route path="/" element={<Navigate to="/admin/login" />} />
      <Route path="/admin/login" element={<AdminLogin/>} />
      <Route path="/admin/dashboard" element={<AdminDashboard/>} />
      <Route path="/admin/manage-user" element={<ManageUsers/>} />
      <Route path="/admin/delete-user" element={<DeleteUser/>} />
      <Route path="/register" element={<RegisterUser/>} />
      <Route path="/register-admin" element={<RegisterAdmin/>} />
    </Routes>
    </>
  )
}

export default App;

