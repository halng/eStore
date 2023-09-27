import slugify from 'slugify'

const convertStandardSlug = (value: string): string => {
    if (value) {
        return slugify(value, {
            remove: /[*+~.()'"!:@]/g,
            replacement: '-',
            lower: true,
            trim: true,
        })
    }
    return ''
}

export { convertStandardSlug }
