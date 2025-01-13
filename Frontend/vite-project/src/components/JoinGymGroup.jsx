import axios from "axios";
import { useRef, useState } from "react";

const JoinGymGroup = (props) => {

    const groupName = useRef();;
    const [setupMessage, setSetupMessage] = useState("");

    const joinGroup = async () => {
        try {            
            const res = await axios.post("http://localhost:3000/joinGroup", { params: { userEmail: props.user.email, gymGroupName: groupName.current.value } });
            if (res.status === 201) {
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

