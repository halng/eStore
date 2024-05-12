'use client'

import { Breadcrumbs, Typography, Link } from '@mui/material'
import { usePathname } from 'next/navigation'
import HomeIcon from '@mui/icons-material/Home'

const CustomBreadcrumbs = () => {
    const pathname = usePathname()
    return (
        <Breadcrumbs aria-label='breadcrumb'>
            {pathname.split('/').map((path, index) => {
                if (index === 0) {
                    return (
                        <Link underline='hover' color='inherit' key={path}>
                            <HomeIcon />
                        </Link>
                    )
                } else
                    return (
                        <Link underline='hover' color='inherit' key={path}>
                            <Typography
                                sx={{
                                    textTransform: 'capitalize',
                                }}
                            >
                                {path.replaceAll('-', ' ')}
                            </Typography>
                        </Link>
                    )
            })}
        </Breadcrumbs>
    )
}

export default CustomBreadcrumbs
