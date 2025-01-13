
import { render, screen, fireEvent } from "@testing-library/react";
// import userEvent from "@testing-library/user-event";
import WeightTraining from "../../src/components/WeightTraining.jsx";

import testData from "../sampleUsers.js";
import ViewWorkout from "../../src/pages/ViewWorkouts.jsx";
import { MemoryRouter } from "react-router-dom";
import { expect } from "vitest";

describe('WeightTraining Component Tests', () => {
  it('calls setWeightTraining with correct data on form submission', async () => {
    const setWeightTrainingMock = vi.fn();
    const selectedOptionText = "Bench Press"; // Example selected option text

      render(<WeightTraining setWeightTraining={setWeightTrainingMock} selectedOptionText={selectedOptionText} />);

      fireEvent.change(screen.getByPlaceholderText('Number of sets'), { target: { value: '3' } });


      fireEvent.change(screen.getByPlaceholderText('Number of reps'), { target: { value: '10' } });

      fireEvent.change(screen.getByPlaceholderText('Weight in kg'), { target: { value: '50' } });
      

    // Simulate form submission
    fireEvent.submit(screen.getByRole('button', { name: /log exercise/i }));

    // Assert setWeightTraining was called with the correct data
    expect(setWeightTrainingMock).toHaveBeenCalledWith({
      name: selectedOptionText,
      reps: '10',
      sets: '3',
      weight: '50'
    });
  });
    
    it('disables submit button when any input field is empty', () => {
    render(<WeightTraining setWeightTraining={() => {}} selectedOptionText="Squat" />);
    const submitButton = screen.getByRole('button', { name: /log exercise/i });
    expect(submitButton).toBeDisabled();
    });

    it('enables submit button when all input fields are filled', () => {
        render(<WeightTraining setWeightTraining={() => {}} selectedOptionText="Squat" />);
        fireEvent.change(screen.getByPlaceholderText('Number of sets'), { target: { value: '3' } });
        fireEvent.change(screen.getByPlaceholderText('Number of reps'), { target: { value: '10' } });
        fireEvent.change(screen.getByPlaceholderText('Weight in kg'), { target: { value: '50' } });
        const submitButton = screen.getByRole('button', { name: /log exercise/i });
        expect(submitButton).not.toBeDisabled();
    });

    it('does not call setWeightTrainingMock when form is submitted with incomplete data', () => {
        const setWeightTrainingMock = vi.fn();
        render(<WeightTraining setWeightTraining={setWeightTrainingMock} selectedOptionText="Squat" />);

        // Only fill in one of the required fields
        fireEvent.change(screen.getByPlaceholderText('Number of sets'), { target: { value: '3' } });

        // Simulate form submission
        fireEvent.submit(screen.getByRole('button', { name: /log exercise/i }));

        // Assert setWeightTrainingMock was not called due to incomplete data
        expect(setWeightTrainingMock).not.toHaveBeenCalled();
    });
        
});