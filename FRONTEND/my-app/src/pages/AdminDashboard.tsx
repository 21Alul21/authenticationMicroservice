import Header from "../components/Header";
import { useNavigate } from "react-router-dom";
import HamburgerMenu from "../components/HamburgerMenu";


function AdminDashboard(){

   const navigate = useNavigate();
   const handleClick = () => navigate("/admin/login");
   const manageUsers = () => navigate("/admin/manage-user");

   const handleLogout = () => {
    localStorage.removeItem("accessToken");
       navigate("/admin/login");
   };


    return(
    <>
    <Header onClick={handleClick}/>
    <HamburgerMenu/>
    
    <div style={{textAlign: "center", width: "80%",  position: "absolute", top: "50%", left: "50%",
        transform: "translate(-50%, -50%)"
     }}>
        
        
        <h1>Welcome to your admin dashboard</h1>
        <br />
        <p>Here you can manage users, and configure settings.</p>
            <br />
            <br />
        <div>
            <h2>User Management</h2>
            <button onClick={manageUsers} style={{padding:"20px", marginRight:"20px"}}>View and update users</button>
            
        </div>

        <h4 style={{position:"absolute", top:"0", right:"5px", color:"white"}}>ADMIN</h4>

        
    </div>
    </>
    )
}

export default AdminDashboard;