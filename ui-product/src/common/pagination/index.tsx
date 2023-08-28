import React from 'react'

interface props {
    total: number
    currentPage: number
    totalPage: number
    setPage: React.Dispatch<React.SetStateAction<number>>
}

const Pagination = ({ total, currentPage, setPage, totalPage }: props) => {
    return (
        <div className='d-flex justify-content-between align-items-center'>
            <div className='summary'>Total: {total}</div>
            <div className='pagination'>
                <nav aria-label='...'>
                    <ul className='pagination'>
                        <li className={currentPage === 1 ? 'page-item disabled' : 'page-item'}>
                            <a role='button' className='page-link' onClick={() => setPage(currentPage - 1)}>
                                Previous
                            </a>
                        </li>
                        {[...Array(totalPage)].map((item, index) => (
                            <li className='page-item' key={index}>
                                <a
                                    role='button'
                                    onClick={() => setPage(index + 1)}
                                    className={currentPage === index + 1 ? 'page-link active' : 'page-link'}
                                >
                                    {index + 1}
                                </a>
                            </li>
                        ))}
                        <li className={totalPage === currentPage ? 'page-item disabled' : 'page-item'}>
                            <a role='button' className='page-link' onClick={() => setPage(currentPage + 1)}>
                                Next
                            </a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    )
}

export default Pagination
