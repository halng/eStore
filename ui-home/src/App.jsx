import React, { StrictMode } from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import { Route, Routes, BrowserRouter } from 'react-router-dom'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.min.js'

const AuthApp = React.lazy(() => import('UIAuth/AuthApp'))
const ProductApp = React.lazy(() => import('UIProduct/ProductApp'))

import Layout from './layout'

const App = () => (
    <div className='app'>
        <React.Suspense fallback='Loading'>
            <BrowserRouter>
                <Routes>
                    <Route path='' element={<Layout />}>
                        <Route path='/product/*' element={<ProductApp />}></Route>
                    </Route>
                    <Route path='/auth/*' element={<AuthApp />} />
                </Routes>
            </BrowserRouter>
        </React.Suspense>
    </div>
)
ReactDOM.render(
    <StrictMode>
        <App />
    </StrictMode>,
    document.getElementById('app'),
)
