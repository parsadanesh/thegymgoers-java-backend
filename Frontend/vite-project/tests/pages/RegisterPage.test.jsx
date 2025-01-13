import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import RegisterPage from '../../src/pages/RegisterPage';

// Mock child components and props
vi.mock('../components/UserDetailsForm', () => ({
  __esModule: true,
  default: ({ setEmail, setPassword }) => (
    <div>
      <input data-testid="email" onChange={(e) => setEmail(e.target.value)} />
      <input data-testid="password" onChange={(e) => setPassword(e.target.value)} />
    </div>
  ),
}));

describe('RegisterPage', () => {
  let setNewUserMock;

  beforeEach(() => {
    setNewUserMock = vi.fn();
  });

  it('renders without crashing', () => {
    render(<RegisterPage setNewUser={setNewUserMock} />);
    expect(screen.getByText('Register')).toBeInTheDocument();
  });

  it('updates email and password state on input change', async () => {
    render(<RegisterPage setNewUser={setNewUserMock} />);
    
    const emailInput = screen.getByTestId('email');
    const passwordInput = screen.getByTestId('password');

    fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
    fireEvent.change(passwordInput, { target: { value: 'password123' } });

    expect(emailInput.value).toBe('test@example.com');
    expect(passwordInput.value).toBe('password123');
  });
    
    it('calls setNewUser prop function with email and password on input change', async () => {
        render(<RegisterPage setNewUser={setNewUserMock} />);
  
        // Simulate user input
        fireEvent.change(screen.getByTestId('email'), { target: { value: 'test@example.com' } });
        fireEvent.change(screen.getByTestId('password'), { target: { value: 'password123' } });
        
        // Simulate form submission
        fireEvent.submit(screen.getByRole('button', { name: /submit/i }));
        
        // Wait for the mock function to be called and assert its call arguments
        await waitFor(() => {
            expect(setNewUserMock).toHaveBeenCalledWith({
            email: 'test@example.com',
            password: 'password123',
            });
        });
    });
    
});