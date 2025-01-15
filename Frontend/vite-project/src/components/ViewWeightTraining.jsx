
const ViewWeightTraining = (props) => {
    
    const name = props.name;
    const reps = props.reps;
    const sets = props.sets;
    const weight = props.weight;
    
    return (
        
                                             
        <ul className="list-group mt-3 d-flex flex-row">
            <li className="list-group-item"> <strong>{name}</strong></li>
            <li className="list-group-item">Reps: <strong>{reps}</strong></li>
            <li className="list-group-item">Sets: <strong>{sets}</strong></li>
            <li className="list-group-item">Weight (kg): <strong>{weight}</strong></li>
            {/* <li className="list-group-item">
                <button className="btn btn-danger" onClick={props.onDelete} >Delete</button>
            </li> */}
        </ul>

        
    )
}

export default ViewWeightTraining;