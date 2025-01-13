import { useState } from "react";

const CardioTraining = (props) => {
    const [durationRef, setDurationRef] = useState("");

    const handleDurationChange = (e) => setDurationRef(e.target.value);

    const submitExercise = (e) => {
        e.preventDefault();

        if (props.selectedOptionText && durationRef) {
            props.setCardioTraining({
                name: props.selectedOptionText,
                duration: durationRef
            });
        }
        setDurationRef("");
    }

    return (
        <form onSubmit={submitExercise}>
            <div className="form-row d-flex flex-column align-items-center mt-3">
                <div className="form-group col-md-4 mt-3">
                    <label className="fw-bold" htmlFor="inputTime4">Time</label>
                    <input type="number" className="form-control mt-1" id="inputTime4" placeholder="Minutes" value={durationRef} onChange={handleDurationChange}/>
                </div>
            </div>
            <button type="submit" className="btn btn-primary mt-3" disabled={!durationRef}>Log Exercise</button>
        </form>
    )

}

export default CardioTraining