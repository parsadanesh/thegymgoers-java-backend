import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"
import ViewGymGroups from "../components/ViewGymGroups";

const GymGroupPage = (props) => {

    const token = localStorage.getItem('token');
    const [gymGroups, setGymGroups] = useState([]);
    const [isLoaded, setIsLoaded] = useState(false);
    const navigate = useNavigate();


    const getGroups = async (e) => {
        try {
            const res = await axios.get(`${import.meta.env.VITE_APP_GYMBACKEND}/gymgroups/${props.user.username}`,{
                    headers: {
                        Authorization: token
                    }
                }
            );
            

            console.log(res.data);
            

            if (res) {
                setGymGroups(res.data);
                setIsLoaded(true)
            }
            
        } catch (e) {
            console.log(e.response?.data?.message);
        }
    }
    
    useEffect(() => {
        getGroups();
    }, []);
    

    const redirectToCreateGymGroup = () => {
        navigate('/createGroup'); 
    };

    const redirectToJoinGymGroup = () => {
        navigate('/joinGroup'); 
    };

    return (
        <div className="container">
            <h1>Welcome to GymGroups</h1>
            {isLoaded && <ViewGymGroups groups={gymGroups} />}
            
            <button className="btn btn-primary mt-3" onClick={redirectToCreateGymGroup}>Create Gym Group</button>
            <br />
            <button className="btn btn-primary mt-3" onClick={redirectToJoinGymGroup}>Join Gym Group</button>
        </div>
    );

};

export default GymGroupPage;