export type UserInfo = {
    username: string
    password: string
    isRemember?: boolean
}

export type AuthState = {
    isAuth: boolean
    username: string
    photoUrl: string
    role: string
    email: string
    id: string
}
