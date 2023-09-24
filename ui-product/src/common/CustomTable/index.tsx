/**
 * This common component only use for simple component. Now it only use for
 * - Product Attribute
 * - Product Group
 * - Product Option
 */

import React, { useState } from 'react'
import './style.css'
import { TableRowType } from '../../types/TableRowType'

interface PropsData {
    category: string
    firstRow: TableRowType[]
    rows: any
    onDeleteHandler: any
    onUpdateHandler: any
    onChangeGroupStatusHandler?: any
}

const CustomTable = ({
    category,
    firstRow,
    rows,
    onDeleteHandler,
    onUpdateHandler,
    onChangeGroupStatusHandler,
}: PropsData) => {
    const DeleteModal = ({ item }: any) => {
        return (
            <div>
                {/* <!-- Button trigger modal --> */}
                <button
                    type='button'
                    className='btn btn-danger btn_del'
                    data-bs-toggle='modal'
                    data-bs-target={`#deleteModal_${item.id}`}
                >
                    Delete
                </button>

                {/* <!-- Modal --> */}
                <div
                    className='modal fade'
                    id={`deleteModal_${item.id}`}
                    data-bs-keyboard='false'
                    tabIndex={-1}
                    aria-labelledby='staticBackdropLabel'
                    aria-hidden='true'
                >
                    <div className='modal-dialog modal-dialog-centered'>
                        <div className='modal-content'>
                            <div className='modal-header'>
                                <h1 className='modal-title fs-5' id='staticBackdropLabel'>
                                    Delete {category}
                                </h1>
                                <button
                                    type='button'
                                    className='btn-close'
                                    data-bs-dismiss='modal'
                                    aria-label='Close'
                                ></button>
                            </div>
                            <div className='modal-body'>
                                Do you want to delete {category} name:
                                <span className='text-danger'> {item.name}</span>
                            </div>
                            <div className='modal-footer'>
                                <button
                                    type='button'
                                    className='btn btn-danger'
                                    data-bs-dismiss='modal'
                                    onClick={(e) => onDeleteHandler(e, item.id)}
                                >
                                    Delete
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    const UpdateModal = ({ item }: any) => {
        const [data, setData] = useState<any>({})
        return (
            <div>
                {/* <!-- Button trigger modal --> */}
                <button
                    type='button'
                    className='btn btn-primary'
                    data-bs-toggle='modal'
                    data-bs-target={`#updateModal_${item.id}`}
                >
                    Update
                </button>

                {/* <!-- Modal --> */}
                <div
                    className='modal fade'
                    id={`updateModal_${item.id}`}
                    data-bs-keyboard='false'
                    tabIndex={-1}
                    aria-labelledby='staticBackdropLabel'
                    aria-hidden='true'
                >
                    <div className='modal-dialog modal-dialog-centered'>
                        <div className='modal-content'>
                            <div className='modal-header'>
                                <h1 className='modal-title fs-5' id='staticBackdropLabel'>
                                    Update {category} : {item.name}
                                </h1>
                                <button
                                    type='button'
                                    className='btn-close'
                                    data-bs-dismiss='modal'
                                    aria-label='Close'
                                ></button>
                            </div>
                            <div className='modal-body text-start'>
                                <div className='mb-3'>
                                    <label htmlFor='new-group-name' className='col-form-label'>
                                        {category[0].toUpperCase() + category.slice(1)} name:
                                    </label>
                                    <input
                                        type='text'
                                        className='form-control'
                                        id='new-name'
                                        placeholder={item.name}
                                        onChange={(e) => setData({ ...data, name: e.target.value })}
                                    />
                                </div>
                                {category === 'option' && (
                                    <div>
                                        <select
                                            className='form-select mt-3'
                                            id='form-displayType'
                                            onChange={(e) => setData({ ...data, displayType: e.target.value })}
                                        >
                                            <option selected={item.displayType === 'color'} value='color'>
                                                Color
                                            </option>
                                            <option selected={item.displayType === 'number'} value='number'>
                                                Number
                                            </option>
                                            <option selected={item.displayType === 'text'} value='text'>
                                                Text
                                            </option>
                                        </select>
                                    </div>
                                )}
                                {(category === 'attribute' || category === 'option') && (
                                    <div className='mt-3'>
                                        <textarea
                                            className='form-control'
                                            placeholder={item.description}
                                            rows={4}
                                            onChange={(e) =>
                                                setData({
                                                    ...data,
                                                    description: e.target.value,
                                                })
                                            }
                                        />
                                    </div>
                                )}
                            </div>
                            <div className='modal-footer'>
                                {(category === 'group' || category === 'attribute') && (
                                    <button
                                        type='button'
                                        className='btn btn-warning'
                                        data-bs-dismiss='modal'
                                        onClick={(e) => onChangeGroupStatusHandler(e, item.id, item.status)}
                                    >
                                        {item.status.toLowerCase() === 'disabled' ? 'Enable' : 'Disable'}
                                    </button>
                                )}
                                <button
                                    type='button'
                                    className='btn btn-primary'
                                    data-bs-dismiss='modal'
                                    onClick={(e) => onUpdateHandler(e, data, item)}
                                >
                                    Update
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }

    return (
        <div className='table-responsive'>
            <table className='table'>
                <thead className='table-light'>
                    <tr>
                        <th scope='col'>STT</th>
                        {(firstRow || []).map((item: TableRowType) => (
                            <th scope='col' key={item.value}>
                                {item.value}
                            </th>
                        ))}
                    </tr>
                </thead>
                <tbody>
                    {(rows || []).map((item: any, index: any) => (
                        <tr key={index}>
                            {/* index column */}
                            <th>{index + 1}</th>
                            {/* data columns */}
                            {[...Array(firstRow.length - 1)].map((_, index) => (
                                <th
                                    scope='row'
                                    key={index}
                                    className={
                                        firstRow[index].fieldName.toString() === 'status'
                                            ? `text-${item[firstRow[index].fieldName.toString()].toLowerCase()}`
                                            : ''
                                    }
                                >
                                    {item[firstRow[index].fieldName.toString()]}
                                </th>
                            ))}
                            {/* action col */}
                            <td className='d-flex flex-row justify-content-center'>
                                {(category === 'group' || category === 'attribute') && item.status === 'REMOVED' ? (
                                    <></>
                                ) : (
                                    <>
                                        <UpdateModal item={item} />
                                        <p className='px-3'></p>
                                        <DeleteModal item={item} />
                                    </>
                                )}
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default CustomTable
