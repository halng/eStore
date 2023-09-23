const assert = require('assert')
const simple = require('../../src/utils/index')

describe('Simple Test Func', () => {
    it('1. Simple test', () => {
        const result = simple.simpleTest(1, 2)
        assert.equal(result, 2)
    })
})
