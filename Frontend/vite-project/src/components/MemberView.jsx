import { useEffect, useState } from "react";

const MemberView = (props) => {

    const [consecutiveDays, setConsecutiveDays] = useState(0);

    const workouts = props.workouts;

    const calculateConsecutiveDays = () => {
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

        return maxStreak;
    };

    useEffect(() => {
        setConsecutiveDays(calculateConsecutiveDays());
    }, [])

    return (
        <div>
            <h4>{`${props.email}  Days: ${consecutiveDays}`}</h4>
            <br />
            
        </div>
)
}

export default MemberView;