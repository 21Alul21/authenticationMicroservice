import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../styling/HamburgerMenu.css";


const HamburgerMenu: React.FC = () => {
   const [open, setOpen] = useState<boolean>(false);
   const navigate = useNavigate();
   const handleClick = () => {
   setOpen(!open);
  }

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    navigate("/admin/login");
  }
  

  return (
    <>
   <button style={{position: "absolute", top: "20px", left: "20px"}}
   className={`hamburger ${open? "open": ""}`} 
   onClick={handleClick} aria-label="Menu">

    <span/>
    <span/>    
    <span/>        
   </button>


   {open && (
    <nav style={{width:"80%", position:"absolute", top:"60px", left:"0", backgroundColor:"#f0f8ff"}}>
        <li style={{display:"flex", flexDirection:"column", alignItems: "flex-start", padding: "20px", gap: "10px"}}>
          
          <ul>
            <li style={{listStyle: "none"}}>  
              <Link style={{}} to="/admin/manage-user">View and manage users</Link>
            </li>
          </ul>
          <ul>Update your profile</ul>
          <ul style={{cursor: "pointer"}} onClick={handleLogout}>Log out</ul>  
        </li>
    </nav>
   )}

  </>
  );

};

export default HamburgerMenu;