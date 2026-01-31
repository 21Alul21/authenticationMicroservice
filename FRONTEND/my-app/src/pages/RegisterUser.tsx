import Header from "../components/Header";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import axios from "axios";
import { toast } from "react-toastify";

function RegisterUser() {
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: ""
    });
    const [isLoading, setIsLoading] = useState(false);

    const navigate = useNavigate();
    const handleHeaderClick = () => navigate("/admin/login");

    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (formData.password !== formData.confirmPassword) {
            alert("Passwords do not match");
            return;
        }

        setIsLoading(true);

        // Prepare data for backend (exclude confirmPassword)
        const registrationData = {
            email: formData.email,
            password: formData.password,
            firstName: formData.firstName,
            lastName: formData.lastName
        };

        // Send registration request to backend
        axios.post("http://localhost:8080/api/v1/auth/register", registrationData)
            .then((response) => {
                console.log("Registration successful:", response.data);
                //alert("Registration successful! Please login with your credentials.");
                navigate("/admin/login");
                toast.success("user registration successful");
            })
            .catch((error) => {
                console.error("Registration failed:", error);
                if (error.response && error.response.data) {
                    toast.error(`Registration failed: ${error.response.data.message || 'Please try again.'}`);
                } else {
                    toast.error("Registration failed. Please check your connection and try again.");
                }
            })
            .finally(() => {
                setIsLoading(false);
            });
    };

    return (
        <>
            <Header onClick={handleHeaderClick} />
            <div style={{ marginTop: "80px", padding: "20px", maxWidth: "400px", margin: "80px auto 0", backgroundColor: "#f9f9f9", borderRadius: "8px", boxShadow: "0 0 10px rgba(0,0,0,0.1)" }}>
                <h2 style={{ textAlign: "center", marginBottom: "20px" }}>Register as User</h2>
                <form onSubmit={handleSubmit}>
                    <div style={{ marginBottom: "15px" }}>
                        <label htmlFor="firstName" style={{ display: "block", marginBottom: "5px" }}>First Name:</label>
                        <input
                            type="text"
                            id="firstName"
                            name="firstName"
                            value={formData.firstName}
                            onChange={handleChange}
                            required
                            style={{ width: "100%", padding: "8px", border: "1px solid #ccc", borderRadius: "4px" }}
                        />
                    </div>
                    <div style={{ marginBottom: "15px" }}>
                        <label htmlFor="lastName" style={{ display: "block", marginBottom: "5px" }}>Last Name:</label>
                        <input
                            type="text"
                            id="lastName"
                            name="lastName"
                            value={formData.lastName}
                            onChange={handleChange}
                            required
                            style={{ width: "100%", padding: "8px", border: "1px solid #ccc", borderRadius: "4px" }}
                        />
                    </div>
                    <div style={{ marginBottom: "15px" }}>
                        <label htmlFor="email" style={{ display: "block", marginBottom: "5px" }}>Email:</label>
                        <input
                            type="email"
                            id="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            style={{ width: "100%", padding: "8px", border: "1px solid #ccc", borderRadius: "4px" }}
                        />
                    </div>
                    <div style={{ marginBottom: "15px" }}>
                        <label htmlFor="password" style={{ display: "block", marginBottom: "5px" }}>Password:</label>
                        <input
                            type="password"
                            id="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            style={{ width: "100%", padding: "8px", border: "1px solid #ccc", borderRadius: "4px" }}
                        />
                    </div>
                    <div style={{ marginBottom: "20px" }}>
                        <label htmlFor="confirmPassword" style={{ display: "block", marginBottom: "5px" }}>Confirm Password:</label>
                        <input
                            type="password"
                            id="confirmPassword"
                            name="confirmPassword"
                            value={formData.confirmPassword}
                            onChange={handleChange}
                            required
                            style={{ width: "100%", padding: "8px", border: "1px solid #ccc", borderRadius: "4px" }}
                        />
                    </div>
                    <button 
                        type="submit" 
                        disabled={isLoading}
                        style={{ 
                            width: "100%", 
                            padding: "10px", 
                            backgroundColor: isLoading ? "#cccccc" : "#4CAF50", 
                            color: "white", 
                            border: "none", 
                            borderRadius: "4px", 
                            cursor: isLoading ? "not-allowed" : "pointer", 
                            fontSize: "16px" 
                        }}
                    >
                        {isLoading ? "Registering..." : "Register"}
                    </button>
                </form>
            </div>
        </>
    );
}

export default RegisterUser;