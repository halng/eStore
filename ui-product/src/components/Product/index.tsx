import React from 'react'
import Navbar from '../../common/Navbar'
import CreateProduct from './actions/CreateProduct'

const Product = () => {
    return (
        <div className='product-board'>
            <Navbar />
            <div className='product-board'>
                <div className='d-flex justify-content-between pb-3'>
                    <div className='fs-3'>Product</div>
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
                            <input className='me-2 ps-3 py-2' type='text' placeholder='Search' aria-label='Search' />
                        </div>

                        <button
                            type='button'
                            className='btn btn-primary'
                            data-bs-toggle='offcanvas'
                            data-bs-target='#offcanvasRight'
                            aria-controls='offcanvasRight'
                        >
                            Create new product
                        </button>

                        <div
                            className='offcanvas offcanvas-end w-75'
                            tabIndex={-1}
                            id='offcanvasRight'
                            aria-labelledby='offcanvasRightLabel'
                        >
                            <div className='offcanvas-header'>
                                <h5 className='offcanvas-title' id='offcanvasRightLabel'>
                                    Create New Product:
                                </h5>
                                <button
                                    type='button'
                                    className='btn-close'
                                    data-bs-dismiss='offcanvas'
                                    aria-label='Close'
                                ></button>
                            </div>
                            <div className='offcanvas-body d-flex flex-column align-content-center'>
                                <CreateProduct />
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Product
