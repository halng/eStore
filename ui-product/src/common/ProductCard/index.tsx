import React from 'react'
import AspectRatio from '@mui/joy/AspectRatio'
import Card from '@mui/joy/Card'
import CardContent from '@mui/joy/CardContent'
import CardOverflow from '@mui/joy/CardOverflow'
import Chip from '@mui/joy/Chip'
import Link from '@mui/joy/Link'
import Typography from '@mui/joy/Typography'
import { ProductType } from '../../types/ProductType'
import Grid from '@mui/joy/Grid'
// Refer: https://mui.com/joy-ui/react-card/

interface props {
    item: ProductType
}

const ProductCard = ({ item }: props) => {
    return (
        <Grid xs={3}>
            <Card sx={{ width: 320, boxShadow: 'lg', height: '23rem' }}>
                <CardOverflow>
                    <AspectRatio sx={{ minWidth: 200 }}>
                        <img src={item.thumbnail} alt='' />
                    </AspectRatio>
                </CardOverflow>
                <CardContent>
                    <Typography level='body-xs'>{item.groupName}</Typography>
                    <Link href={`detail/${item.slug}`} fontWeight='md' color='neutral' textColor='text.primary' overlay>
                        {item.name}
                    </Link>

                    <Typography
                        level='title-lg'
                        sx={{ mt: 1, fontWeight: 'xl' }}
                        endDecorator={
                            <Chip component='span' size='sm' variant='soft' color='success'>
                                <b>{item.quantity}</b> left in stock!
                            </Chip>
                        }
                    >
                        {item.price.toLocaleString('en-US', {
                            style: 'currency',
                            currency: 'USD',
                        })}
                    </Typography>
                    <Typography level='body-sm'>
                        (Have <b>{item.variations}</b> product variations)
                    </Typography>
                </CardContent>
            </Card>
        </Grid>
    )
}

export default ProductCard
