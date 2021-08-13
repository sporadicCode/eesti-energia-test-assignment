import * as React from "react";
import Product from "../model/Product";
import CartService from "../service/CartService";


interface CartItemProps {
    product: Product,
    quantity: number,
    update: () => void
}

const CartItem = ({product: {id, name, image_url, price, stock, category = "GENERIC"}, quantity, update} : CartItemProps) => {

    const increment = (id: number) : void => {
        CartService
            .addToCart(id)
            .then(()=>{
                update();
            })
            .catch(e => {
                console.log(e);
            });
    }

    const decrement = (id: number) : void => {
        CartService
            .removeFromCart(id)
            .then(()=>{
                update();
            })
            .catch(e => {
                console.log(e);
            });
    }

    const remove = (id: number) : void => {
        CartService
            .deleteFromCart(id)
            .then(()=>{
                update();
            })
            .catch(e => {
                console.log(e);
            });
    }

    return (
            <div className="d-flex justify-content-between align-items-center mb-1 text-muted fs-6 border-bottom border-1 p-2">

                <span className="d-flex me-2 align-items-center">
                    <span
                        className="me-2 fs-4 align-items-center"
                        style={{cursor:"pointer"}}
                        onClick={()=>(remove(id))}>
                        &times;
                    </span>
                    {name}
                </span>
                <span>
                    <button
                        className="btn btn-outline-secondary btn-sm"
                        onClick={() => {decrement(id)}}
                    >-</button>
                    <span className="mx-3">
                        {quantity}
                    </span>
                    <button
                        className="btn btn-outline-secondary btn-sm"
                        onClick={()=>{increment(id)}}
                    >+</button>
                </span>
            </div>
    );
}

export default CartItem;