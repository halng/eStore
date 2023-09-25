import React from 'react'
import { fireEvent, render, screen } from '@testing-library/react'
import ProductOption from '../../components/ProductOption'
import '@testing-library/jest-dom'
jest.mock('api-estore-v2')
import { ProductOptionAPI } from 'api-estore-v2'
import { options } from '../mock/data.js'
import { act } from 'react-dom/test-utils'

describe('Test: Product Option Component ', () => {
    beforeEach(() => {
        const res = {
            data: {
                options: options,
                totalPages: 2,
                totalOptions: 13,
            },
        }
        ProductOptionAPI.getAll = jest.fn(() => Promise.resolve(res))
    })
    it('Test rendering => When init data success', () => {
        // eslint-disable-next-line no-global-assign
        window = Object.create(window)
        const url = 'http://localhost:3000/product/option'
        Object.defineProperty(window, 'location', {
            value: {
                href: url,
            },
            writable: true,
        })

        // render component
        const { container } = render(<ProductOption />)

        const breadcrumbItems = container.getElementsByClassName('breadcrumb-item')
        expect(breadcrumbItems.length).toBe(3)

        expect(screen.getAllByText('Option')[0]).toBeInTheDocument()
    })

    it('Test rendering => When init data failed', async () => {
        ProductOptionAPI.getAll = jest.fn(() => Promise.reject('Cannot get all option'))

        const { container } = render(<ProductOption />)

        expect(container.getElementsByTagName('tr').length).toBe(1)
    })

    it('Test create new option', () => {
        ProductOptionAPI.create = jest.fn(() => Promise.resolve())

        render(<ProductOption />)

        const button = screen.getByTestId('openOffcanvas')

        act(() => {
            fireEvent.click(button) // click on create new option button
        })

        expect(screen.getByTestId('offcanvasRightLabel')).toBeInTheDocument()

        const inputTag = screen.getByPlaceholderText('New option name')
        const selectTag = screen.getByTestId('formSelectOptionType')
        const textTag = screen.getByPlaceholderText('Description for new options')
        // fill value
        act(() => {
            fireEvent.change(inputTag, { target: { value: 'this is new name' } })
            fireEvent.select(selectTag, { target: { value: 'number' } })
            fireEvent.change(textTag, { target: { value: 'this is desc' } })
        })

        // assert
        expect(inputTag.value).toBe('this is new name')
        expect(selectTag.value).toBe('number')
        expect(textTag.value).toBe('this is desc')

        const createButton = screen.getByTestId('btnCreate')

        act(() => {
            fireEvent.click(createButton)
        })
    })

    it('Test search func', () => {
        render(<ProductOption />)

        const InputSearchTag = screen.getByPlaceholderText('Search in option name')

        act(() => {
            fireEvent.change(InputSearchTag, { target: { value: '2' } })
        })

        const allRecord = screen.getAllByText('2')

        expect(allRecord.length).toBe(2)
    })
})
