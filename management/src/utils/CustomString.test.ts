import { FormatString } from './CustomString'

describe('FormatString', () => {
    it('should replace placeholders with values', () => {
        const result = FormatString('Hello, {0}! Welcome to {1}.', 'John', 'eStore')
        expect(result).toEqual('Hello, John! Welcome to eStore.')
    })

    it('should handle multiple placeholders', () => {
        const result = FormatString('The sum of {0} and {1} is {2}.', 5, 10, 15)
        expect(result).toEqual('The sum of 5 and 10 is 15.')
    })

    it('should handle placeholders with different types', () => {
        const result = FormatString('My name is {0} and I am {1} years old.', 'Alice', 25)
        expect(result).toEqual('My name is Alice and I am 25 years old.')
    })

    it('should handle missing placeholders', () => {
        const result = FormatString('Hello, {0}! Welcome to {1}.', 'John')
        expect(result).toEqual('Hello, John! Welcome to {1}.')
    })
})
