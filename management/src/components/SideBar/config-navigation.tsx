import StorefrontIcon from '@mui/icons-material/Storefront'
import DashboardIcon from '@mui/icons-material/Dashboard'
import CategoryIcon from '@mui/icons-material/Category'
import ReorderIcon from '@mui/icons-material/Reorder'
import WorkspacesIcon from '@mui/icons-material/Workspaces'
import ChatIcon from '@mui/icons-material/Chat'
import SettingsIcon from '@mui/icons-material/Settings'

const navConfig = [
    {
        title: 'dashboard',
        path: '/partner/',
        icon: <DashboardIcon />,
        allowed: ['SELLER', 'ADMIN', 'SUPER_ADMIN', 'STAFF'],
    },
    {
        title: 'product',
        path: '/partner/products',
        icon: <ReorderIcon />,
        allowed: ['SELLER'],
    },
    {
        title: 'product group',
        path: '/partner/product-groups',
        icon: <WorkspacesIcon />,
        allowed: ['SELLER'],
    },
    {
        title: 'product attribute',
        path: '/partner/product-attributes',
        icon: <CategoryIcon />,
        allowed: ['SELLER'],
    },
    {
        title: 'store',
        path: '/partner/stores',
        icon: <StorefrontIcon />,
        allowed: ['SELLER'],
    },
    {
        title: 'Chat',
        path: '/partner/chat',
        icon: <ChatIcon />,
        allowed: ['SELLER', 'STAFF'],
    },
    {
        title: 'settings',
        path: '/partner/settings',
        icon: <SettingsIcon />,
        allowed: ['SELLER'],
    },
]

export default navConfig
