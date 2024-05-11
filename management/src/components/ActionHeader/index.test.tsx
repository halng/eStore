import '@testing-library/jest-dom'
import { render, screen } from '@testing-library/react'
import ActionHeader from '.'

jest.mock('next/navigation', () => ({
    usePathname: jest.fn(() => '/partner/test'),
}))

describe('Test Action Header Component', () => {
    it('snapshot rendering', () => {
        const { asFragment } = render(<ActionHeader tableData={[]} setOpenAction={null} />)
        expect(asFragment()).toMatchSnapshot()
    })
    it('renders a heading', () => {
        render(<ActionHeader tableData={[]} setOpenAction={null} />)

        // check render breadcrumbs
        const separatorCount = screen.getAllByText('/')
        expect(separatorCount).toHaveLength(2)

        const homeIcon = screen.getByTestId('HomeIcon')
        expect(homeIcon).toBeInTheDocument()

        const pathElement = screen.getByText('partner', { exact: false })
        expect(pathElement).toBeInTheDocument()

        // check render search box
        const searchBox = screen.getByRole('combobox')
        expect(searchBox).toBeInTheDocument()

        // check render create button
        const createButton = screen.getByRole('button', { name: 'Add' })
        expect(createButton).toBeInTheDocument()
    })
})
