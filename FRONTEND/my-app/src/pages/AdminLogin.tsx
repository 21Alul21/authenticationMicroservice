import { type FormEvent, useState } from "react";
import Header from "../components/Header";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {toast} from "react-toastify";



function AdminLogin() {
    const [email, setEmail] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const [error, setError] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);

    const navigate = useNavigate();


    async function handleSubmit(e: FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setError(null);
        setLoading(true);
        try {
           const response = await axios.post("http://localhost:8080/api/v1/auth/login", {
            email,
            password,
           });

           
           if(response.status == 200){
            toast.success("Login Successful, redirecting to admin dashboard");
            localStorage.setItem("accessToken", response.data.accessToken);
            localStorage.setItem("userRole", response.data.role);
            navigate("/admin/dashboard");
           }
            
        } catch (err: any) {
            const message = err?.response?.data?.message ?? err?.message ?? "Login failed";
            setError(message);
        } finally {
            setLoading(false);
        }
    }


    const handleClick = () => navigate("/admin/login");
    
    const handleRegister = () => {
        navigate("/register");
    }    

    const handleRegisterAdmin = () => {
        navigate("/register-admin")
    }

    return <>
        <Header onClick={handleClick}/>

        
        
        <div style={{backgroundColor: "rgba(243, 243, 248, 1)", width:"80%", maxWidth: "500px", position: "absolute", top: "50%", left: "50%",
            transform: "translate(-50%, -50%)"
        }}>

            <h1 style={{textAlign:"center", marginTop: "20px"}}>Admin Login Page</h1> <br />
            
            <form style={{backgroundColor: "rgba(243, 243, 248, 1)", margin: "auto", width:"70%", display:"flex", flexDirection: "column"}} onSubmit={handleSubmit}>
                
                <br />
                {error && <p style={{ color: "red" }}>{error}</p>}

                <div style={{}}>
                    <label style={{}} htmlFor="email">Email</label><br></br>
                    <input style={{padding: "20px", width:"100%", height: "30px", }}
                        id="email"
                        name="email"
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>

                <div>
                    <label style={{}} htmlFor="password">Password</label> <br></br>
                    <input style={{outline: "pink", padding: "20px", width:"100%", height: "30px", marginBottom:"20px"}}
                        id="password"
                        name="password"
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>

                <button style={{backgroundColor:"#465088ff", color:"white"}} type="submit" disabled={loading}>
                    {loading ? "Submitting..." : "Submit"}
                </button>

                <br />
                <br/>

                <button onClick={handleRegister} style={{backgroundColor: "green", color: "white", marginBottom: "20px" }}>Register as User</button>
                <button onClick={handleRegisterAdmin} style={{backgroundColor: "red", color: "white", marginBottom: "20px" }}>Register as Admin</button>
                <button style={{marginBottom: "20px"}}>--Login with GOOGLE--</button>
            </form>
  

        </div>
        
    </>
}

export default AdminLogin;