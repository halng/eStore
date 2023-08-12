import React from 'react'
import ReactDOM from 'react-dom'
import 'bootstrap/dist/css/bootstrap.css'
import './app.css'

import LogInForm from './components/LoginForm'
import RegisterForm from './components/RegisterForm'
const App = () => (
    <div className='auth-app'>
        <RegisterForm />
    </div>
)
ReactDOM.render(<App />, document.getElementById('app'))
