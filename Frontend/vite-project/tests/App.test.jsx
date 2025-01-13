
import { describe, it, expect, beforeEach, afterEach, vi } from 'vitest';
import { render, fireEvent, waitFor, screen } from '@testing-library/react';
import axios from 'axios';
import { MemoryRouter } from 'react-router-dom';
import App from "../src/App";

const user = { email: 'test@example.com', password: 'password' };
const setLoggedIn = vi.fn();
const navigate = vi.fn();
const setRegistrationMessage = vi.fn();
const e = { preventDefault: vi.fn() }; 

vi.mock('axios');

describe.only('App Component', () => {
    beforeEach(() => {
    // Reset mocks before each test
    vi.resetAllMocks();
    });
  

  it('renders login page when not logged in', () => {
    render(<App />, { wrapper: MemoryRouter });
    expect(screen.getByText(/login/i)).toBeInTheDocument();
  });

  it('renders register page when navigating to sign-up', () => {
    render(<App />, { wrapper: MemoryRouter });
    fireEvent.click(screen.getByText(/sign up/i));
    expect(screen.getByText(/register/i)).toBeInTheDocument();
  });

 });
