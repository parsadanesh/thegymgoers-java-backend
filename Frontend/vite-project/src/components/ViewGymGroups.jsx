import axios from "axios";
import { useEffect, useState } from "react";
import ViewGroup from "./ViewGroup";

const ViewGymGroups = (props) => {

    const [display, setDisplay] = useState(false);
    const [arr, setArr] = useState([]);

    const createView = () => {
        if (props.groups?.length > 0) {
            let arr1 = [];
            

            props.groups.forEach(group => {
                
                arr1.push(<ViewGroup name={group.groupName} members={group.members} />)
            });

            
            setDisplay(true);
            setArr(arr1);
        }
    };

    

    useEffect(() => {
        createView();
    }, [])

    return (
        <div className="d-flex justify-content-center">
            <br/>
            {display &&
                <div>
                    {arr}
                    {/* <h4>Here are your current groups:</h4>
                    <br/>
                    <ul className="list-group mt-3 d-flex flex-row">
                        <li className="list-group-item"> <strong>{props.group[0].name}</strong></li>
                        <li className="list-group-item">
                            Members: <strong>{props.group[0].members[0]}</strong>
                            
                        </li>
                    </ul> */}
                </div>
            }
            {!display &&
                <div className="mt-3">
                    <h4>You have no gymGroups to display</h4>
                </div>
            }
            
        </div>
    )

}

export default ViewGymGroups;