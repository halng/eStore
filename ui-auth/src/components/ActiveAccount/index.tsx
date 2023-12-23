import AuthAPI from 'api-estore-v2/dist/esm/api/auth'
import React, { useEffect, useState } from 'react'
import { useSearchParams } from 'react-router-dom'
import './style.css'
import { LoadingStatus } from '../../model/Constants'
import { toast } from 'react-toastify'

const ActiveAccount = () => {
    const [params] = useSearchParams()
    const [loadingStatus, setLoadingStatus] = useState<LoadingStatus>(LoadingStatus.LOADING)

    useEffect(() => {
        const token = params.get('token')
        const email = params.get('email')
        if (token && email) {
            AuthAPI.activeAccount(token, email)
                .then(() => {
                    setLoadingStatus(LoadingStatus.SUCCESS)
                })
                .catch((err) => {
                    if (err.response.status === 400) {
                        toast.info(err.response.data.message)
                        setLoadingStatus(LoadingStatus.SUCCESS)
                    } else {
                        setLoadingStatus(LoadingStatus.ERROR)
                    }
                })
        }
    }, [params])

    if (loadingStatus === LoadingStatus.LOADING) {
        return (
            <div className='background-active-account w-100'>
                <div className='spinner-grow text-primary' role='status'>
                    <span className='visually-hidden'>Loading...</span>
                </div>
            </div>
        )
    } else if (loadingStatus === LoadingStatus.SUCCESS) {
        return (
            <div className='background-active-account w-100'>
                <div className='alert alert-success' role='alert'>
                    Account activated successfully. Click <a href='http://localhost:3000/auth/login'>here</a> to go to
                    login page!
                </div>
            </div>
        )
    } else {
        return (
            <div className='background-active-account w-100'>
                <div className='alert alert-danger' role='alert'>
                    We cannot activate your account. Please try again later!
                </div>
            </div>
        )
    }
}

export default ActiveAccount
