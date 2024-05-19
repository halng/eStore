import { authReducer } from './authSlice'
import '@testing-library/jest-dom'

describe('test AuthSlice', () => {
    it('should return the initial state', () => {
        expect(authReducer(undefined, { type: '', payload: '' })).toEqual({
            isAuth: false,
            username: '',
            photoUrl: '',
            role: '',
            email: '',
            id: '',
        })
    }),
        it('should handle setAuth when authenticated', () => {
            const data = {
                isAuth: true,
                username: 'username',
                photoUrl: 'https://www.google.com',
                role: 'SELLER',
                email: 'email@gmail',
                id: '123',
            }
            expect(authReducer(undefined, { type: 'auth/setAuth', payload: data })).toEqual(data)
        }),
        it('should handle setAuth when not authenticated', () => {
            const data = {
                isAuth: false,
                username: '',
                photoUrl: '',
                role: '',
                email: '',
                id: '',
            }
            expect(authReducer(undefined, { type: 'auth/setAuth', payload: data })).toEqual(data)
        })
})
