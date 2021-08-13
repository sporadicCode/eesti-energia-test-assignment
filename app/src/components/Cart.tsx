import * as React from "react";
import Product from "../model/Product";
import CartService from "../service/CartService";
import CartItem from "./CartItem";
import { useState } from "react";
import CurrencyInput from "react-currency-input-field";


interface CartProps {
    cart: Map<Product, number>,
    total: number,
    update: () => void,
    refreshProducts: () => void
}

const Cart = ({cart, total, update, refreshProducts} : CartProps) => {
    const [isWaitingForPayment, setWaitingForPayment] = useState<boolean>(false);
    const [isPaymentSuccessful, setPaymentSuccess] = useState<boolean>(false);
    const [value, setValue] = useState<string | number>(0.00);
    const [errorMessage, setErrorMessage] = useState('');
    const [className, setClassName] = useState('');
    const [changeMap, setChangeMap] = useState<Map<string, number>>(new Map<string, number>());
    const limit = 1000;

    const clearCart = () : void => {
        CartService.clearCart().then(r => {
            if (!r.ok) {
                console.error("Clear cart request failed.\n", r.json())
            }
            update();
        });
    }

    const updateChangeMap = (key: string, value: number) => {
        setChangeMap(new Map(changeMap.set(key,value)));
    }

    const clearChangeMap = () => {
        setChangeMap(new Map<string,number>());
    }

    const checkout = (cashInserted: number) : boolean => {
        if (isNaN(cashInserted)) {
            console.error("Value inserted is not a number, expected: number, got: ", cashInserted);
            return false;
        }
        CartService.checkout(cashInserted).then(r => {
            if (!r.ok) {
                console.error("Checkout failed. Server responded: \n", r.json());
                return false;
            }
            // process change to be returned response
            r.json().then(data => {
                if (data.hasOwnProperty("change") && data["change"] === 0) {
                    clearChangeMap();
                    return;
                }
                clearChangeMap();
                const keys = Object.keys(data);
                // sort by numerical value
                keys.sort((a, b) => Number(b.split('.')[0]) - Number(a.split('.')[0]));
                keys.forEach(key => {
                    const firstDigit : number = Number(key.split('.')[0]);
                    let type : string;
                    let value : number;
                    if (firstDigit > 0) {
                        type = "Euro";
                        value = Number(key.split('.')[0]);
                    } else {
                        type = "cents";
                        value = Number(key.split('.')[1]);
                    }
                    updateChangeMap(`${value} ${type}`, data[key]);
                })
            });
            update();
            setPaymentSuccess(true);
            setWaitingForPayment(false);
            setValue(0.00);
            refreshProducts();
            return true;
        }).catch((err) => {console.error(err)});
        setPaymentSuccess(false);
        return false;
    }

    const handleOnValueChange = (value: string | undefined): void => {
        if (!value) {
            setClassName('');
            setValue('');
            return;
        }
        if (Number.isNaN(Number(value))) {
            setErrorMessage('Please enter a valid number');
            setClassName('is-invalid');
            return;
        }
        if (Number(value) > limit) {
            setErrorMessage(`Maximum accepted cash amount: ${limit}€`);
            setClassName('is-invalid');
            setValue(value);
            return;
        }
        setClassName('is-valid');
        setValue(value);
    };

    return (
        <>
            <div className="mx-auto justify-content-center col-sm-10 col-md-6 col-lg-4 col-xl-3 shadow mb-4 p-4">

                {!isWaitingForPayment && !isPaymentSuccessful && (<>
                <div className="d-flex justify-content-center mb-1 fs-3">Cart</div>
                {cart.size > 0 ? <div className="d-flex justify-content-between mb-1"><span className="fs-5">Product</span><span className="fs-5">Quantity</span></div> : null}
                <div className="justify-content-center">
                    {Array.from(cart).map(([key, value])=>(
                        <CartItem key={"ci-"+key.id} product={key} quantity={value} update={update}/>
                    ))}
                </div>
                <div className="d-flex justify-content-between mb-4">
                    <span className="fs-4">Total:</span>
                    <span className="fs-4">{total.toFixed(2)}€</span>
                </div>
                <div>
                    <button
                        className="btn btn-primary btn-lg mb-2 form-control"
                        disabled={total === 0}
                        onClick={() => {
                            setWaitingForPayment(true);
                        }}
                    >Checkout</button>
                        <button
                            disabled={cart.size === 0}
                            onClick={() => clearCart()}
                            className="btn btn-lg btn-outline-danger mb-2 form-control">
                            Clear cart
                        </button>

                </div>
                </>)}

                {isWaitingForPayment && !isPaymentSuccessful && (
                <div className="mb-4">
                        <span className="my-2 fs-5">Amount  to be paid: {total.toFixed(2)}€</span>
                        <label htmlFor="currency-input" className="my-2">Please enter inserted cash amount:</label>
                        <CurrencyInput
                            id="currency-input"
                            className={`form-control ${className}`}
                            value={value}
                            onValueChange={handleOnValueChange}
                            placeholder="Please enter inserted cash amount, e.g. 74.55"
                            suffix={"€"}
                            decimalScale={2}
                            fixedDecimalLength={2}
                            allowNegativeValue={false}
                        />
                        <div className="invalid-feedback fs-6 lh-1 fw-light">{errorMessage}</div>

                    <button
                        className="btn btn-lg btn-primary input-group mt-4"
                        type="button"
                        id="button-addon2"
                        disabled={value < total || total <= 0}
                        onClick={() => {
                            checkout(+value); // + for converting to number
                        }}
                    >
                        Pay
                    </button>
                    <button
                        className="btn btn-lg btn-secondary input-group mt-2"
                        onClick={()=>{setWaitingForPayment(false)}}
                    >
                        Back
                    </button>
                </div>
                )}

                {!isWaitingForPayment && isPaymentSuccessful && changeMap.size > 0 && (
                    <div className="d-flex flex-column align-items-center">
                    <span className="fs-5 mb-2">Here is your change:</span>
                    {Array.from(changeMap).map(([key, value])=>(
                        <div key={"ch-"+key}>
                        <span>{key}: </span>
                        <span>x {value}</span>
                        </div>
                        ))}
                    </div>
                )}

                {!isWaitingForPayment && isPaymentSuccessful && (
                    <div
                        className="d-flex flex-column justify-content-center align-items-center"
                    >
                        <div
                            className="fs-3 my-2"
                        >
                            Thanks for the purchase!
                        </div>
                        <button
                            className="btn btn-lg btn-secondary mt-2 form-control"
                            onClick={() => {
                                setPaymentSuccess(false);
                                setWaitingForPayment(false);
                            }}
                        >
                            Back
                        </button>
                    </div>
                )}

            </div>
        </>
    );
}

export default Cart;