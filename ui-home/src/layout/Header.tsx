import React from 'react'

const Header = () => {
    return (
        <div className='header d-flex flex-row w-100 align-items-center'>
            {/* icon and app name */}
            <div className='header-item app-info d-flex flex-row justify-content-center align-items-center'>
                <div className='logo me-3'></div>
                <div className='app-name fs-1'>eStore</div>
            </div>

            <div className='header-item store-info d-flex flex-row justify-content-between p-2'>
                <div className='store-item store-search d-flex align-items-center'>
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

                <div className='store-item d-flex flex-row align-items-center'>
                    {/* 
                    waiting
                    package
                    shipping
                    done */}

                    <button type='button' className='btn btn-outline-primary d-flex align-items-center p-2 me-3'>
                        <svg
                            xmlns='http://www.w3.org/2000/svg'
                            width='18'
                            height='18'
                            fill='currentColor'
                            className='bi bi-hourglass-split'
                            viewBox='0 0 18 18'
                        >
                            <path d='M2.5 15a.5.5 0 1 1 0-1h1v-1a4.5 4.5 0 0 1 2.557-4.06c.29-.139.443-.377.443-.59v-.7c0-.213-.154-.451-.443-.59A4.5 4.5 0 0 1 3.5 3V2h-1a.5.5 0 0 1 0-1h11a.5.5 0 0 1 0 1h-1v1a4.5 4.5 0 0 1-2.557 4.06c-.29.139-.443.377-.443.59v.7c0 .213.154.451.443.59A4.5 4.5 0 0 1 12.5 13v1h1a.5.5 0 0 1 0 1h-11zm2-13v1c0 .537.12 1.045.337 1.5h6.326c.216-.455.337-.963.337-1.5V2h-7zm3 6.35c0 .701-.478 1.236-1.011 1.492A3.5 3.5 0 0 0 4.5 13s.866-1.299 3-1.48V8.35zm1 0v3.17c2.134.181 3 1.48 3 1.48a3.5 3.5 0 0 0-1.989-3.158C8.978 9.586 8.5 9.052 8.5 8.351z' />
                        </svg>
                        <span className='ps-2'>22</span>
                    </button>

                    <button type='button' className='btn btn-outline-primary d-flex align-items-center p-2 me-3'>
                        <svg
                            xmlns='http://www.w3.org/2000/svg'
                            width='18'
                            height='18'
                            fill='currentColor'
                            className='bi bi-box'
                            viewBox='0 0 18 18'
                        >
                            <path d='M8.186 1.113a.5.5 0 0 0-.372 0L1.846 3.5 8 5.961 14.154 3.5 8.186 1.113zM15 4.239l-6.5 2.6v7.922l6.5-2.6V4.24zM7.5 14.762V6.838L1 4.239v7.923l6.5 2.6zM7.443.184a1.5 1.5 0 0 1 1.114 0l7.129 2.852A.5.5 0 0 1 16 3.5v8.662a1 1 0 0 1-.629.928l-7.185 2.874a.5.5 0 0 1-.372 0L.63 13.09a1 1 0 0 1-.63-.928V3.5a.5.5 0 0 1 .314-.464L7.443.184z' />
                        </svg>
                        <span className='ps-2'>22</span>
                    </button>

                    <button type='button' className='btn btn-outline-primary d-flex align-items-center p-2 me-3'>
                        <svg
                            xmlns='http://www.w3.org/2000/svg'
                            width='16'
                            height='16'
                            fill='currentColor'
                            className='bi bi-truck'
                            viewBox='0 0 16 16'
                        >
                            <path d='M0 3.5A1.5 1.5 0 0 1 1.5 2h9A1.5 1.5 0 0 1 12 3.5V5h1.02a1.5 1.5 0 0 1 1.17.563l1.481 1.85a1.5 1.5 0 0 1 .329.938V10.5a1.5 1.5 0 0 1-1.5 1.5H14a2 2 0 1 1-4 0H5a2 2 0 1 1-3.998-.085A1.5 1.5 0 0 1 0 10.5v-7zm1.294 7.456A1.999 1.999 0 0 1 4.732 11h5.536a2.01 2.01 0 0 1 .732-.732V3.5a.5.5 0 0 0-.5-.5h-9a.5.5 0 0 0-.5.5v7a.5.5 0 0 0 .294.456zM12 10a2 2 0 0 1 1.732 1h.768a.5.5 0 0 0 .5-.5V8.35a.5.5 0 0 0-.11-.312l-1.48-1.85A.5.5 0 0 0 13.02 6H12v4zm-9 1a1 1 0 1 0 0 2 1 1 0 0 0 0-2zm9 0a1 1 0 1 0 0 2 1 1 0 0 0 0-2z' />
                        </svg>
                        <span className='ps-2'>22</span>
                    </button>

                    <button type='button' className='btn btn-outline-primary d-flex align-items-center p-2 me-3'>
                        <svg
                            xmlns='http://www.w3.org/2000/svg'
                            width='16'
                            height='16'
                            fill='currentColor'
                            className='bi bi-check-square'
                            viewBox='0 0 16 16'
                        >
                            <path d='M14 1a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z' />
                            <path d='M10.97 4.97a.75.75 0 0 1 1.071 1.05l-3.992 4.99a.75.75 0 0 1-1.08.02L4.324 8.384a.75.75 0 1 1 1.06-1.06l2.094 2.093 3.473-4.425a.235.235 0 0 1 .02-.022z' />
                        </svg>
                        <span className='ps-2'>22</span>
                    </button>

                    {/* Notification button */}
                    <button type='button' className='btn btn-outline-primary position-relative p-2 me-3'>
                        <svg
                            xmlns='http://www.w3.org/2000/svg'
                            width='18'
                            height='18'
                            fill='currentColor'
                            className='bi bi-bell-fill'
                            viewBox='0 0 18 18'
                        >
                            <path d='M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zm.995-14.901a1 1 0 1 0-1.99 0A5.002 5.002 0 0 0 3 6c0 1.098-.5 6-2 7h14c-1.5-1-2-5.902-2-7 0-2.42-1.72-4.44-4.005-4.901z' />
                        </svg>
                        <span className='position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger'>
                            99+
                        </span>
                    </button>

                    {/* store name */}
                    <div className="store-name fs-2 ps-3 ms-2">
                        Test store
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Header
