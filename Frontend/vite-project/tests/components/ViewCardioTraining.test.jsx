import { render, screen } from "@testing-library/react";
// import userEvent from "@testing-library/user-event";
import ViewCardioTraining from "../../src/components/ViewCardioTraining.jsx";

import testData from "../sampleUsers.js";
import ViewWorkout from "../../src/pages/ViewWorkouts.jsx";
import { MemoryRouter } from "react-router-dom";
import { expect } from "vitest";

describe("View Cardio training test", () => {

    it("should have the correct name for a weight training component", () => {
        const testUser = testData.testUsers[1];
        const testWorkout = testUser.workouts[0]
        render(
            <MemoryRouter>
                <ViewCardioTraining name={testWorkout.exercises[0].name} duration={"25"} date={"28/06/2024"} />
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

    it("should have the duration name for a weight training component", () => {
        const testUser = testData.testUsers[1];
        const testWorkout = testUser.workouts[0]
        render(
            <MemoryRouter>
                <ViewCardioTraining name={testWorkout.exercises[0].name} duration={"25"} date={"28/06/2024"} />
            </MemoryRouter>
        )
        const durationElement = screen.getByText((content, node) => {
            const hasText = (node) => node.textContent === `Duration: ${"25"}`;
            const nodeHasText = hasText(node);
            const parentHasText = node.parentElement && hasText(node.parentElement);
            return nodeHasText && !parentHasText;
        });
        expect(durationElement).toBeInTheDocument();
    });

    
});