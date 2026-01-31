
import Header from "../components/Header"
import DeleteIcon from "../assets/DeleteIcon.png";
import { useEffect, useState } from "react";
import DeleteUser from "./DeleteUser";
import axios from "axios";
import { useNavigate } from "react-router-dom";


function ManageUsers(){
    useEffect(() =>{
        axios.get("http://localhost:8080/api/v1/users/list-users", {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
            },
        })
        .then((response) => {
            setRegisteredUsers(response.data);
        })
        .catch((err) => {
            console.error("Error fetching users:", err);
            alert("Error occurred while fetching users");
        })
        .finally(() => {
            setLoading(false);
        });
    }, []        
    );


    const [userz, setUsers] = useState()
    const [userDeletedState, setDeleteState] = useState<boolean>(false);
    const [userId, setId] = useState<string>("");
    const navigate = useNavigate();
    const handleClick = () => navigate("/admin/login");
    const [registeredUsers, setRegisteredUsers] = useState<any[]>([]);
    const [loading, setLoading] = useState(true);

    function toggleActive(id: string): void {
        // Find the current user to determine if we need to activate or deactivate
        const currentUser = registeredUsers.find(user => user.id === id);
        if (!currentUser) return;

        const newIsActive = !currentUser.isActive;
        const endpoint = newIsActive ? "activate-user" : "deactivate-user";
        const url = `http://localhost:8080/api/v1/users/${endpoint}/${id}`;
        

        // Send request to backend
        axios.post(url, {}, {
           headers: {
               Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
           },
            })
            .then((response) => {
                // Update local state only if backend request succeeds
                setRegisteredUsers(prevUsers => prevUsers.map(user => {
                    if (user.id === id) {
                        return { ...user, isActive: newIsActive };
                    }
                    return user;
                }));
                console.log(`User ${newIsActive ? 'activated' : 'deactivated'} successfully`);
            })
            .catch((error) => {
                console.error(`Error ${newIsActive ? 'activating' : 'deactivating'} user:`, error);
                alert(`Failed to ${newIsActive ? 'activate' : 'deactivate'} user. Please try again.`);
            });
    };
        

    function handleDelete(): void {
        
        const url = `http://localhost:8080/api/v1/users/delete-user/${userId}`;
        axios.post(url, {}, {
           headers: {
               Authorization: `Bearer ${localStorage.getItem("accessToken")}`,
           },
            })
            .then((response) => {
                // Remove user from local state
                setRegisteredUsers(prevUsers => prevUsers.filter(user => user.id !== userId));
                setDeleteState(false);
                setId("");
                console.log("User deleted successfully");
            })
            .catch((error) => {
                console.error("Error deleting user:", error);
                alert("Failed to delete user. Please try again.");
            });
    }


    function handleCancel(): void {
        setDeleteState(false);
        setId("");
    }


    return(
        <>
        <Header onClick={handleClick}/>
        

        <div style={{margin: "10px", width: "100%", padding: "10px", border: "solid, white"}}>
            <br />
            <br />
            <br />
            <h3>Users</h3>

            <br />

            {userDeletedState && (
                <DeleteUser 
                handleDelete={handleDelete}
                handleCancel={handleCancel}
                />
            )}

            <div>
                {loading ? (
                    <div>Loading users...</div>
                ) : (
                    registeredUsers.map((user) => (
                        <div className="item-card" style={{ padding: "10px", display: "flex", alignItems: "center", justifyContent: "space-between", position: "relative"}} key={user.id}>
                            
                            <div>{user.firstName} {user.lastName}</div>

                            {/* user status div */}
                            <div key={user.id} onClick={() => toggleActive(user.id)} style={{height: "20px", width: "60px", backgroundColor: user.isActive ? "green" : "red", cursor: "pointer", display: "flex", justifyContent: "center", alignItems: "center", color: "white", fontSize: "12px", borderRadius: "4px", position: "absolute", left: "50%", transform: "translateX(-50%)"}}>
                                {user.isActive ? "Active" : "Inactive"}
                            </div>

                            {/* delete icon div */}
                            <div onClick={() => {
                                setId(user.id);
                                setDeleteState(true);
                            }} style={{cursor: "pointer", display:"flex", alignItems:"center", justifyContent:"center", background: "pink", height: "30px", width: "30px", borderRadius: "15px"}}>
                                <img style={{height: "20px", width: "20px"}} src={DeleteIcon} alt="delete icon"/>
                            </div>
                        </div>
                    ) 
                    )
                )}
            </div>
    
        </div>
        </>

        
    )

}

export default ManageUsers;