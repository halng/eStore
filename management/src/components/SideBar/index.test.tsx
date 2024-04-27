import '@testing-library/jest-dom'
import { render, screen } from '@testing-library/react'
import { SideBar } from '@components'
import navConfig from './config-navigation'

jest.mock('next/navigation', () => ({
    usePathname: jest.fn(() => '/partner'),
}))

describe('Test SideBar Component', () => {
    it('snapshot rendering', () => {
        const { asFragment } = render(<SideBar />)
        expect(asFragment()).toMatchSnapshot()
    })
    it('renders a side bar', () => {
        const { container } = render(<SideBar />)

        // check render account
        const accountName = screen.getByText('Tao Nguyen')
        const accountRole = screen.getByText('SELLER')
        expect(accountName).toBeInTheDocument()
        expect(accountRole).toBeInTheDocument()

        // check render menu
        const menuItems = container.querySelectorAll('a')
        expect(menuItems).toHaveLength(navConfig.length)
        for (let i = 0; i < navConfig.length; i++) {
            expect(menuItems[i]).toHaveTextContent(navConfig[i].title)
        }
    })
})
