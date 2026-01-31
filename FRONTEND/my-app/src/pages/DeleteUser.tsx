import Header from "../components/Header";

type DeleteProps = {
    handleDelete: () => void;
    handleCancel: () => void;
}


function DeleteUser({handleDelete, handleCancel
}: DeleteProps){


    return(
        <>
        <div style={{zIndex:"2" ,backgroundColor: "#dde7f1ff", borderRadius: "10px", display: "flex", flexDirection: "column", alignItems:"center", position: "absolute", top: "50%", left:"50%", transform: "translate(-50%, -50%)",
              width: "300px", padding:"20px"}}>
            <h2>
                Are you sure you want to delete this user?
            </h2>

            <br />
            <br />
            <div style={{display: "flex", justifyContent:"space-around", width: "100%"}}>
                <button onClick={handleDelete} style={{ color: "white", backgroundColor: "red", width: "150px"}}>yes</button>
            <button onClick={handleCancel} style={{width: "150px"}}>cancel</button>

            </div>
                        
        </div>
        </>
    )
}

export default DeleteUser;