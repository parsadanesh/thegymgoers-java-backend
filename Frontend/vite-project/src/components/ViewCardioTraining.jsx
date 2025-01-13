const ViewCardioTraining = ({name, duration, onDelete}) => {
    


    return (
        <ul className="list-group mt-3 d-flex flex-row">
            <li className="list-group-item"> <strong>{name}</strong></li>
            <li className="list-group-item">Duration: <strong>{duration}</strong></li>
            <li className="list-group-item">
                <button className="btn btn-danger" onClick={onDelete} >Delete</button>
            </li>
        </ul>
        
    )
}

export default ViewCardioTraining;