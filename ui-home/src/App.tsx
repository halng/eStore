import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.min.js'

import Layout from './layout'

const App = () => (
    <div className='app'>
        <Layout />
    </div>
)
ReactDOM.render(<App />, document.getElementById('app'))
