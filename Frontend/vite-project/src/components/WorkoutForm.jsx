import { useState, useEffect } from "react";
import CardioTraining from "./CardioTraining";
import WeightTraining from "./WeightTraining";
import axios from "axios";

const WorkoutForm = (props) => {
    // create some hooks for a workout object
    const token = localStorage.getItem('token');

    const [selectedExercise, setSelectedExercise] = useState(null);
    const [selectedOptionText, setSelectedOptionText] = useState("");
    const [workout, setWorkout] = useState({ exercises: [] });
    const [exercises, setExercises] = useState([]);
    const [workRef, setWorkRef] = useState(false);
    const [successMessage, setSuccessMessage] = useState(''); 
    const [cardioTraining, setCardioTraining] = useState({ name: "", duration: "" });
    const [weightTraining, setWeightTraining] = useState({ name: "", reps: "", sets: "", weight: "" });
    
    const logWorkout = async (e) => {
        try {

            const res = await axios.post(`${import.meta.env.VITE_APP_GYMBACKEND}/users/${props.user.username}/workouts`, 
                {
                    exercises: exercises,
                    dateCreated: ""
                },
                {
                    headers: {
                        Authorization: token
                    }
                }
            );
            if (res.status === 200) console.log("success");
        } catch (e) {
                if (e.response && e.response.data) {
                    console.log(e.response.data.message);
                } else {
                    // Handle the error without a response (e.g., network error, or request was not sent)
                    console.log(e.message);
    }
        }
    }
    
    //method that created a new workout using the states
    useEffect(() => {
        if (exercises.length > 0) {
            setWorkRef(true);
        }
        
    }, [exercises])


    useEffect(() => {
        if (weightTraining.name || weightTraining.reps || weightTraining.sets || weightTraining.weight) {
            setExercises(prevWorkout => [...prevWorkout, weightTraining]);
        }
    }, [weightTraining]);

    useEffect(() => {
        if (cardioTraining.name || weightTraining.duration ) {
            setExercises(prevWorkout => [...prevWorkout, cardioTraining]);
        }
    }, [cardioTraining]);

    const handleSubmit = (e) => {
        e.preventDefault();
        setWorkout({ exercises: exercises });
        setSuccessMessage('Your workout has been successfully submitted!');
        setTimeout(() => {
            setSuccessMessage('');
        }, 3000);

    }

    useEffect(() => {
        workout.exercises.length>0 && logWorkout();
    }, [workout])

    const handleExerciseChange = (e) => {
        e.preventDefault();
        setSelectedOptionText(e.target.options[e.target.selectedIndex].text);
        setSelectedExercise(Number(event.target.value));
    }

    return (
        <div className="container container-md mt-3 bg-light">
            <h1>Welcome {props.user.email}</h1>
            <form onSubmit={handleSubmit}>
            <div className="row d-flex justify-content-center">
                <div className=" col-12 col-sm-8 col-md-6 col-lg-4">
                    <select className="form-control" onChange={handleExerciseChange}>
                        <option value="-1">Select an exercise</option>
                        <option value="0">Flat DB Chest Press</option>
                        <option value="0">Incline DB Chest Press</option>
                        <option value="0">Flat Barbell Chest Press</option>
                        <option value="0">Incline Barbell Chest Press</option>
                        <option value="0">Squat</option>
                        <option value="0">Lat Pulldown</option>
                        <option value="0">DB Overhead Press</option>
                        <option value="0">Barbell Row</option>
                        <option value="0">Chest-Supported Row</option>
                        <option value="0">Lateral Raise</option>
                        <option value="0">Leg Extension</option>
                        <option value="0">Leg Press</option>
                        <option value="0">DB Curl</option>
                        <option value ="1">Cardio</option>
                    </select>
                    </div> 
                </div>
            <button type="submit" className="btn btn-primary mt-3" disabled={!workRef}>Add a new workout</button>

            </form>
            {successMessage && <div className="alert alert-success mt-3">{successMessage}</div>}
        
            {selectedExercise === 1 &&
                // <h1>HI</h1>
                <CardioTraining selectedOptionText={selectedOptionText} setCardioTraining={setCardioTraining} />
            }

            {selectedExercise === 0 &&
                <WeightTraining selectedOptionText={selectedOptionText} setWeightTraining={setWeightTraining} />
            }
            

                
            


        </div>
    )

}

export default WorkoutForm