import React from "react";
import {Outlet} from 'react-router-dom'
import DashBoard from "./DashBoard";

const MainBoard = () => {
    return (
        <div className="main-board">
            <DashBoard />
            <Outlet />
        </div>
    )
}

export default MainBoard