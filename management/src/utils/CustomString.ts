export const FormatString = (str: string, ...val: string[] | any[]) => {
    for (let index = 0; index < val.length; index++) {
        str = str.replace(`{${index}}`, val[index])
    }
    return str
}
