import React from 'react'
import { Outlet } from 'react-router-dom'
import DashBoard from './DashBoard'

const MainBoard = () => {
    return (
        <div className='main-board d-flex'>
            <DashBoard />
            <div className='main-data'>
                <Outlet />
            </div>
        </div>
    )
}

export default MainBoard
