import { render, screen } from '@testing-library/react';
import Welcome from '../../src/components/welcome';
import '@testing-library/jest-dom';

describe('Test welcome rendering', () => {
  it('Render component', () => {
    const msg = 'Hello From Product!';
    render(<Welcome />);

    const heading = screen.getByText(msg);

    expect(heading).toBeInTheDocument();
  });
});
