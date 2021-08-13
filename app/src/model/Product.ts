interface Product {
    id: number,
    name: string,
    image_url: string,
    price: number,
    stock: number,
    category?: string
}

export default Product;