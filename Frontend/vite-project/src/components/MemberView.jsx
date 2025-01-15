// import { useEffect, useState } from "react";

// const MemberView = (props) => {

//     const [consecutiveDays, setConsecutiveDays] = useState(0);

//     const workouts = props.workouts;

//     const calculateConsecutiveDays = () => {
//         console.log(workouts);
        
//         // Find the individual dates the user logged worked out
//         const uniqueDates = [...new Set(workouts.map(workout => {
//             const date = new Date(workout.dateCreated);
//             return date.toISOString().split('T')[0];
//         }))].sort();


//         // Variables used to keep track of the streak
//         let maxStreak = 0;
//         let currentStreak = 1;

//         // calculates the difference between the dates
//         for (let i = 1; i < uniqueDates.length; i++) {
//             const currentDate = new Date(uniqueDates[i]);
//             const previousDate = new Date(uniqueDates[i - 1]);
//             const diffInDays = (currentDate - previousDate) / (1000 * 3600 * 24);

//             // checks dates are consecutive and adds to the streak if it is
//             if (diffInDays === 1) {
//                 currentStreak++;
//             } else {
//                 maxStreak = Math.max(maxStreak, currentStreak);
//                 currentStreak = 1;
//             }
//         }
//         // Check the last streak
//         maxStreak = Math.max(maxStreak, currentStreak);

//         return maxStreak;
//     };

//     useEffect(() => {
//         setConsecutiveDays(calculateConsecutiveDays());
//     }, [])

//     return (
//         <div>
//             <h4>{`${props.email}  Days: ${consecutiveDays}`}</h4>
//             <br />
            
//         </div>
// )
// }

// export default MemberView;

import React, { useEffect, useState } from "react";
import axios from "axios";

const MemberView = (props) => {
    
    const token = localStorage.getItem('token');
    const [consecutiveDays, setConsecutiveDays] = useState(0);
    const [workouts, setWorkouts] = useState([]);

    const getMember = async () => {
        // console.log(token);
        // console.log(props.member.username);
        
        try {
            const res = await axios.get(`http://localhost:4000/gymgroups/group/${props.name}`,
        
                {
                    headers: {
                    Authorization: token
                    },
                    
                    params: {
                        username: props.member
                        
                }
                }
            
            );
            // console.log(res);
            setWorkouts(res.data);
            
        } catch (e) {
            console.log(e.message);
            
        }

    }

    useEffect(() => {
        getMember();
    }, []);

    const calculateConsecutiveDays = () => {
        console.log(workouts);
        
        if (!Array.isArray(workouts) || workouts.length === 0) {
            console.log("No workouts available");
            return;
        }

        console.log(workouts);

        // Find the individual dates the user logged worked out
        const uniqueDates = [...new Set(workouts.map(workout => {
            const date = new Date(workout.dateCreated);
            return date.toISOString().split('T')[0];
        }))].sort();

        // Variables used to keep track of the streak
        let maxStreak = 0;
        let currentStreak = 1;

        // calculates the difference between the dates
        for (let i = 1; i < uniqueDates.length; i++) {
            const prevDate = new Date(uniqueDates[i - 1]);
            const currDate = new Date(uniqueDates[i]);

            const diffTime = Math.abs(currDate - prevDate);
            const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

            if (diffDays === 1) {
                currentStreak++;
            } else {
                maxStreak = Math.max(maxStreak, currentStreak);
                currentStreak = 1;
            }
        }

        maxStreak = Math.max(maxStreak, currentStreak);
        setConsecutiveDays(maxStreak);
    };

    useEffect(() => {
        calculateConsecutiveDays();
    }, [workouts]);

    return (
        <div>
            <h4>{`User: ${props.member}, `}</h4>
            <h4>{`Days Consecutive: ${consecutiveDays}`}</h4>
        </div>
    );
};

export default MemberView;