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
import { useAppSelector } from '@stores'

export default function Nav() {
    const account = useAppSelector((state) => state.auth)

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
            <Avatar
                src='https://e-store-product-thumbnail.s3.ap-southeast-1.amazonaws.com/Wallpaper.jpg?response-content-disposition=inline&X-Amz-Security-Token=IQoJb3JpZ2luX2VjENP%2F%2F%2F%2F%2F%2F%2F%2F%2F%2FwEaDmFwLXNvdXRoZWFzdC0xIkcwRQIgLyNKUxrGXAPej4s7yHWVk%2FSn9Z56PXAt6ooxUE89hWQCIQDF2plXG1hYEPPoKq58rDOUqjSXhcc1DZ11WeCocxI2KiqJAwg8EAAaDDU5MDE4Mzc2NDcxMCIMj9EKwtj430ebHKERKuYCCdVbev1LJ%2BssATf3cJ4aNV6HtG4KfAhrnoEcgl%2FdvNsaRd2zNfH6rZHY2jI7BRX4%2Bn6xl9sS4yjnbatDinbl13KBQvtREJmiREySw8LxvHmLZvK74UhVlzYaDxtX234PzCKAGnFXb7gDrCMvz6Kq0e%2FfNyMQcy48pjXugMCQUnuwXcfYi6FrAwqE48JdMtCjFxHo57K5AOLZ14vEvmbGcc0iMTV1y3vBbVrFsAxrD%2FZND8RkVrpijB3TxW1LtiVbCUkKXSE9zh4S54uWQUW54%2B%2FRziWZdNYUSfWgxFTzNX6m6IHkySTb0jValEHO%2FSjY%2BnA%2FwQYD7wl4eBXIFFEKp4IZ8q0MQvFAXvvU01kXdFyezTzufjFbH0XFUZEfEb8QCZJD9miN4vqPzSQvR6zw%2FlkVlxCoItxSrllbzuMHAozRD7W2ZTMm03Sfoljryn0%2B1R1XrxVLrWmjD056Xb4YT0oTrC4ygzDUwPuxBjqzAvAVl4l2zgl0TkddAuunUfEbl7yx%2B7SZqJDJRYgVNShVUAXgTBDHs395H9pln2y%2F%2BtddZXACttSBABMZRy9LQlZfOD0t3JnFlPUo%2FYKI7TRjpoEoce%2FgeRCW0T%2BvpUfhEIrXJhUbtd7XB238bk8aBRBZGsexSBe%2BhilfHUsa4hqR5OOZkShucI7eNrDAMChfl8zoQt65y%2FZYhYOieI6lIOBmySLaXv7cB89q4lS%2F0x3s9pbeCfnwkK6frXUJgfrs%2F5rblod8ri6rTWI5z9hvAMseUweSateKPc%2Fc0WAdcDqyCYGWdSOchS%2Bs8UqjN0fIhHb%2FRD%2F0C1EMIlR65Hlt9Hi88p8%2B2c%2FjdFPMERdIvXDBWUiMu8x5kaXObCzpgoIEdeehvUagNx2md1BWxbHBOPqgvvc%3D&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240511T032135Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3599&X-Amz-Credential=ASIAYS2NRSLTN64XRDU5%2F20240511%2Fap-southeast-1%2Fs3%2Faws4_request&X-Amz-Signature=a9d227a09bfe7f4ccca7e5c2d040b0160d204713ac959f23fe6bfc8dc2f8babb'
                alt='photo'
            />

            <Box sx={{ ml: 2 }}>
                <Typography variant='subtitle2'>{account.username}</Typography>

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
