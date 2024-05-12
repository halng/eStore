export const calculateTimeAgo = (dateString: string): string => {
    const date = new Date(dateString)
    const currentDate = new Date()
    const timeDifference = currentDate.getTime() - date.getTime()

    const seconds = Math.floor(timeDifference / 1000)
    const minutes = Math.floor(seconds / 60)
    const hours = Math.floor(minutes / 60)
    const days = Math.floor(hours / 24)

    if (minutes < 1) {
        return `${seconds} seconds ago`
    } else if (hours < 1) {
        return `${minutes} minutes ago`
    } else if (days < 1) {
        return `${hours} hours ago`
    } else {
        return `${days} days ago`
    }
}
