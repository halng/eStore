import React from 'react'
import { render, fireEvent, screen } from '@testing-library/react'
import CustomTable from '../../common/CustomTable'
import '@testing-library/jest-dom'
import ReactTestUtils from 'react-dom/test-utils'

describe('Test CustomTable Component: Group', () => {
    // mock data
    const category = 'group'
    const firstRow = [
        { value: 'Id', fieldName: 'id' },
        { value: 'Name', fieldName: 'name' },
        { value: 'Status', fieldName: 'status' },
        { value: 'Create Date', fieldName: 'createdDate' },
        { value: 'Update Date', fieldName: 'updatedDate' },
        { value: 'Actions', fieldName: '' },
    ]
    const row = [
        { id: '1', name: 'name 1', status: 'ENABLED', createdDate: '2022-01-01', updatedDate: '2022-02-01' },
        { id: '1', name: 'name 1', status: 'REMOVED', createdDate: '2022-01-01', updatedDate: '2022-02-01' },
        { id: '1', name: 'name 1', status: 'DISABLED', createdDate: '2022-01-01', updatedDate: '2022-02-01' },
    ]
    let deleteFunc, updateFunc, updateStatusFunc
    beforeEach(() => {
        deleteFunc = jest.fn()
        updateFunc = jest.fn()
        updateStatusFunc = jest.fn()
    })

    it('Test ui rendering', () => {
        const { container } = render(
            <CustomTable
                category={category}
                firstRow={firstRow}
                onDeleteHandler={deleteFunc}
                onUpdateHandler={updateFunc}
                onChangeGroupStatusHandler={updateStatusFunc}
                rows={row}
            />,
        )

        const renderedRow = container.getElementsByTagName('tr')
        expect(renderedRow.length).toBe(4)

        const rowWithEnableStatus = container.getElementsByClassName('text-enabled')
        expect(rowWithEnableStatus.length).toBe(1)

        const btnDelete = container.getElementsByClassName('btn_del')
        expect(btnDelete.length).toBe(2) // 2 show and 2 hidden in modal
    })

    it('Test Delete button', () => {
        const { container } = render(
            <CustomTable
                category={category}
                firstRow={firstRow}
                onDeleteHandler={deleteFunc}
                onUpdateHandler={updateFunc}
                onChangeGroupStatusHandler={updateStatusFunc}
                rows={row}
            />,
        )

        const deleteButton = container.getElementsByClassName('btn_del')
        ReactTestUtils.act(() => fireEvent.click(deleteButton[0]))

        expect(screen.getAllByText('Delete group')[0]).toBeInTheDocument()
    })
})
