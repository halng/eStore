import React, { useEffect, useState } from 'react'
import Navbar from '../../common/Navbar'
import { ProductGroupType } from '../../types/ProductGroupType'
import { toast } from 'react-toastify'

import { ProductGroupAPI } from 'api-estore-v2'
import Pagination from '../../common/pagination'
import CustomTable from '../../common/CustomTable'

const ProductGroup = () => {
    const [groups, setGroups] = useState<ProductGroupType[]>([])
    const [tempGroups, setTempGroup] = useState<ProductGroupType[]>([])
    const [page, setPage] = useState<number>(1)
    const [totalPage, setTotalPage] = useState<number>(0)
    const [totalGroups, setTotalGroups] = useState<number>(0)
    const [newGroup, setNewGroup] = useState<string>('')
    const firstRow = [
        { value: 'Id', fieldName: 'id' },
        { value: 'Name', fieldName: 'name' },
        { value: 'Status', fieldName: 'status' },
        { value: 'Create Date', fieldName: 'createdDate' },
        { value: 'Update Date', fieldName: 'updatedDate' },
        { value: 'Actions', fieldName: '' },
    ]

    const getAllGroup = () => {
        ProductGroupAPI.getAll(page)
            .then((res) => {
                setGroups(res.data.groups)
                setTempGroup(res.data.groups)
                setTotalPage(res.data.totalPages)
                setTotalGroups(res.data.totalGroup)
            })
            .catch(() => {
                toast.error("Can't get all group! Try again later.")
            })
    }

    useEffect(() => {
        getAllGroup()
    }, [page])

    const deleteGroup = (e: any, groupId: string) => {
        ProductGroupAPI.deleteGroup(groupId)
            .then((res) => {
                toast.success(res.data.message)
                getAllGroup()
            })
            .catch((err) => {
                toast.error(err.response.data.msg)
            })
    }

    const updateGroup = (e: any, data: any, group: any) => {
        ProductGroupAPI.update(data['name'], group.id)
            .then((res) => {
                toast.success(res.data.message)
                getAllGroup()
            })
            .catch((err) => {
                console.log(err)
                toast.error('Cannot Update. Try later')
            })
    }

    const createGroup = () => {
        ProductGroupAPI.create(newGroup)
            .then((res) => {
                toast.success(res.data.message)
                getAllGroup()
                setNewGroup('')
            })
            .catch(() => {
                toast.error('Create new group failed. Try again later!')
            })
    }

    const changeStatus = (e: any, groupId: string, status: string) => {
        if (status === 'ENABLED') {
            ProductGroupAPI.disableGroup(groupId)
                .then((res) => {
                    toast.success(res.data.message)
                    getAllGroup()
                })
                .catch(() => {
                    toast.error('Disable group failed. Try again later!')
                })
        } else {
            ProductGroupAPI.enabledGroup(groupId)
                .then((res) => {
                    toast.success(res.data.message)
                    getAllGroup()
                })
                .catch(() => {
                    toast.error('Enable group failed. Try again later!')
                })
        }
    }

    const onSearchHandler = (e: any) => {
        const temp = tempGroups.filter((item) => item.name.includes(e.target.value))
        setGroups(temp)
    }

    return (
        <div className='product-group'>
            <Navbar />

            <div className='product-group-main'>
                <div className='d-flex justify-content-between pb-3'>
                    <div className='fs-3'>Product Group</div>
                    <div className='product-group-func d-flex justify-content-end align-items-center'>
                        <div className='search-box d-flex align-items-center me-3'>
                            <svg
                                xmlns='http://www.w3.org/2000/svg'
                                width='18'
                                height='18'
                                fill='currentColor'
                                className='bi bi-search'
                                viewBox='0 0 16 16'
                            >
                                <path d='M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z' />
                            </svg>
                            <input
                                className='me-2 ps-3 py-2'
                                type='text'
                                placeholder='Search in group name'
                                aria-label='Search'
                                onChange={(e) => onSearchHandler(e)}
                            />
                        </div>

                        <button
                            type='button'
                            className='btn btn-primary'
                            data-bs-toggle='offcanvas'
                            data-bs-target='#offcanvasRight'
                            aria-controls='offcanvasRight'
                        >
                            Create new group
                        </button>

                        <div
                            className='offcanvas offcanvas-end'
                            tabIndex={-1}
                            id='offcanvasRight'
                            aria-labelledby='offcanvasRightLabel'
                        >
                            <div className='offcanvas-header'>
                                <h5 className='offcanvas-title' id='offcanvasRightLabel'>
                                    Create New Group:
                                </h5>
                                <button
                                    type='button'
                                    className='btn-close'
                                    data-bs-dismiss='offcanvas'
                                    aria-label='Close'
                                ></button>
                            </div>
                            <div className='offcanvas-body d-flex flex-column align-content-center'>
                                <div>
                                    <label htmlFor='new-group-name' className='col-form-label'>
                                        New Group Name:
                                    </label>
                                    <input
                                        type='text'
                                        className='form-control'
                                        id='new-group-name'
                                        value={newGroup}
                                        onChange={(e) => setNewGroup(e.target.value)}
                                    />
                                </div>
                                <div className='pt-3 text-center'>
                                    <button
                                        type='button'
                                        className='btn btn-primary'
                                        data-bs-dismiss='offcanvas'
                                        onClick={createGroup}
                                    >
                                        Create
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <CustomTable
                    category={'group'}
                    firstRow={firstRow}
                    rows={groups}
                    onDeleteHandler={deleteGroup}
                    onUpdateHandler={updateGroup}
                    onChangeGroupStatusHandler={changeStatus}
                />

                {totalPage > 1 && (
                    <Pagination total={totalGroups} currentPage={page} totalPage={totalPage} setPage={setPage} />
                )}
            </div>
        </div>
    )
}

export default ProductGroup
