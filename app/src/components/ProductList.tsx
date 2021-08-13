import * as React from "react";
import ProductView from "./ProductView";
import Product from "../model/Product";

interface ProductListProps {
    update: () => void,
    food: Array<Product>,
    clothes: Array<Product>,
    toys: Array<Product>
}

const ProductList = ({ update, food, clothes, toys } : ProductListProps) => {

    return (
        <>
            <h3 className="mx-auto mt-2">Food</h3>
            <div className="container">
                <div className="row">
                    {food.map((product : Product) =>
                        <div key={"food-"+product.id} className="col-sm-12 col-md-6 col-lg-4 col-xl-3">
                            <ProductView product={product} update={update}/>
                        </div>
                    )}
                </div>
            </div>

            <h3 className="mx-auto mt-2 mb-2">Clothes</h3>
            <div className="container">
                <div className="row">
                    {clothes.map((product) =>
                        <div key={"product-"+product.id} className="col-sm-12 col-md-6 col-lg-4 col-xl-3">
                            <ProductView product={product} update={update}/>
                        </div>
                    )}
                </div>
            </div>

            <h3 className="mx-auto mt-2">Toys</h3>
            <div className="container">
                <div className="row">
                    {toys.map((product) =>
                        <div key={"toys-"+product.id} className="col-sm-12 col-md-6 col-lg-4 col-xl-3">
                            <ProductView product={product} update={update}/>
                        </div>
                    )}
                </div>
            </div>
        </>
    );
}

export default ProductList;