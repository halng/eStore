// import * as React from 'react'
// import { render } from '@testing-library/react'

import 'jest-canvas-mock'
import { isIgnore, getAccessToken, saveAccessToken } from '../../src/api/helper/token'

test('Test check url call need to have access token', () => {
    expect(isIgnore('register')).toBe(true)
    expect(isIgnore('auth/re')).toBe(false)
})

test('Check access token after saved', () => {
    saveAccessToken('hello')
    expect(getAccessToken()).toBe('hello')
})
