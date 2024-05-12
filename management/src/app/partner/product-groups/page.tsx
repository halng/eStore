'use client'
import React, { useEffect, useState } from 'react'
import { TableView, ActionHeader } from '@components'
import { PRODUCT_GROUP_TABLE_HEADER, CRUD_ACTION, CRUD_ACTION_COLOR } from '@constants'
import { ProductGroupTableData } from '@types'
import Button from '@mui/material/Button'
import TextField from '@mui/material/TextField'
import Dialog from '@mui/material/Dialog'
import DialogActions from '@mui/material/DialogActions'
import DialogContent from '@mui/material/DialogContent'
import DialogContentText from '@mui/material/DialogContentText'
import DialogTitle from '@mui/material/DialogTitle'
import { FormatString } from '../../../utils/CustomString'
import { Highlight } from '@components'
import { Switch } from '@mui/material'
import FormControlLabel from '@mui/material/FormControlLabel'
import { ProductGroupAPI } from 'api-estore-v2'
import { toast } from 'react-toastify'
import { calculateTimeAgo } from '../../../utils/CalculateDate'

interface DialogProps {
    id?: string
    name?: string
    description?: string
    status?: 'ENABLED' | 'DISABLED'
    action: CRUD_ACTION.CREATE | CRUD_ACTION.UPDATE | CRUD_ACTION.DELETE
}

const message = {
    [CRUD_ACTION.CREATE]:
        'To create new Product Group please enter your product group name and product group description here.',
    [CRUD_ACTION.UPDATE]:
        'To update exist Product Group: {0} please enter your new product group name and new product group description here.',
    [CRUD_ACTION.DELETE]: 'Are you sure you want to delete this product group {0}?',
}

const ProductGroups: React.FC = () => {
    const [data, setData] = useState<ProductGroupTableData[]>([])
    const [openDialogData, setOpenDialogData] = useState<DialogProps | null>(null)
    const [enable, setEnable] = useState<string>('')

    // *************************************************************************************

    useEffect(() => {
        ProductGroupAPI.getAll(1)
            .then((res) => {
                const _data = [...res.data.items]
                const productGroup: ProductGroupTableData[] = _data.map((item: ProductGroupTableData, index) => {
                    item.updatedDate = calculateTimeAgo(item.updatedDate)
                    item.no = index + 1
                    return item
                })
                setData([...productGroup])
            })
            .catch(() => {
                toast.error("Cannot not get product group's data")
            })
    }, [])

    const closeDialogHandler = () => {
        setOpenDialogData(null)
    }

    const submitDialogHandler = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault()
        const formData = new FormData(event.currentTarget)
        const formJson = Object.fromEntries((formData as any).entries())

        if (openDialogData?.action === CRUD_ACTION.CREATE) {
            // prepare data to create new product group
            const newProductGroup = {
                name: formJson.groupName,
                description: formJson.groupDescription,
            }

            ProductGroupAPI.create(newProductGroup)
                .then((res: any) => {
                    console.log(res)
                    toast.success(res.data.message)
                })
                .catch((err) => {
                    toast.error(err.response.data.message)
                })
        } else if (openDialogData?.action === CRUD_ACTION.UPDATE) {
            // prepare data to update product group
            const updateProductGroup = {
                id: openDialogData.id,
                name: formJson.groupName,
                description: formJson.groupDescription,
                status: enable,
            }

            ProductGroupAPI.update(updateProductGroup)
                .then((res: any) => {
                    toast.success(res.data.message)
                })
                .catch((err) => {
                    toast.error(err.response.data.message)
                })
        } else if (openDialogData?.action === CRUD_ACTION.DELETE) {
            // prepare data to delete product group
            const groupId = openDialogData.id
            if (!groupId) return
            ProductGroupAPI.deleteGroup(groupId)
                .then((res: any) => {
                    toast.success(res.data.message)
                })
                .catch((err) => {
                    toast.error(err.response.data.message)
                })
        }

        closeDialogHandler()
    }

    const onOpenDialogHandler = (id: string, action: CRUD_ACTION) => {
        if (id !== '') {
            const productGroup = data.find((item) => item.id === id)
            if (productGroup) {
                setOpenDialogData({
                    id: productGroup.id,
                    name: productGroup.name,
                    description: productGroup.description,
                    status: productGroup.status,
                    action,
                })
                setEnable(productGroup.status)
            }
        } else {
            // Create new product group action
            setOpenDialogData({
                action,
            })
        }
    }

    // *************************************************************************************

    return (
        <div>
            <ActionHeader tableData={data} setOpenAction={onOpenDialogHandler} />
            <TableView tableData={data} tableHeader={PRODUCT_GROUP_TABLE_HEADER} setOpenAction={onOpenDialogHandler} />

            {/* Open dialog to perform action */}
            {openDialogData?.action && (
                <Dialog
                    open={openDialogData?.action !== null}
                    onClose={closeDialogHandler}
                    PaperProps={{
                        component: 'form',
                        onSubmit: submitDialogHandler,
                    }}
                >
                    <DialogTitle>{openDialogData.action.toLocaleUpperCase()}</DialogTitle>
                    <DialogContent>
                        <DialogContentText pb={2}>
                            {
                                <Highlight
                                    originStr={FormatString(message[openDialogData.action], openDialogData?.name)}
                                    highlight={openDialogData.name ? openDialogData.name : ''}
                                    color={'error'}
                                />
                            }
                        </DialogContentText>
                        {openDialogData.action !== CRUD_ACTION.DELETE && (
                            <>
                                <TextField
                                    autoFocus
                                    required
                                    margin='dense'
                                    name='groupName'
                                    label='Product Name'
                                    defaultValue={openDialogData.name}
                                    type='text'
                                    fullWidth
                                    variant='standard'
                                />
                                <TextField
                                    autoFocus
                                    required
                                    margin='dense'
                                    name='groupDescription'
                                    label='Product Group Description'
                                    type='text'
                                    defaultValue={openDialogData.description}
                                    maxRows={3}
                                    multiline
                                    fullWidth
                                    variant='standard'
                                    sx={{ pt: 2, pb: 2 }}
                                />
                            </>
                        )}
                        {openDialogData.action === CRUD_ACTION.UPDATE && (
                            <FormControlLabel
                                control={
                                    <Switch
                                        checked={enable === 'ENABLED' ? true : false}
                                        onChange={() => {
                                            setEnable(enable === 'ENABLED' ? 'DISABLED' : 'ENABLED')
                                        }}
                                        inputProps={{ 'aria-label': 'controlled' }}
                                    />
                                }
                                label={enable === 'ENABLED' ? 'Enable' : 'Disable'}
                            />
                        )}
                    </DialogContent>
                    <DialogActions>
                        <Button onClick={closeDialogHandler} color='primary'>
                            Cancel
                        </Button>

                        <Button type='submit' color={CRUD_ACTION_COLOR[openDialogData.action] as any}>
                            {openDialogData.action.toLocaleUpperCase()}
                        </Button>
                    </DialogActions>
                </Dialog>
            )}
        </div>
    )
}

export default ProductGroups
