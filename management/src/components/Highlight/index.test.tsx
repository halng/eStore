import '@testing-library/jest-dom'
import { render, screen } from '@testing-library/react'
import Highlight from '.'

describe('Highlight Component', () => {
    it('renders the original string when no highlight is provided', () => {
        const originStr = 'Lorem ipsum dolor sit amet'
        render(<Highlight originStr={originStr} highlight='' color='success' />)
        const textElement = screen.getByText(originStr)
        expect(textElement).toBeInTheDocument()
    })

    it('renders the highlighted string with the specified color', () => {
        const originStr = 'Lorem ipsum dolor sit amet'
        const highlight = 'ipsum'
        const color = 'warning'
        render(<Highlight originStr={originStr} highlight={highlight} color={color} />)
        const highlightedTextElement = screen.getByText(highlight)
        expect(highlightedTextElement).toBeInTheDocument()
        expect(highlightedTextElement).toHaveStyle(`color: ${color}`)
    })

    it('renders the original string with multiple highlighted occurrences', () => {
        const originStr = 'Lorem ipsum dolor sit amet ipsum consectetur adipiscing elit ipsum'
        const highlight = 'ipsum'
        const color = 'success'
        render(<Highlight originStr={originStr} highlight={highlight} color={color} />)
        const highlightedTextElements = screen.getAllByText(highlight)
        expect(highlightedTextElements).toHaveLength(3)
        highlightedTextElements.forEach((element) => {
            expect(element).toHaveStyle(`color: ${color}`)
        })
    })
})
