'use client'

import { Provider } from 'react-redux'
import { setupStore } from '@stores'
import { persistStore } from 'redux-persist'

const store = setupStore({})
persistStore(store)

interface CypressWithStore extends Cypress.Cypress {
    store?: typeof store
}

declare global {
    interface Window {
        Cypress?: CypressWithStore
    }
}

if (window.Cypress) {
    window.Cypress.store = store
}

export default function StoreProvider({ children }: { children: React.ReactNode }) {
    return <Provider store={store}>{children}</Provider>
}
