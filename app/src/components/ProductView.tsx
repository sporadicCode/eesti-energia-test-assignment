import * as React from "react";
import Product from "../model/Product";
import CartService from "../service/CartService";

interface ProductViewProps {
    product: Product,
    update: () => void
}

const ProductView = ({product: {id, name, image_url, price, stock, category = "GENERIC"}, update } : ProductViewProps) => {

    const addToCart = (id: number) => {
        CartService
            .addToCart(id)
            .then(resp =>  {
                if (!resp.ok) {
                    throw Error(resp.status.toString());
                }
                update();
            })
            .catch(e => {
            console.log(e);
        });
    }

    return(
        <>
            <div className="d-flex justify-content-center mb-3 mt-3">
                <div className="card p-3 bg-white shadow-sm">
                    <div className="text-center mt-2">
                        <img
                            style={{
                                filter: stock === 0 ? "grayscale(100%) contrast(30%)" : undefined
                            }}
                            src={image_url}
                            width="200"
                            height="160"
                            alt={"Picture of a " + name}
                            onClick={() => {addToCart(id)}}
                        />
                        <div>
                            <h4 style={{ textTransform: "capitalize"}}>{name}</h4>
                            <h6 style={{ textTransform: "lowercase"}} className="mt-0 text-black-50">{category}</h6>
                        </div>
                    </div>

                    <div className="mt-2">
                        <div className="d-flex justify-content-between">
                            <span>In stock</span><span>{stock}</span>
                        </div>

                        <div className="d-flex justify-content-between">
                            <span>Price</span><span>{price.toFixed(2)}€</span>
                        </div>
                    </div>

                    <div className="d-flex justify-content-end mt-4">
                        <button
                            className="btn btn-primary"
                            disabled={stock === 0}
                            onClick={() => {addToCart(id)}}
                        >
                            {stock === 0 ? "Out of stock" : "Add to cart"}
                        </button>
                    </div>

                </div>
            </div>
        </>
    );

}

export default ProductView;