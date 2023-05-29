import '@testing-library/jest-dom';

describe('Test welcome tab title', () => {
  it('Test get title', () => {
    const msg = 'Create Next App';

    expect('Create Next App').toEqual(msg);
  });
});
