import axios from "axios";
import React from 'react';
import { render, fireEvent, waitFor } from '@testing-library/react';
import { describe, it, expect, vi, beforeEach } from 'vitest';
import ViewWorkout from "../../src/pages/ViewWorkouts";


vi.mock("axios")

// Example test block using Vitest
describe('View Workout Component', () => {

    beforeEach(() => {
        vi.resetAllMocks();
    });

    it('fetches workouts on mount and displays them', async () => {
        const mockWorkouts = {
            data: [
                // Add mock data here
            ],
        };
        axios.get.mockResolvedValue(mockWorkouts);

        const { getByText } = render(<ViewWorkout user={{ email: 'test@example.com' }} />);
    
        await waitFor(() => {
            expect(axios.get).toHaveBeenCalledWith("http://localhost:3000/viewWorkouts", { params: { email: 'test@example.com' } });
        });
    });

    it('calculates consecutive workout days correctly', async () => {
        const mockWorkouts = {
            data: [
                { id: 'workout1', dateCreated: '2023-04-01' },
                { id: 'workout2', dateCreated: '2023-04-02' },
                { id: 'workout3', dateCreated: '2023-04-04' },
            ],
        };
        axios.get.mockResolvedValue(mockWorkouts);

        const { getByText } = render(<ViewWorkout user={{ email: 'test@example.com' }} />);
        await waitFor(() => expect(axios.get).toHaveBeenCalled());

        expect(getByText(/Number of Consecutive Days: 1/i)).toBeInTheDocument();
    });

    it('logs an error message when the fetch fails', async () => {
        const consoleSpy = vi.spyOn(console, 'log');
        const errorMessage = 'Failed to fetch workouts';
        axios.get.mockRejectedValue({
            response: {
                data: { message: errorMessage }
            }
        });

        render(<ViewWorkout user={{ email: 'test@example.com' }} />);

        await waitFor(() => {
            expect(consoleSpy).toHaveBeenCalledWith(errorMessage);
        });

        consoleSpy.mockRestore();
    });
});