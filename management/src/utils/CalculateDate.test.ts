import { calculateTimeAgo } from './CalculateDate'

describe('calculateTimeAgo', () => {
    it('should return "X seconds ago" when less than a minute', () => {
        const result = calculateTimeAgo(new Date().toISOString())
        expect(result).toMatch(/\d+ seconds ago/)
    })

    it('should return "X minutes ago" when less than an hour', () => {
        const date = new Date()
        date.setMinutes(date.getMinutes() - 10)
        const result = calculateTimeAgo(date.toISOString())
        expect(result).toMatch(/\d+ minutes ago/)
    })

    it('should return "X hours ago" when less than a day', () => {
        const date = new Date()
        date.setHours(date.getHours() - 5)
        const result = calculateTimeAgo(date.toISOString())
        expect(result).toMatch(/\d+ hours ago/)
    })

    it('should return "X days ago" when more than a day', () => {
        const date = new Date()
        date.setDate(date.getDate() - 3)
        const result = calculateTimeAgo(date.toISOString())
        expect(result).toMatch(/\d+ days ago/)
    })

    it('should return "0 seconds ago" when given the current date', () => {
        const result = calculateTimeAgo(new Date().toISOString())
        expect(result).toBe('0 seconds ago')
    })
})
