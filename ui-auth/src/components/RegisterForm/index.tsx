import React from 'react'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import * as yup from 'yup'
import { Auth } from 'api-estore-v2'

import { UserRegister } from '../../model'

import '../LoginForm/style.css'

const schema = yup.object({
    username: yup.string().min(4).max(20).required('Please enter your username'),
    email: yup.string().email().required('Please enter your email'),
    password: yup
        .string()
        .matches(
            /^.*(?=.{8,})((?=.*[!@#$%^&*()\-_=+{};:,<.>]){1})(?=.*\d)((?=.*[a-z]){1})((?=.*[A-Z]){1}).*$/,
            'Password must contain at least 8 characters, one uppercase, one number and one special case character',
        )
        .required('Please enter your password'),
    rePassword: yup
        .string()
        .oneOf([yup.ref('password')], "Password doesn't match")
        .required('Please confirm your password'),
})

const RegisterForm = () => {
    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm<UserRegister | any>({
        resolver: yupResolver(schema),
    })

    const handleFormSubmit = async (data: UserRegister) => {
        const resData = {
            username: data.username,
            password: data.password,
            email: data.email,
            rePassword: data.rePassword,
            role: 2,
        }

        Auth.register(resData)
            .then((res) => {
                console.log(res)
            })
            .catch((err) => {
                console.log(err)
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
                                    </div>

                                    {/* <!-- Username input --> */}
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
                                        <p className='fst-italic text-danger'>{errors.username?.message}</p>
                                    </div>

                                    {/* <!-- Email input --> */}
                                    <div className='form-outline mb-4'>
                                        <label className='form-label' htmlFor='form3Example3'>
                                            Email address:
                                        </label>
                                        <input
                                            {...register('email')}
                                            type='email'
                                            id='form3Example3'
                                            className='form-control'
                                        />
                                        <p className='fst-italic text-danger'>{errors.email?.message}</p>
                                    </div>

                                    {/* <!-- Password input --> */}
                                    <div className='form-outline mb-4'>
                                        <label className='form-label' htmlFor='form3Example4'>
                                            Password:
                                        </label>
                                        <input
                                            {...register('password')}
                                            type='password'
                                            id='form3Example4'
                                            className='form-control'
                                        />
                                        <p className='fst-italic text-danger'>{errors.password?.message}</p>
                                    </div>

                                    {/* <!-- Confirm password input --> */}
                                    <div className='form-outline mb-4'>
                                        <label className='form-label' htmlFor='form3Example4'>
                                            Confirm password:
                                        </label>
                                        <input
                                            {...register('rePassword')}
                                            type='password'
                                            id='form3Example4'
                                            className='form-control'
                                        />
                                        <p className='fst-italic text-danger'>{errors.rePassword?.message}</p>
                                    </div>

                                    {/* <!-- Submit button --> */}
                                    <div className='d-flex justify-content-center'>
                                        <button type='submit' className='btn btn-primary btn-block mb-4 px-5'>
                                            Register
                                        </button>
                                    </div>

                                    {/* <!-- Register buttons --> */}
                                    <div className='text-center'>
                                        <p>
                                            Already have an account?{' '}
                                            <a
                                                className='link-offset-2 link-offset-3-hover link-underline link-underline-opacity-0 link-underline-opacity-75-hover'
                                                href='/auth/login'
                                            >
                                                Log In
                                            </a>
                                        </p>
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

export default RegisterForm
