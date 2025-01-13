import React from 'react';
import { render } from '@testing-library/react';
import Footer from '../../src/components/Footer';

describe('Footer Component', () => {
  test('renders without crashing', () => {
    const { container } = render(<Footer />);
    expect(container).toBeInTheDocument();
  });

  test('contains the correct text', () => {
    const { getByText } = render(<Footer />);
    expect(getByText('The GymGoers')).toBeInTheDocument();
  });

  test('has correct classes for styling', () => {
    const { container } = render(<Footer />);
    const footer = container.querySelector('footer');
    expect(footer).toHaveClass('bg-dark');
    expect(footer).toHaveClass('text-success');
  });
});