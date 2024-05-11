'use client'

import { Box, Button } from '@mui/material'

import * as React from 'react'
import TextField from '@mui/material/TextField'
import Autocomplete from '@mui/material/Autocomplete'
import { ProductGroupTableData } from '@types'
import AddIcon from '@mui/icons-material/Add'
import CustomBreadcrumbs from '../Breadcrumbs'

interface SearchBoxProps {
    initializeData: ProductGroupTableData[]
}

const SearchBox = ({ initializeData }: SearchBoxProps) => {
    return (
        <Autocomplete
            sx={{ width: '40%', height: '3rem' }}
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
    return (
        <Box sx={{ display: 'flex', alignItems: 'left', mb: 2, flexDirection: 'column', mt: 3 }}>
            <CustomBreadcrumbs />
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
                <Button variant='contained' color='success' startIcon={<AddIcon />}>
                    Add
                </Button>
            </Box>
        </Box>
    )
}

export default ActionHeader
