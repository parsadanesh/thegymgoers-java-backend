import { render, screen } from "@testing-library/react";
// import userEvent from "@testing-library/user-event";
import ViewWeightTraining from "../../src/components/ViewWeightTraining.jsx";

import testData from "../sampleUsers.js";
import ViewWorkout from "../../src/pages/ViewWorkouts.jsx";
import { MemoryRouter } from "react-router-dom";
import { expect } from "vitest";

describe("View weight training test", () => { 

    it("should have the correct name for a weight training component", () => {
        const testUser = testData.testUsers[1];
        const testWorkout = testUser.workouts[0]
        render(
            <MemoryRouter>
                <ViewWeightTraining name={testWorkout.exercises[0].name} reps={testWorkout.exercises[0].reps} sets={testWorkout.exercises[0].sets} weight={testWorkout.exercises[0].weight} date={"28/06/2024"} />
            </MemoryRouter>
        )
        const nameElement = screen.getByText((content, node) => {
            const hasText = (node) => node.textContent === `${testWorkout.exercises[0].name}`;
            const nodeHasText = hasText(node);
            const parentHasText = node.parentElement && hasText(node.parentElement);
            return nodeHasText && !parentHasText;
        });
        expect(nameElement).toBeInTheDocument();
    });

    it("should have reps for a weight training component", () => {
        const testUser = testData.testUsers[1];
        const testWorkout = testUser.workouts[0]
        render(
            <MemoryRouter>
                <ViewWeightTraining name={testWorkout.exercises[0].name} reps={testWorkout.exercises[0].reps} sets={testWorkout.exercises[0].sets} weight={testWorkout.exercises[0].weight} date={"28/06/2024"} />
            </MemoryRouter>
        )
        const repsElement = screen.getByText((content, node) => {
            const hasText = (node) => node.textContent === `Reps: ${testWorkout.exercises[0].reps}`;
            const nodeHasText = hasText(node);
            const parentHasText = node.parentElement && hasText(node.parentElement);
            return nodeHasText && !parentHasText;
        });
        expect(repsElement).toBeInTheDocument();
    });

    it("should have sets for a weight training component", () => {
        const testUser = testData.testUsers[1];
        const testWorkout = testUser.workouts[0]
        render(
            <MemoryRouter>
                <ViewWeightTraining name={testWorkout.exercises[0].name} reps={testWorkout.exercises[0].reps} sets={testWorkout.exercises[0].sets} weight={testWorkout.exercises[0].weight} date={"28/06/2024"} />
            </MemoryRouter>
        )
        const setsElement = screen.getByText((content, node) => {
            const hasText = (node) => node.textContent === `Sets: ${testWorkout.exercises[0].sets}`;
            const nodeHasText = hasText(node);
            const parentHasText = node.parentElement && hasText(node.parentElement);
            return nodeHasText && !parentHasText;
        });
        expect(setsElement).toBeInTheDocument();
    });

    it("should have sets for a weight training component", () => {
        const testUser = testData.testUsers[1];
        const testWorkout = testUser.workouts[0]
        render(
            <MemoryRouter>
                <ViewWeightTraining name={testWorkout.exercises[0].name} reps={testWorkout.exercises[0].reps} sets={testWorkout.exercises[0].sets} weight={testWorkout.exercises[0].weight} date={"28/06/2024"} />
            </MemoryRouter>
        )
        const setsElement = screen.getByText((content, node) => {
            const hasText = (node) => node.textContent === `Weight (kg): ${testWorkout.exercises[0].weight}`;
            const nodeHasText = hasText(node);
            const parentHasText = node.parentElement && hasText(node.parentElement);
            return nodeHasText && !parentHasText;
        });
        expect(setsElement).toBeInTheDocument();
    });
})