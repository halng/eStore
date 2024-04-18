import type { Metadata } from 'next'
import Box from '@mui/material/Box'
import Grid from '@mui/material/Grid'
import SideBar from '@/components/SideBar'

export const metadata: Metadata = {
    title: 'E-Store - Shop Name',
    description: 'your own work, your own shop, your own brand',
}

export default function Layout({
    children,
}: Readonly<{
    children: React.ReactNode
}>) {
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
