'use client'
import React, { useState } from 'react'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'
import { UserInfo } from '@types'

import './style.css'
import { Auth } from 'api-estore-v2'
import Message from '@message'
import LoadingStatus from '@status'
import { toast } from 'react-toastify'
import 'bootstrap/dist/css/bootstrap.css'
import { useAppDispatch, useAppSelector } from '@stores'
import { setAuth } from '../stores/authSlice'
import { redirect } from 'next/navigation'

const schema = yup.object({
    username: yup.string().required('Username is required'),
    password: yup.string().required('Password is required'),
    isRemember: yup.boolean(),
})

const LogIn = () => {
    const [isRemember, setIsRemember] = useState(false)
    const [status, setStatus] = useState<LoadingStatus>(LoadingStatus.NOPE)
    const dispatch = useAppDispatch()
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<UserInfo | any>({
        resolver: yupResolver(schema),
    })

    const handleFormSubmit = async (data: UserInfo) => {
        const resData = {
            username: data.username,
            password: data.password,
        }
        Auth.login(resData)
            .then((res) => {
                // handle data to set for auth state
                const { accountId, email, role, username, photoUrl } = res.data
                const authData = { id: accountId, email, role, username, photoUrl, isAuth: true }
                dispatch(setAuth(authData))
                toast.success(Message.LOGIN.SUCCESS)

                if (res.data.role === 'SELLER') {
                    redirect('/partner')
                } else if (res.data.role === 'ADMIN' || res.data.role === 'SUPER_ADMIN' || res.data.role === 'STAFF') {
                    redirect('/management')
                }
            })
            .catch((err) => {
                if (err.response.status === 401) {
                    setStatus(LoadingStatus.INVALID)
                } else {
                    setStatus(LoadingStatus.ERROR)
                }
            })
    }
    return (
        <div className='background-radial-gradient overflow-hidden h-100'>
            <div className='container px-4 py-5 px-md-5 text-center text-lg-start h-100 d-flex'>
                <div className='row gx-lg-5 align-items-center mb-5'>
                    <div className='col-lg-6 mb-5 mb-lg-0 intro'>
                        <h1 className='my-5 display-5 fw-bold ls-tight title-color'>
                            The best offer <br />
                            <span className='title-color-embrace'>for your business</span>
                        </h1>
                        <p className='mb-4 opacity-70 title-color-description'>
                            Lorem ipsum dolor, sit amet consectetur adipisicing elit. Temporibus, expedita iusto veniam
                            atque, magni tempora mollitia dolorum consequatur nulla, neque debitis eos reprehenderit
                            quasi ab ipsum nisi dolorem modi. Quos?
                        </p>
                    </div>

                    <div className='col-lg-6 mb-5 mb-lg-0 position-relative'>
                        <div id='radius-shape-1' className='position-absolute rounded-circle shadow-5-strong'></div>
                        <div id='radius-shape-2' className='position-absolute shadow-5-strong'></div>

                        <div className='card bg-glass'>
                            <div className='card-body px-4 py-5 px-md-5'>
                                <form onSubmit={handleSubmit(handleFormSubmit)}>
                                    <div className='mb-3 text-center'>
                                        <h3>Welcome</h3>
                                        {status === LoadingStatus.INVALID && (
                                            <p className='text-danger'>{Message.LOGIN.INVALID}</p>
                                        )}
                                    </div>

                                    {/* <!-- username input --> */}
                                    <div className='form-outline mb-4'>
                                        <label className='form-label' htmlFor='form3Example3'>
                                            Username:
                                        </label>
                                        <input
                                            {...register('username')}
                                            type='text'
                                            id='form3Example3'
                                            className='form-control'
                                        />
                                        <p className='fst-italic text-danger'>{errors.username?.message?.toString()}</p>
                                    </div>

                                    {/* <!-- Password input --> */}
                                    <div className='form-outline mb-4'>
                                        <label className='form-label' htmlFor='form3Example4'>
                                            Password
                                        </label>
                                        <input
                                            {...register('password')}
                                            type='password'
                                            id='form3Example4'
                                            className='form-control'
                                        />
                                        <p className='fst-italic text-danger'>{errors.password?.message?.toString()}</p>
                                    </div>

                                    {/* <!-- Checkbox --> */}
                                    <div className='form-check d-flex justify-content-around mb-4'>
                                        <div>
                                            <input
                                                className='form-check-input me-2'
                                                type='checkbox'
                                                value=''
                                                id='form2Example33'
                                                checked={isRemember}
                                                {...register('isRemember')}
                                                onClick={() => setIsRemember(!isRemember)}
                                            />
                                            <label className='form-check-label' htmlFor='form2Example33'>
                                                Remember me
                                            </label>
                                        </div>
                                        <div>
                                            <a
                                                className='link-offset-2 link-offset-3-hover link-underline link-underline-opacity-0 link-underline-opacity-75-hover'
                                                href='#'
                                            >
                                                Forgot Password?
                                            </a>
                                        </div>
                                    </div>

                                    {/* <!-- Submit button --> */}
                                    <div className='d-flex justify-content-center'>
                                        <button type='submit' className='btn btn-primary btn-block mb-4 px-5'>
                                            Login
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

const Home = () => {
    const authData = useAppSelector((state) => state.auth)
    if (authData.isAuth) {
        if (authData.role === 'SELLER') {
            redirect('/partner')
        } else {
            redirect('/management')
        }
    } else {
        return <LogIn />
    }
}

export default Home
