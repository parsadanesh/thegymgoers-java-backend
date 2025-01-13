import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import { BrowserRouter as Router } from 'react-router-dom';
import Header from '../../src/components/Header';

describe('Header Component', () => {
  test('should toggle navbar on click', () => {
    const { getByLabelText, queryByTestId } = render(
      <Router>
        <Header loggedIn={false} />
      </Router>
      );
      
    // Initially, navbar should not show its contents
    expect(queryByTestId('navbarNav')).not.toHaveClass('show');

    // Simulate click on toggle button
    fireEvent.click(getByLabelText('Toggle navigation'));

    // Now, navbar should show its contents
    expect(queryByTestId('navbarNav')).toHaveClass('show');
  });

  test('should display login and sign up when not logged in', () => {
    const { getByText } = render(
      <Router>
        <Header loggedIn={false} />
      </Router>
    );

    // Login and Sign Up links should be present
    expect(getByText('Login')).toBeInTheDocument();
    expect(getByText('Sign Up')).toBeInTheDocument();
  });

  test('should display user links when logged in', () => {
    const { getByText } = render(
      <Router>
        <Header loggedIn={true} />
      </Router>
    );

    // Dashboard, Log workout, View workout, GymGroups, and Log Out should be present
    
    expect(getByText('Log workout')).toBeInTheDocument();
    expect(getByText('View workout')).toBeInTheDocument();
    
    expect(getByText('Log Out')).toBeInTheDocument();
  });
});