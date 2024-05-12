import { Typography } from '@mui/material'

interface HighlightProps {
    originStr: string
    highlight: string
    color: 'warning' | 'success' | 'error' | 'info' | 'primary' | 'secondary' | 'default'
}

const Highlight = ({ originStr, highlight, color }: HighlightProps) => {
    if (!highlight) {
        return <Typography variant='body1'>{originStr}</Typography>
    }
    const texts = originStr.split(new RegExp(`(${highlight})`, 'gi'))
    return (
        <span>
            {texts.map((text, index) => {
                if (text.toLowerCase() === highlight.toLowerCase()) {
                    return (
                        <Typography key={index} component='span' variant='inherit' color={color}>
                            {text}
                        </Typography>
                    )
                }
                return text
            })}
        </span>
    )
}

export default Highlight
