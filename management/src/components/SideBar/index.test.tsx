import '@testing-library/jest-dom'
import { screen } from '@testing-library/react'
import { SideBar } from '@components'
import navConfig from './config-navigation'
import { renderWithProviders } from '../../utils/test-utils'
import { AuthState } from '@types'

jest.mock('next/navigation', () => ({
    usePathname: jest.fn(() => '/partner'),
}))

describe('Test SideBar Component', () => {
    it('renders a side bar', () => {
        const initData: AuthState = {
            isAuth: true,
            username: 'Tao Nguyen',
            photoUrl: 'https://www.google.com',
            role: 'SELLER',
            email: 'hello@gmail.com',
            id: '123',
        }
        const { container } = renderWithProviders(<SideBar />, {
            preloadedState: { auth: initData },
        })

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
