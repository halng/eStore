'use client'
import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'
import { SideBar } from '@components'
import { useAppSelector } from '@stores'

export default function Layout({
    children,
}: Readonly<{
    children: React.ReactNode
}>) {
    const isAuth = useAppSelector((state) => state.auth.isAuth)
    if (!isAuth) {
        window.location.href = '/'
    } else {
        return (
            <Box sx={{ flexGrow: 1 }}>
                <Grid container spacing={2}>
                    <Grid item xs={2}>
                        <SideBar />
                    </Grid>
                    <Grid item xs={10}>
                        {children}
                    </Grid>
                </Grid>
            </Box>
        )
    }
}
