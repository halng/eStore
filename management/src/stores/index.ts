import { combineReducers, configureStore } from '@reduxjs/toolkit'
import { useDispatch, TypedUseSelectorHook, useSelector } from 'react-redux'
import { authReducer } from './authSlice'
import { persistReducer } from 'redux-persist'
import storage from 'redux-persist/lib/storage'

// config which key we want to persist
const authPersistConfig = {
    key: 'auth',
    storage,
    whitelist: ['isAuth', 'username', 'photoUrl', 'role', 'email', 'id'],
}

const rootReducer = combineReducers({
    auth: persistReducer(authPersistConfig, authReducer),
})

export const setupStore = (preloadedState?: Partial<RootState>) => {
    return configureStore({
        reducer: rootReducer,
        preloadedState,
    })
}

export type RootState = ReturnType<typeof rootReducer>
export type AppStore = ReturnType<typeof setupStore>
export type AppDispatch = AppStore['dispatch']
export const useAppDispatch = () => useDispatch<AppDispatch>()
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector
