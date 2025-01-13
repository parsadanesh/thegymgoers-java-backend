import axios from "axios";
import { useEffect, useRef, useState } from "react";

const CreateGymGroup = (props) => {

    const groupName = useRef();

    const [tempGroup, setTempGroup] = useState({ name: "", admin: "" });
    const [setupMessage, setSetupMessage] = useState(""); 

    const handleSubmit = (e) => {
        e.preventDefault();
        setTempGroup({
            name: groupName.current.value,
            admin: props.user.email
        })
        
        
    };

    useEffect(() => {
        if (tempGroup.name.length > 0 && tempGroup.admin.length > 0) {
            createGroup();
        }
    }, [tempGroup])

    const createGroup = async (e) => {
        const email = props.user.email
        try {
            const res = await axios.post("http://localhost:3000/createGroup", { params: { user: email, newGroup: tempGroup } })
            if (res.status === 201) {
                setSetupMessage("GymGroup Successfully Created");
                setTimeout(() => setSetupMessage(""), 3000);
                groupName.current.value = "";
            }
        } catch (e) {
            console.log(e.response?.date);
            setSetupMessage("Could Not Create GymGroup Try Again Later");
            setTimeout(() => setSetupMessage(""), 5000);
            groupName.current.value = "";
        }
    }

    useEffect(() => {
        // createGroup();
    }, [tempGroup]);
    

    return (
        <div>
            {setupMessage && <div className="alert alert-success mt-3">{setupMessage}</div>}
            <div className="container mt-3 d-flex justify-content-center align-items-center">
                <form onSubmit={handleSubmit}>
                    <div className="row justify-content-center">
                        <div className="form-group">
                            <label htmlFor="GymGroupName">GymGroup Name:</label>
                            
                            <input type="text" className="form-control" id="GroupName" placeholder="Enter GymGroup Name" ref={groupName} />
                        </div>
                    
                    </div>
                    <button type="submit" className="btn  btn-primary mt-5">Create GymGroup</button>
                </form>
            </div>
        </div>
    );

};

export default CreateGymGroup;