import React from 'react'
import { Routes, Route } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.css'
import './app.css'

import LogInForm from './components/LoginForm'
import RegisterForm from './components/RegisterForm'
import ActiveAccount from './components/ActiveAccount'
import { ToastContainer } from 'react-toastify'

import 'react-toastify/dist/ReactToastify.css'

const App = () => (
    // <BrowserRouter basename='/auth'>
    <div className='h-100'>
        <Routes>
            <Route path='login' element={<LogInForm />} />
            <Route path='register' element={<RegisterForm />} />
            <Route path='active-account' element={<ActiveAccount />} />
        </Routes>
        <ToastContainer />
    </div>
    // </BrowserRouter>
)

export default App
// ReactDOM.render(<App />, document.getElementById('app'))
