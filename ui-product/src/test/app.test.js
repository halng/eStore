import React from 'react'
import { render } from '@testing-library/react'
import '@testing-library/jest-dom'
import App from '../App'
import { BrowserRouter } from 'react-router-dom'

test('Full component navigating/rendering test', () => {
    render(<App />, { wrapper: BrowserRouter })
})
