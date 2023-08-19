import React from 'react'
import ReactDOM from 'react-dom'
import {BrowserRouter, Routes, Route} from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.css'
import './app.css'

import LogInForm from './components/LoginForm'
import RegisterForm from './components/RegisterForm'

const App = () => (
    // <BrowserRouter basename='/auth'>
        <Routes>
            <Route path='login' element= {<LogInForm />} />
            <Route path='register' element= {<RegisterForm />} />
        </Routes>
    // </BrowserRouter>
)

export default App;
// ReactDOM.render(<App />, document.getElementById('app'))
