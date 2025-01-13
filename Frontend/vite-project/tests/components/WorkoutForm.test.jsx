import { render, fireEvent, waitFor } from '@testing-library/react';
import axios from 'axios';
import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest';
import WorkoutForm from '../../src/components/WorkoutForm';

// Mock axios
vi.mock('axios');
axios.post = vi.fn();

describe('WorkoutForm', () => {
  beforeEach(() => {
    // Reset mocks before each test
    axios.post.mockClear();
  });


  it('renders without crashing', () => {
    const { getByText } = render(<WorkoutForm user={{ email: 'test@example.com' }} />);
    expect(getByText('Welcome test@example.com')).toBeInTheDocument();
  });

  it('handles exercise selection correctly', async () => {
    const { getByText, getByRole } = render(<WorkoutForm user={{ email: 'test@example.com' }} />);
    fireEvent.change(getByRole('combobox'), { target: { value: '1' } }); // Selecting Cardio
    await waitFor(() => expect(getByText('Cardio')).toBeInTheDocument());
  });

  it('submits workout correctly', async () => {
    axios.post.mockResolvedValue({ status: 201 });
    const { getByText, getByRole } = render(<WorkoutForm user={{ email: 'test@example.com' }} />);
    fireEvent.submit(getByRole('button'));
    await waitFor(() => expect(axios.post).toHaveBeenCalledWith(expect.any(String), expect.any(Object)));
  });

  afterEach(() => {
      // Clean up after each test
      vi.resetAllMocks();
  });
});