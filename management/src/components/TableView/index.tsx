'use client'
import * as React from 'react'
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

interface TableViewProps {
    tableHeader: ProductGroupTableHeader[] | null
    tableData: ProductGroupTableData[]
}

const TableView = ({ tableHeader, tableData }: TableViewProps) => {
    const [page, setPage] = React.useState(0)
    const [rowsPerPage, setRowsPerPage] = React.useState(10)

    const handleChangePage = (event: unknown, newPage: number) => {
        setPage(newPage)
    }

    const handleChangeRowsPerPage = (event: React.ChangeEvent<HTMLInputElement>) => {
        setRowsPerPage(+event.target.value)
        setPage(0)
    }

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
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {tableData.slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage).map((row) => {
                            return (
                                <TableRow hover role='checkbox' tabIndex={-1} key={row.id}>
                                    {tableHeader.map((column) => {
                                        if (column.isDisplay === false) return null
                                        const value = row[column.id]
                                        return (
                                            <TableCell key={column.id} align={column.align}>
                                                {column.format && typeof value === 'number'
                                                    ? column.format(value)
                                                    : value}
                                            </TableCell>
                                        )
                                    })}
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
