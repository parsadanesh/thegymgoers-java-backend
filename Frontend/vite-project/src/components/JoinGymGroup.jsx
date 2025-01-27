import axios from "axios";
import { useRef, useState } from "react";

const JoinGymGroup = (props) => {

    
    const token = localStorage.getItem('token');
    const groupName = useRef();;
    const [setupMessage, setSetupMessage] = useState("");

    const joinGroup = async () => {
        console.log(token);
        
        try {            
            const res = await axios.post(`${import.meta.env.VITE_APP_GYMBACKEND}/${props.user.username}/${groupName.current.value}`, 
                {},
                {
                    headers: {
                        Authorization: token
                    }
                    
                });
            if (res.status === 200) {
                console.log("joined");
                setSetupMessage(`joined GymGroup: ${groupName.current.value}`);
                setTimeout(() => setSetupMessage(""), 3000);
                groupName.current.value = "";
            }
        } catch (e) {
            console.log(e.response?.date);
            setSetupMessage(`Could Not join GymGroup: ${groupName.current.value}`);
            setTimeout(() => setSetupMessage(""), 3000);
            groupName.current.value = "";
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        joinGroup();
    }

    return (
        <div>
            {setupMessage && <div className="alert alert-success mt-3">{setupMessage}</div>}
            <div className="container mt-3 d-flex justify-content-center align-items-center">
                <form onSubmit={handleSubmit}>
                    <div className="row justify-content-center">
                        <div className="form-group">
                            <label htmlFor="GymGroupName">Join GymGroup By Name:</label>
                            <input type="text" className="form-control mt-3" id="GroupName" placeholder="Enter GymGroup Name" ref={groupName} />
                        </div>
                    </div>
                    <button type="submit" className="btn  btn-primary mt-5">Join GymGroup</button>
                </form>
            </div>
        </div>
    );

}

export default JoinGymGroup;

