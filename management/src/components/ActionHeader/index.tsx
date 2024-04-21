'use client'

import { Breadcrumbs, Typography, Link, Box, Button } from '@mui/material'
import { usePathname } from 'next/navigation'
import HomeIcon from '@mui/icons-material/Home'

import * as React from 'react'
import TextField from '@mui/material/TextField'
import Autocomplete from '@mui/material/Autocomplete'
import { ProductGroupTableData } from '@types'

interface SearchBoxProps {
    initializeData: ProductGroupTableData[]
}
const SearchBox = ({ initializeData }: SearchBoxProps) => {
    return (
        <Autocomplete
            sx={{ width: '40%' }}
            freeSolo
            id='free-solo-2-demo'
            disableClearable
            options={(initializeData || []).map((option) => option.name)}
            renderInput={(params) => (
                <TextField
                    {...params}
                    label='Search'
                    InputProps={{
                        ...params.InputProps,
                        type: 'search',
                    }}
                />
            )}
        />
    )
}

interface ActionHeaderProps {
    tableData: ProductGroupTableData[]
}

const ActionHeader = ({ tableData }: ActionHeaderProps) => {
    const pathname = usePathname()

    return (
        <Box sx={{ display: 'flex', alignItems: 'left', mb: 2, flexDirection: 'column', mt: 3 }}>
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

            <Box
                sx={{
                    display: 'flex',
                    width: '100%',
                    justifyContent: 'space-between',
                    paddingRight: '1rem',
                    pt: '2rem',
                }}
            >
                {<SearchBox initializeData={tableData} />}
                <Button variant='contained' color='primary'>
                    Create
                </Button>
            </Box>
        </Box>
    )
}

export default ActionHeader
