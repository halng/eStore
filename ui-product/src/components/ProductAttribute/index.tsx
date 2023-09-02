import React, { useEffect, useState } from 'react'
import Navbar from '../../common/Navbar'
import { toast } from 'react-toastify'
import { ProductAttributeCreateType, ProductAttributeType } from '../../types/ProductAttributeType'

import { ProductAttributeAPI } from 'api-estore-v2'
import Pagination from '../../common/pagination'
import CustomTable from '../../common/CustomTable'

const ProductAttribute = () => {
    const [attributes, setAttributes] = useState<ProductAttributeType[]>([])
    const [tempAttributes, setTempAttributes] = useState<ProductAttributeType[]>([])
    const [page, setPage] = useState<number>(1)
    const [totalPage, setTotalPage] = useState<number>(0)
    const [totalAttribute, setTotalAttribute] = useState<number>(0)
    const [newAtt, setNewAtt] = useState<ProductAttributeCreateType>({
        name: '',
        description: '',
    })
    const firstRow = [
        { value: 'Id', fieldName: 'id' },
        { value: 'Name', fieldName: 'name' },
        { value: 'Description', fieldName: 'description' },
        { value: 'Status', fieldName: 'status' },
        { value: 'Last Update Date', fieldName: 'lastUpdateDate' },
        { value: 'Actions', fieldName: '' },
    ]

    const getAllAttribute = () => {
        ProductAttributeAPI.getAll(page)
            .then((res) => {
                setAttributes(res.data.attributes)
                setTempAttributes(res.data.attributes)
                setTotalPage(res.data.totalPages)
                setTotalAttribute(res.data.totalAttributes)
            })
            .catch(() => {
                toast.error("Can't get all attribute! Try again later.")
            })
    }

    useEffect(() => {
        getAllAttribute()
    }, [page])

    const deleteAttribute = (e: any, attId: string) => {
        ProductAttributeAPI.deleteAttribute(attId)
            .then((res) => {
                toast.success(res.data.message)
                getAllAttribute()
            })
            .catch((err) => {
                toast.error(err.response.data.msg)
            })
    }

    const updateAttribute = (e: any, data: any, att: any) => {
        const body = {
            name: data['name'] ? data['name'] : att.name,
            description: data['description'] ? data['description'] : att.description,
        }
        ProductAttributeAPI.update(body, att.id)
            .then((res) => {
                toast.success(res.data.message)
                getAllAttribute()
            })
            .catch((err) => {
                console.log(err)
                toast.error('Cannot Update. Try later')
            })
    }

    const createAttribute = () => {
        ProductAttributeAPI.create(newAtt)
            .then((res) => {
                toast.success(res.data.message)
                getAllAttribute()
                setNewAtt({ name: '', description: '' })
            })
            .catch(() => {
                toast.error('Create new group failed. Try again later!')
            })
    }

    const changeStatus = (e: any, attId: string, status: string) => {
        if (status === 'ENABLED') {
            ProductAttributeAPI.disableAttribute(attId)
                .then((res) => {
                    toast.success(res.data.message)
                    getAllAttribute()
                })
                .catch(() => {
                    toast.error('Disable group failed. Try again later!')
                })
        } else {
            ProductAttributeAPI.enabledAttribute(attId)
                .then((res) => {
                    toast.success(res.data.message)
                    getAllAttribute()
                })
                .catch(() => {
                    toast.error('Enable group failed. Try again later!')
                })
        }
    }

    const onSearchHandler = (e: any) => {
        const temp = tempAttributes.filter((item) => item.name.includes(e.target.value))
        setAttributes(temp)
    }

    return (
        <div className='product-group'>
            <Navbar />

            <div className='product-group-main'>
                <div className='d-flex justify-content-between pb-3'>
                    <div className='fs-3'>Product Attribute</div>
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
                                placeholder='Search'
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
                            Create new attribute
                        </button>

                        <div
                            className='offcanvas offcanvas-end'
                            tabIndex={-1}
                            id='offcanvasRight'
                            aria-labelledby='offcanvasRightLabel'
                        >
                            <div className='offcanvas-header'>
                                <h5 className='offcanvas-title' id='offcanvasRightLabel'>
                                    Create New Attribute:
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
                                        New Attribute Name:
                                    </label>
                                    <input
                                        type='text'
                                        className='form-control'
                                        id='new-group-name'
                                        value={newAtt.name}
                                        onChange={(e) => setNewAtt({ ...newAtt, name: e.target.value })}
                                    />
                                </div>
                                <div className='mt-3'>
                                    <textarea
                                        className='form-control'
                                        placeholder='Description for new options'
                                        rows={4}
                                        value={newAtt.description}
                                        onChange={(e) =>
                                            setNewAtt({
                                                ...newAtt,
                                                description: e.target.value,
                                            })
                                        }
                                    />
                                </div>
                                <div className='pt-3 text-center'>
                                    <button
                                        type='button'
                                        className='btn btn-primary'
                                        data-bs-dismiss='offcanvas'
                                        onClick={createAttribute}
                                    >
                                        Create
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <CustomTable
                    category={'attribute'}
                    firstRow={firstRow}
                    rows={attributes}
                    onDeleteHandler={deleteAttribute}
                    onUpdateHandler={updateAttribute}
                    onChangeGroupStatusHandler={changeStatus}
                />

                {totalPage > 1 && (
                    <Pagination total={totalAttribute} currentPage={page} totalPage={totalPage} setPage={setPage} />
                )}
            </div>
        </div>
    )
}

export default ProductAttribute
