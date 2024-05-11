'use client'
import React, { useState } from 'react'
import Paper from '@mui/material/Paper'
import Table from '@mui/material/Table'
import TableBody from '@mui/material/TableBody'
import TableCell from '@mui/material/TableCell'
import TableContainer from '@mui/material/TableContainer'
import TableHead from '@mui/material/TableHead'
import TablePagination from '@mui/material/TablePagination'
import TableRow from '@mui/material/TableRow'
import { ProductGroupTableData, ProductGroupTableHeader } from '@types'
import { Typography } from '@mui/material'
import Chip from '@mui/material/Chip'
import IconButton from '@mui/material/IconButton'
import Popover from '@mui/material/Popover'
import MenuItem from '@mui/material/MenuItem'
import MoreVertIcon from '@mui/icons-material/MoreVert'
import EditIcon from '@mui/icons-material/Edit'
import DeleteIcon from '@mui/icons-material/Delete'
import { CRUD_ACTION } from '@constants'

interface TableViewProps {
    tableHeader: ProductGroupTableHeader[] | null
    tableData: ProductGroupTableData[]
    setOpenAction: any
}
const ItemAction = ({ id, setOpenAction }: any) => {
    const [open, setOpen] = useState<any>(null)

    const handleClick = (event: React.MouseEvent<HTMLElement>) => {
        setOpen(event.currentTarget)
    }
    const handleClose = () => {
        setOpen(null)
    }
    return (
        <>
            <IconButton
                aria-label='more'
                id='long-button'
                aria-controls={open ? 'long-menu' : undefined}
                aria-expanded={open ? 'true' : undefined}
                aria-haspopup='true'
                onClick={handleClick}
            >
                <MoreVertIcon />
            </IconButton>
            <Popover
                open={!!open}
                anchorEl={open}
                onClose={handleClose}
                anchorOrigin={{ vertical: 'top', horizontal: 'left' }}
                transformOrigin={{ vertical: 'top', horizontal: 'right' }}
                PaperProps={{
                    sx: { width: 140 },
                }}
            >
                <MenuItem
                    onClick={() => {
                        setOpenAction(id, CRUD_ACTION.UPDATE)
                        handleClose()
                    }}
                >
                    <EditIcon sx={{ mr: 2 }} />
                    <Typography variant='inherit'>Edit</Typography>
                </MenuItem>
                <MenuItem
                    onClick={() => {
                        setOpenAction(id, CRUD_ACTION.DELETE)
                        handleClose()
                    }}
                    sx={{ color: 'error.main' }}
                >
                    <DeleteIcon sx={{ mr: 2 }} />
                    Delete
                </MenuItem>
            </Popover>
        </>
    )
}

const TableView = ({ tableHeader, tableData, setOpenAction }: TableViewProps) => {
    const [page, setPage] = React.useState(0)
    const [rowsPerPage, setRowsPerPage] = React.useState(10)

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage)
    }

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(+event.target.value)
        setPage(0)
    }

    // *************************************************************************************************

    if (!tableHeader || !tableData) {
        return (
            <Paper sx={{ width: '100%', textAlign: 'center', overflow: 'hidden' }}>
                <Typography variant='h5' component='h2'>
                    No Data!
                </Typography>
            </Paper>
        )
    }

    return (
        <Paper sx={{ width: '100%', overflow: 'hidden', paddingRight: '2rem' }}>
            <TableContainer sx={{ maxHeight: '40rem' }}>
                <Table stickyHeader aria-label='sticky table'>
                    <TableHead>
                        <TableRow>
                            {tableHeader.map((column) => {
                                if (column.isDisplay === false) return null
                                return (
                                    <TableCell
                                        key={column.id}
                                        align={column.align}
                                        style={{ minWidth: column.minWidth, fontWeight: 'bold', fontSize: '1rem' }}
                                    >
                                        {column.label}
                                    </TableCell>
                                )
                            })}
                            <TableCell />
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {tableData.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => {
                            return (
                                <TableRow hover role='checkbox' tabIndex={-1} key={row.id}>
                                    {tableHeader.map((column) => {
                                        if (column.isDisplay === false) return null
                                        const value = row[column.id]
                                        if (column.id.toLocaleLowerCase() === 'status') {
                                            return (
                                                <TableCell key={column.id} align={column.align}>
                                                    <Chip
                                                        variant='outlined'
                                                        color={value === 'active' ? 'success' : 'error'}
                                                        label={value.toString().toUpperCase()}
                                                    />
                                                </TableCell>
                                            )
                                        } else {
                                            return (
                                                <TableCell key={column.id} align={column.align}>
                                                    {column.format && typeof value === 'number'
                                                        ? column.format(value)
                                                        : value}
                                                </TableCell>
                                            )
                                        }
                                    })}
                                    <TableCell>
                                        <ItemAction id={row.id} setOpenAction={setOpenAction} />
                                    </TableCell>
                                </TableRow>
                            )
                        })}
                    </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                sx={{ pr: '2rem' }}
                component='div'
                count={tableData.length}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />
        </Paper>
    )
}

export default TableView
