enum LoadingStatus {
    NOPE,
    LOADING,
    SUCCESS,
    ERROR,
    INVALID,
}

const MESSAGE = {
    LOGIN: {
        SUCCESS: 'Login Successfully. Welcome back!',
        ERROR: 'Something wrong with your account. Please try again later',
        INVALID: 'Incorrect Username or Password',
    },
    REGISTER: {
        SUCCESS: 'Register Successfully. Please check your email to activate your account.',
        ERROR: 'Something wrong with your account. Please try again',
        INVALID: 'Some fields are incorrect',
    },
}

export { LoadingStatus, MESSAGE }
