import Home from '@/app/page';
import { render, screen } from '@testing-library/react';
import '@testing-library/jest-dom';

describe('Test style home page', () => {
  it('Test style div', () => {
    render(<Home />);
    const msg = 'Hello From Product!';

    const ele = screen.getByText(msg);

    expect(ele).toBeInTheDocument();
  });
});
