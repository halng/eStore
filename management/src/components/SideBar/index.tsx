'use client'
import Box from '@mui/material/Box'
import Stack from '@mui/material/Stack'
import Avatar from '@mui/material/Avatar'
import { alpha } from '@mui/material/styles'
import Typography from '@mui/material/Typography'
import ListItemButton from '@mui/material/ListItemButton'

import { usePathname } from 'next/navigation'

import { NAV } from '@constants'
import navConfig from './config-navigation'

export default function Nav() {
    const account = {
        displayName: 'Tao Nguyen',
        photoURL: 'https://letsenhance.io/static/8f5e523ee6b2479e26ecc91b9c25261e/1015f/MainAfter.jpg',
        role: 'SELLER',
    }

    const renderAccount = (
        <Box
            sx={{
                my: 3,
                mx: 2.5,
                py: 2,
                px: 2.5,
                display: 'flex',
                borderRadius: 1.5,
                alignItems: 'center',
                bgcolor: (theme) => alpha(theme.palette.grey[500], 0.12),
            }}
        >
            <Avatar src={account.photoURL} alt='photo' />

            <Box sx={{ ml: 2 }}>
                <Typography variant='subtitle2'>{account.displayName}</Typography>

                <Typography variant='body2' sx={{ color: 'text.secondary' }}>
                    {account.role}
                </Typography>
            </Box>
        </Box>
    )

    const renderMenu = (
        <Stack component='nav' spacing={0.5} sx={{ px: 2 }}>
            {navConfig.map((item) => item.allowed.includes(account.role) && <NavItem key={item.title} item={item} />)}
        </Stack>
    )

    const renderContent = (
        <>
            {renderAccount}

            {renderMenu}

            <Box sx={{ flexGrow: 1 }} />
        </>
    )

    return (
        <Box
            sx={{
                flexShrink: { lg: 0 },
                width: { lg: NAV.WIDTH },
            }}
        >
            <Box
                sx={{
                    height: 1,
                    position: 'fixed',
                    width: NAV.WIDTH,
                    borderRight: (theme) => `dashed 1px ${theme.palette.divider}`,
                }}
            >
                {renderContent}
            </Box>
        </Box>
    )
}

// ----------------------------------------------------------------------

function NavItem({ item }: any) {
    const pathname = usePathname()

    const active = item.path === pathname

    return (
        <ListItemButton
            href={item.path}
            sx={{
                minHeight: 44,
                borderRadius: 0.75,
                typography: 'body2',
                color: 'text.secondary',
                textTransform: 'capitalize',
                fontWeight: 'fontWeightMedium',
                ...(active && {
                    color: 'primary.main',
                    fontWeight: 'fontWeightSemiBold',
                    bgcolor: (theme) => alpha(theme.palette.primary.main, 0.08),
                    '&:hover': {
                        bgcolor: (theme) => alpha(theme.palette.primary.main, 0.16),
                    },
                }),
            }}
        >
            <Box component='span' sx={{ width: 24, height: 24, mr: 2 }}>
                {item.icon}
            </Box>

            <Box component='span'>{item.title} </Box>
        </ListItemButton>
    )
}
