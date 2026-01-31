type Props = {
    onClick: () => void;
};


function Header({onClick} : Props){
return(
<header onClick={onClick} style={{display:"flex", justifyContent:"center", position:"fixed", top:"0", left:"0", padding:"20px", backgroundColor:"black", width:"100%", color:"white", cursor:"pointer"}}>Authentication Microservice Application</header>
)
}

export default Header;