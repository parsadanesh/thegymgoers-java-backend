import { describe, it, expect, vi } from 'vitest';
import { render } from '@testing-library/react';
import { BrowserRouter } from 'react-router-dom';
import React from 'react';
import App from '../src/App';

describe('Application root', () => {
  it('renders without crashing', () => {
    const div = document.createElement('div');
    div.id = 'root';
    document.body.appendChild(div);

    render(
        <BrowserRouter>
          <App />
        </BrowserRouter>
    );

    expect(document.body.contains(div)).toBe(true);
  });
});