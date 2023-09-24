import React from 'react'
import { render, fireEvent } from '@testing-library/react'
import Pagination from '../../common/pagination'
import '@testing-library/jest-dom'
import ReactTestUtils from 'react-dom/test-utils'

describe('Test: Pagination component', () => {
    let setPage
    beforeEach(() => {
        setPage = jest.fn()
        jest.spyOn(React, 'useState').mockImplementation((page) => [page, setPage])
    })
    it('Test rendering', () => {
        const { container } = render(<Pagination total={20} totalPage={2} currentPage={1} />)

        expect(container.getElementsByClassName('page-item').length).toBe(4)
        expect(container.getElementsByClassName('disabled').length).toBe(1)
        expect(container.getElementsByClassName('active').length).toBe(1)
        expect(container.getElementsByClassName('summary')[0].textContent).toEqual('Total: 20')
    })

    it('Test Next Button', () => {
        const { container } = render(<Pagination total={20} totalPage={2} currentPage={1} setPage={setPage} />)

        const expandButton = container.getElementsByClassName('page-link')

        expect(expandButton.length).toBe(4)

        const nextButton = expandButton[3]
        ReactTestUtils.act(() => fireEvent.click(nextButton))
        expect(setPage).toHaveBeenCalledTimes(1)
        expect(setPage).toHaveBeenCalledWith(2)
    })

    it('Test Number Page Button', () => {
        const { container } = render(<Pagination total={20} totalPage={2} currentPage={1} setPage={setPage} />)

        const expandButton = container.getElementsByClassName('page-link')

        // by default when component rendered
        const firstPageButton = expandButton[1]
        expect(firstPageButton.classList).toContain('active')

        // Click on button 2
        const secondPageButton = expandButton[2]
        ReactTestUtils.act(() => fireEvent.click(secondPageButton))
        expect(setPage).toHaveBeenCalledTimes(1)
        expect(setPage).toHaveBeenCalledWith(2)

        // Click again on button 1
        ReactTestUtils.act(() => fireEvent.click(firstPageButton))
        expect(setPage).toHaveBeenCalledWith(1)
    })

    it('Test Rendering for li tag', () => {
        const { container } = render(<Pagination total={20} totalPage={2} currentPage={1} setPage={setPage} />)

        const liItems = container.getElementsByClassName('page-item')

        expect(liItems.length).toBe(4)

        const previousItem = liItems[0]
        // disable when current page = 1
        expect(previousItem.classList).toContain('disabled')
    })
})
