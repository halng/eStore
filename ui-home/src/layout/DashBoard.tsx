import React from 'react'
import { Link } from 'react-router-dom'

const DashBoard = () => {
    return (
        <div className='dashboard d-flex flex-column justify-content-between'>
            <div className='store-dashboard'>
                <div className='accordion accordion-flush' id='accordionFlushExample'>
                    <div className='accordion-item'>
                        <h2 className='accordion-header' id='flush-headingOne'>
                            <Link to={'/'} className='accordion-button collapsed fs-4 menu-item' role='button'>
                                Main Board
                            </Link>
                        </h2>
                    </div>
                    <div className='accordion-item'>
                        <h2 className='accordion-header' id='flush-headingOne'>
                            <button
                                className='accordion-button collapsed fs-4'
                                type='button'
                                data-bs-toggle='collapse'
                                data-bs-target='#flush-collapseOne'
                                aria-expanded='false'
                                aria-controls='flush-collapseOne'
                            >
                                Product
                            </button>
                        </h2>
                        <div
                            id='flush-collapseOne'
                            className='accordion-collapse collapse'
                            aria-labelledby='flush-headingOne'
                            data-bs-parent='#accordionFlushExample'
                        >
                            <div className='accordion-body'>
                                <Link
                                    to={'product/statistic'}
                                    className='menu-item fs-5 btn btn-outline-primary'
                                    role='button'
                                >
                                    Statistic
                                </Link>
                                <Link
                                    to={'product/all'}
                                    className='menu-item fs-5 btn btn-outline-primary'
                                    role='button'
                                >
                                    All product
                                </Link>
                                <Link
                                    to={'product/attribute'}
                                    className='menu-item fs-5 btn btn-outline-primary'
                                    role='button'
                                >
                                    Product Attribute
                                </Link>
                                <Link
                                    to={'product/group'}
                                    className='menu-item fs-5 btn btn-outline-primary'
                                    role='button'
                                >
                                    Product Group
                                </Link>
                                <Link
                                    to={'product/option'}
                                    className='menu-item fs-5 btn btn-outline-primary'
                                    role='button'
                                >
                                    Product Option
                                </Link>
                            </div>
                        </div>
                    </div>
                    <div className='accordion-item'>
                        <h2 className='accordion-header' id='flush-headingTwo'>
                            <button
                                className='accordion-button collapsed fs-4'
                                type='button'
                                data-bs-toggle='collapse'
                                data-bs-target='#flush-collapseTwo'
                                aria-expanded='false'
                                aria-controls='flush-collapseTwo'
                            >
                                Order
                            </button>
                        </h2>
                        <div
                            id='flush-collapseTwo'
                            className='accordion-collapse collapse'
                            aria-labelledby='flush-headingTwo'
                            data-bs-parent='#accordionFlushExample'
                        >
                            <div className='accordion-body'>
                                <Link to={'order/new'} className='menu-item fs-5 btn btn-outline-primary' role='button'>
                                    New Order
                                </Link>
                                <Link to={'order/so'} className='menu-item fs-5 btn btn-outline-primary' role='button'>
                                    So on...
                                </Link>
                            </div>
                        </div>
                    </div>
                    <div className='accordion-item'>
                        <h2 className='accordion-header' id='flush-headingThree'>
                            <button
                                className='accordion-button collapsed fs-4'
                                type='button'
                                data-bs-toggle='collapse'
                                data-bs-target='#flush-collapseThree'
                                aria-expanded='false'
                                aria-controls='flush-collapseThree'
                            >
                                Customer
                            </button>
                        </h2>
                        <div
                            id='flush-collapseThree'
                            className='accordion-collapse collapse'
                            aria-labelledby='flush-headingThree'
                            data-bs-parent='#accordionFlushExample'
                        >
                            <div className='accordion-body'>
                                <Link
                                    to={'customer/message'}
                                    className='menu-item fs-5 btn btn-outline-primary'
                                    role='button'
                                >
                                    Message
                                </Link>
                                <Link
                                    to={'customer/policy'}
                                    className='menu-item fs-5 btn btn-outline-primary'
                                    role='button'
                                >
                                    Return
                                </Link>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div className='account-dashboard'>
                <div className='account-info'>
                    <div className='account-name fs-3'>own name</div>
                    <div className='account-gmail fs-5'>own email@gmail.com</div>
                </div>
                <div className='btn-func d-flex justify-content-center align-items-center'>
                    <Link
                        to={'/auth/login'}
                        type='button'
                        className='menu-item btn btn-outline-primary btn-sign-out'
                        role='button'
                    >
                        <svg
                            xmlns='http://www.w3.org/2000/svg'
                            width='16'
                            height='16'
                            fill='currentColor'
                            className='bi bi-box-arrow-left'
                            viewBox='0 0 16 16'
                        >
                            <path
                                fillRule='evenodd'
                                d='M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z'
                            />
                            <path
                                fillRule='evenodd'
                                d='M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z'
                            />
                        </svg>
                    </Link>

                    <Link to={'setting'} className='menu-item btn btn-outline-primary btn-sign-out' role='button'>
                        <svg
                            xmlns='http://www.w3.org/2000/svg'
                            width='16'
                            height='16'
                            fill='currentColor'
                            className='bi bi-gear'
                            viewBox='0 0 16 16'
                        >
                            <path d='M8 4.754a3.246 3.246 0 1 0 0 6.492 3.246 3.246 0 0 0 0-6.492zM5.754 8a2.246 2.246 0 1 1 4.492 0 2.246 2.246 0 0 1-4.492 0z' />
                            <path d='M9.796 1.343c-.527-1.79-3.065-1.79-3.592 0l-.094.319a.873.873 0 0 1-1.255.52l-.292-.16c-1.64-.892-3.433.902-2.54 2.541l.159.292a.873.873 0 0 1-.52 1.255l-.319.094c-1.79.527-1.79 3.065 0 3.592l.319.094a.873.873 0 0 1 .52 1.255l-.16.292c-.892 1.64.901 3.434 2.541 2.54l.292-.159a.873.873 0 0 1 1.255.52l.094.319c.527 1.79 3.065 1.79 3.592 0l.094-.319a.873.873 0 0 1 1.255-.52l.292.16c1.64.893 3.434-.902 2.54-2.541l-.159-.292a.873.873 0 0 1 .52-1.255l.319-.094c1.79-.527 1.79-3.065 0-3.592l-.319-.094a.873.873 0 0 1-.52-1.255l.16-.292c.893-1.64-.902-3.433-2.541-2.54l-.292.159a.873.873 0 0 1-1.255-.52l-.094-.319zm-2.633.283c.246-.835 1.428-.835 1.674 0l.094.319a1.873 1.873 0 0 0 2.693 1.115l.291-.16c.764-.415 1.6.42 1.184 1.185l-.159.292a1.873 1.873 0 0 0 1.116 2.692l.318.094c.835.246.835 1.428 0 1.674l-.319.094a1.873 1.873 0 0 0-1.115 2.693l.16.291c.415.764-.42 1.6-1.185 1.184l-.291-.159a1.873 1.873 0 0 0-2.693 1.116l-.094.318c-.246.835-1.428.835-1.674 0l-.094-.319a1.873 1.873 0 0 0-2.692-1.115l-.292.16c-.764.415-1.6-.42-1.184-1.185l.159-.291A1.873 1.873 0 0 0 1.945 8.93l-.319-.094c-.835-.246-.835-1.428 0-1.674l.319-.094A1.873 1.873 0 0 0 3.06 4.377l-.16-.292c-.415-.764.42-1.6 1.185-1.184l.292.159a1.873 1.873 0 0 0 2.692-1.115l.094-.319z' />
                        </svg>
                    </Link>

                    <Link to={'support'} className='menu-item btn btn-outline-primary btn-sign-out' role='button'>
                        <svg
                            xmlns='http://www.w3.org/2000/svg'
                            width='16'
                            height='16'
                            fill='currentColor'
                            className='bi bi-question-octagon'
                            viewBox='0 0 16 16'
                        >
                            <path d='M4.54.146A.5.5 0 0 1 4.893 0h6.214a.5.5 0 0 1 .353.146l4.394 4.394a.5.5 0 0 1 .146.353v6.214a.5.5 0 0 1-.146.353l-4.394 4.394a.5.5 0 0 1-.353.146H4.893a.5.5 0 0 1-.353-.146L.146 11.46A.5.5 0 0 1 0 11.107V4.893a.5.5 0 0 1 .146-.353L4.54.146zM5.1 1 1 5.1v5.8L5.1 15h5.8l4.1-4.1V5.1L10.9 1H5.1z' />
                            <path d='M5.255 5.786a.237.237 0 0 0 .241.247h.825c.138 0 .248-.113.266-.25.09-.656.54-1.134 1.342-1.134.686 0 1.314.343 1.314 1.168 0 .635-.374.927-.965 1.371-.673.489-1.206 1.06-1.168 1.987l.003.217a.25.25 0 0 0 .25.246h.811a.25.25 0 0 0 .25-.25v-.105c0-.718.273-.927 1.01-1.486.609-.463 1.244-.977 1.244-2.056 0-1.511-1.276-2.241-2.673-2.241-1.267 0-2.655.59-2.75 2.286zm1.557 5.763c0 .533.425.927 1.01.927.609 0 1.028-.394 1.028-.927 0-.552-.42-.94-1.029-.94-.584 0-1.009.388-1.009.94z' />
                        </svg>
                    </Link>
                </div>
            </div>
        </div>
    )
}

export default DashBoard
