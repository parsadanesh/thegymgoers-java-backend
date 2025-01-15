import { useEffect, useState } from "react";
import axios from "axios";
import MemberView from "./MemberView";

const ViewGroup = (props) => {

    const [groupMembers, setGroupMembers] = useState([]);
    const [name, setName] = useState("");
    const [loaded, setLoaded] = useState(false);
    const [memView, setMemView] = useState([]);

    const getMembers = async (e) => {
        setGroupMembers(props.members);
    //     try {
    //         const groupName = props.name;
    //         const res = await axios.get("http://localhost:3000/getMembers", { params: {groupName} });

    //         setName(res.data.email)
            
            
    //         if (res) {
    //             setGroupMembers(res.data);
    //         }
            
    //     } catch (e) {
    //         console.log(e.message);
    //     }
    }

    const createMembersView = () => {
        let arr = [];
        groupMembers.forEach(member => {
            console.log(member);
            

            arr.push(<MemberView name={props.name} member={member} />)
        });
        if (arr.length > 0) {
            setMemView(arr);
            setLoaded(true);
        }
    }

    useEffect(() => {
        getMembers();
    }, [])

    useEffect(() => {
        createMembersView();
    }, [groupMembers])

    return (
        <ul className="list-group mt-3 d-flex flex-row">
            <li className="list-group-item"> <strong>{`Group Name: ${props.name}`}</strong></li>
            <li className="list-group-item">
                {loaded && <>
                    Members: {memView}
                </>}
                
                
            </li>
        </ul>
    )

}

export default ViewGroup;