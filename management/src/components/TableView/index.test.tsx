import '@testing-library/jest-dom'
import { render, screen } from '@testing-library/react'
import TableView from '.'
import { PRODUCT_GROUP_TABLE_HEADER } from '@constants'
import { mockProductGroupData } from '@mock'

describe('Test Rendering', () => {
    it('snapshot rendering', () => {
        const { asFragment } = render(<TableView tableData={[]} tableHeader={PRODUCT_GROUP_TABLE_HEADER} />)
        expect(asFragment()).toMatchSnapshot()
    })
    it('renders a heading', () => {
        render(<TableView tableData={mockProductGroupData} tableHeader={PRODUCT_GROUP_TABLE_HEADER} />)

        // check render table header
        const nameCol = screen.getByText('Name')
        const descriptionCol = screen.getByText('Description')
        const noCol = screen.getByText('No.')

        expect(nameCol).toBeInTheDocument()
        expect(descriptionCol).toBeInTheDocument()
        expect(noCol).toBeInTheDocument()

        // check render table data
        const nameCount = screen.getAllByText('Product Group', { exact: false })
        expect(nameCount.length).toBe(10)

        const combobox = screen.getByRole('combobox')
        expect(combobox).toBeInTheDocument()
    })
    it('renders a when missing data', () => {
        render(<TableView tableData={null} tableHeader={PRODUCT_GROUP_TABLE_HEADER} />)

        const noData = screen.getByText('No Data!')
        expect(noData).toBeInTheDocument()
    })
    it('renders a when empty data', () => {
        render(<TableView tableData={[]} tableHeader={PRODUCT_GROUP_TABLE_HEADER} />)

        // check render table header
        const nameCol = screen.getByText('Name')
        const descriptionCol = screen.getByText('Description')
        const noCol = screen.getByText('No.')

        expect(nameCol).toBeInTheDocument()
        expect(descriptionCol).toBeInTheDocument()
        expect(noCol).toBeInTheDocument()

        // check render table data
        const nameCount = screen.queryAllByText('Product Group')
        expect(nameCount).toHaveLength(0)
    })
})
