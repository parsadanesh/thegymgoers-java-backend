import axios from "axios";
import { useEffect, useState } from "react";
import ViewCardioTraining from "../components/ViewCardioTraining";
import ViewWeightTraining from "../components/ViewWeightTraining";

const ViewWorkout = (props) => {
    const [workouts, setWorkouts] = useState([]);
    const [display, setDisplay] = useState([]);
    const [consecutiveDays, setConsecutiveDays] = useState(0);

    const token = localStorage.getItem('token');

    const handleDelete = async (workoutId) => {

        console.log(token);
        
        try {     
            await axios.delete(`${import.meta.env.VITE_APP_GYMBACKEND}/users/${props.user.username}/workouts/${workoutId}`, 
                {
                    headers: {
                        Authorization: token
                    }
                });
            
            // const updatedWorkouts = workouts.map(workout => ({
            //     ...workout,
            //     exercises: workout.exercises.filter(exercise => exercise.id !== exerciseID)
            // }));
            // setWorkouts(updatedWorkouts);

        } catch (error) {
            console.error("Failed to delete exercise:", error.response?.data?.message || error.message);
        }
    };

    const getWorkouts = async (e) => {
        try {
            const res = await axios.get(`${import.meta.env.VITE_APP_GYMBACKEND}/users/${props.user.username}/workouts`, {
            headers: {
                Authorization: token
            }
            });
            
            
            setWorkouts(res.data);
        } catch (e) {
            console.log(e.response?.data?.message);
        }
    }

    const reformatDate = (dateString) => {
        const date = new Date(dateString);
        const formattedDate = date.toLocaleDateString('en-GB', {
            day: '2-digit',
            month: '2-digit',
            year: 'numeric',
        });
        return formattedDate;
    }
    
    const createViewWorkout = () => {
        // create the date range
        const sevenDaysAgo = new Date();
        sevenDaysAgo.setDate(sevenDaysAgo.getDate() - 6);

        const recentWorkouts = workouts.filter(workout => new Date(workout.dateCreated) >= sevenDaysAgo).sort((a, b) => new Date(b.dateCreated) - new Date(a.dateCreated));
        
        // Group workouts by date
        const groupedByDate = recentWorkouts.reduce((acc, workout) => {
            const dateKey = reformatDate(workout.dateCreated);
            if (!acc[dateKey]) {
                acc[dateKey] = [];
            }
            acc[dateKey].push(workout);
            return acc;
        }, {});

        // Sort dates and create display elements
        const tempDisplay = Object.keys(groupedByDate).sort((a, b) => new Date(b) - new Date(a)).map(date => (
            <div className="d-flex flex-column align-items-center" key={date} style={{ border: '1px solid #ccc', margin: '10px', padding: '10px' }}>
                <h2>{date}</h2>
                {groupedByDate[date].map(workout => (
                    <div className="mt-2" key={workout._id+Math.random()}>
                        <button className="btn btn-danger mt-2 mb-2" onClick={() => handleDelete(workout._id)}>Delete</button>
                        {workout.exercises.map(exercise => {
                                                
                            if (exercise.name !== "Cardio") {
                                return <ViewWeightTraining key={exercise._id} name={exercise.exerciseName} reps={exercise.reps} sets={exercise.sets} weight={exercise.weight} />;
                            } else {
                                return <ViewCardioTraining key={exercise._id} name={exercise.exerciseName} duration={exercise.duration} />;
                            }
                        })}
                    </div>
                ))}
            </div>
        ));


        // const tempDisplay = Object.keys(groupedByDate).sort((a, b) => new Date(b) - new Date(a)).map(date => (
            
        //     <div className="d-flex flex-column align-items-center" key={date} style={{ border: '1px solid #ccc', margin: '10px', padding: '10px' }}>
        //         <h2>{date}</h2>
                
        //         {groupedByDate[date].map(workout => workout.exercises.map(exercise => {
        //             // console.log(workout._id);
                    
        //             if (exercise.name !== "Cardio") {
        //                 return <ViewWeightTraining key={workout._id + Math.random()} name={exercise.name} reps={exercise.reps} sets={exercise.sets} weight={exercise.weight} onDelete={() => handleDelete(exercise._id)} />;
        //             } else {
        //                 return <ViewCardioTraining key={workout._id + Math.random()} name={exercise.name} duration={exercise.duration} onDelete={() => handleDelete(exercise._id)}/>;
        //             }
        //         }))}
        //     </div>
        // ));
        
        setDisplay(tempDisplay);
        setConsecutiveDays(calculateConsecutiveDays());
        
    }


    const calculateConsecutiveDays = () => {
        // Find the different dates the user worked out
        const uniqueDates = [...new Set(workouts.map(workout => {
            const date = new Date(workout.dateCreated);
            return date.toISOString().split('T')[0];
        }))].sort();

        // Variables used to keep track of the streak
        let maxStreak = 0;
        let currentStreak = 0; 

        // calculates the difference between the dates
        for (let i = 1; i < uniqueDates.length; i++) {
            const currentDate = new Date(uniqueDates[i]);
            const previousDate = new Date(uniqueDates[i - 1]);
            const diffInDays = (currentDate - previousDate) / (1000 * 3600 * 24);

            // checks dates are consecutive and adds to the streak if it is
            if (diffInDays === 1) {
                currentStreak++;
            } else {
                maxStreak = Math.max(maxStreak, currentStreak);
                currentStreak = 1;
            }
        }
        // Check the last streak
        maxStreak = Math.max(maxStreak, currentStreak);

        return currentStreak;
    };


    useEffect(() => {
        getWorkouts();
    }, []);    
    
    useEffect(() => {
        if (workouts.length > 0) {
            createViewWorkout();
            
        }
    }, [workouts]);

    return (
        <div>
            <h2>Hello, here you can see your last 7 days of workouts</h2>
            <h3>Number of Consecutive Days: {consecutiveDays+1}</h3>
            
            {display.length > 0 && (
                <div>{ display }</div>

            )}
            
        </div>
    );

}

export default ViewWorkout;