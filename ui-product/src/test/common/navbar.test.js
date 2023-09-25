import React from 'react'
import { render } from '@testing-library/react'
import Navbar from '../../common/Navbar'

describe('Test: NavBar Component', () => {
    it('Test breadcrumb Items', () => {
        // prepare
        // eslint-disable-next-line no-global-assign
        window = Object.create(window)
        const url = 'http://localhost:3000/product/attribute'
        Object.defineProperty(window, 'location', {
            value: {
                href: url,
            },
            writable: true,
        })

        // render component
        const { container } = render(<Navbar />)

        const breadcrumbItems = container.getElementsByClassName('breadcrumb-item')
        expect(breadcrumbItems.length).toBe(3)
    })
})
