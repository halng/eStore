import { createSlice } from '@reduxjs/toolkit'
import type { PayloadAction } from '@reduxjs/toolkit'
import { AuthState } from '@types'

const initialState: AuthState = {
    isAuth: false,
    username: '',
    photoUrl: '',
    role: '',
    email: '',
    id: '',
}
export const authSlice = createSlice({
    name: 'auth',
    initialState,
    reducers: {
        setAuth: (state, action: PayloadAction<AuthState>) => {
            state.isAuth = action.payload.isAuth
            if (action.payload.isAuth) {
                state.username = action.payload.username
                state.photoUrl = action.payload.photoUrl
                state.role = action.payload.role
                state.email = action.payload.email
                state.id = action.payload.id
            }
        },
    },
})

export const { setAuth } = authSlice.actions
export const authReducer = authSlice.reducer
