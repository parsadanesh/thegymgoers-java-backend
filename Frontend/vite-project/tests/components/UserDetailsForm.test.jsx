import { render, screen, fireEvent } from '@testing-library/react';
import UserDetailsForm from "../../src/components/UserDetailsForm";

describe('UserDetailsForm', () => {
  it('renders without crashing', () => {
    render(<UserDetailsForm setEmail={() => {}} setPassword={() => {}} />);
    expect(screen.getByLabelText(/email/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/password/i)).toBeInTheDocument();
  });

  it('submits correct email and password', async () => {
    const mockSetEmail = vi.fn();
    const mockSetPassword = vi.fn();
    render(<UserDetailsForm setEmail={mockSetEmail} setPassword={mockSetPassword} />);

    // Use fireEvent to simulate typing in the input fields
    fireEvent.change(screen.getByLabelText(/email/i), { target: { value: 'test@example.com' } });
    fireEvent.change(screen.getByLabelText(/password/i), { target: { value: 'password123' } });

    // Use fireEvent to simulate form submission
    fireEvent.click(screen.getByRole('button', { name: /submit/i }));

    expect(mockSetEmail).toHaveBeenCalledWith('test@example.com');
    expect(mockSetPassword).toHaveBeenCalledWith('password123');
  });

  it('has required fields', () => {
    render(<UserDetailsForm setEmail={() => {}} setPassword={() => {}} />);
    // Assertions for required fields
  });
});