import React from 'react'
import { Editor } from 'react-draft-wysiwyg'

const ProductPost = () => {
    return (
        <div className='product-post'>
            <Editor wrapperClassName='wrapper-class' editorClassName='editor-class' toolbarClassName='toolbar-class' />
        </div>
    )
}

export default ProductPost
