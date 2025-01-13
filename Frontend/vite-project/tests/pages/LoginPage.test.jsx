import { describe, it, expect, vi, beforeEach } from 'vitest';
import { render, fireEvent, screen, waitFor } from '@testing-library/react';
import LoginPage from '../../src/pages/LoginPage';

// Mock child components and props
vi.mock('../components/UserDetailsForm', () => ({
  __esModule: true,
  default: ({ setEmail, setPassword }) => (
    <div>
      <input data-testid="emailInput" onChange={(e) => setEmail(e.target.value)} />
      <input data-testid="passwordInput" onChange={(e) => setPassword(e.target.value)} />
    </div>
  ),
}));

describe('LoginPage', () => {
  let setUserMock;

  beforeEach(() => {
    setUserMock = vi.fn();
  });

  it('renders without crashing', () => {
    render(<LoginPage setUser={setUserMock} />);
    expect(screen.getByText('Login')).toBeInTheDocument();
  });

  it('updates email and password state on input change', async () => {
    render(<LoginPage setUser={setUserMock} />);
    
        const emailInput = await screen.findByTestId('email');
        const passwordInput = await screen.findByTestId('password');

        fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
        fireEvent.change(passwordInput, { target: { value: 'password123' } });

        expect(emailInput.value).toBe('test@example.com');
        expect(passwordInput.value).toBe('password123');
    });

    it('calls setUser prop function with email and password on input change', async () => {
        const setUserMock = vi.fn();
        render(<LoginPage setUser={setUserMock} />);
        
        const emailInput = screen.getByTestId('email');
        const passwordInput = screen.getByTestId('password');
        const submitButton = screen.getByRole('button', { name: /submit/i }); 

        fireEvent.change(emailInput, { target: { value: 'test@example.com' } });
        fireEvent.change(passwordInput, { target: { value: 'password123' } });
        fireEvent.click(submitButton); 

        await waitFor(() => {
            expect(setUserMock).toHaveBeenCalledWith({
                email: 'test@example.com',
                password: 'password123',
            });
        });
    });
});