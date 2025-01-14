import { useRef, useState } from "react"

const WeightTraining = (props) => {

    const [setRef, setSetRef] = useState("");
    const [repRef, setRepRef] = useState("");
    const [weightRef, setWeightRef] = useState("");

    
    
    // Handlers for input changes
    const handleSetsChange = (e) => setSetRef(e.target.value);
    const handleRepsChange = (e) => setRepRef(e.target.value);
    const handleWeightChange = (e) => setWeightRef(e.target.value);

    const submitExercise = (e) => {
        e.preventDefault();
        if (props.selectedOptionText && repRef && setRef && weightRef) {
            props.setWeightTraining({
                exerciseName: props.selectedOptionText,
                reps: repRef,
                sets: setRef,
                weight: weightRef
            });
        }
        setSetRef("");
        setRepRef("");
        setWeightRef("");
    }

    return (
        <form onSubmit={submitExercise}>
            <div className="form-row d-flex flex-column align-items-center mt-3">
                <div className="form-group col-md-4 mt-3">
                    <label className="fw-bold" htmlFor="inputEmail4">Sets</label>
                    <input type="number" className="form-control mt-1" id="inputEmail4" placeholder="Number of sets" value={setRef} onChange={handleSetsChange}/>
                </div>
                <div className="form-group col-md-4 mt-3">
                    <label className="fw-bold" htmlFor="inputPassword4">Repetitions</label>
                    <input type="number" className="form-control mt-1" id="inputPassword4" placeholder="Number of reps" value={repRef} onChange={handleRepsChange}/>
                </div>
                <div className="form-group col-md-4 mt-3">
                    <label className="fw-bold" htmlFor="inputWeight4">Weight</label>
                    <input type="number" className="form-control mt-1 " id="inputWeight4" placeholder="Weight in kg" value={weightRef} onChange={handleWeightChange}/>
                </div>
            </div>
            <button type="submit" className="btn btn-primary mt-3" disabled={!setRef || !repRef || !weightRef}>Log Exercise</button>
        </form>
    )
    
}

export default WeightTraining