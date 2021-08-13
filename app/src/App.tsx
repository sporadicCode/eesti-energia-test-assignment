import './App.css';
import ProductList from "./components/ProductList";
import Cart from "./components/Cart";
import CartService from "./service/CartService";
import Product from "./model/Product";
import {useEffect, useState} from "react";
import FoodService from "./service/FoodService";
import ClothesService from "./service/ClothesService";
import ToysService from "./service/ToysService";

function App() {

    const [cart, setCart] = useState<Map<Product, number>>(new Map<Product, number>());
    const [total, setTotal] = useState<number>(0);
    const [food, setFood] = useState<Array<Product>>([]);
    const [clothes, setClothes] = useState<Array<Product>>([]);
    const [toys, setToys] = useState<Array<Product>>([]);

    const updateCart = () => {
        CartService
            .getCart()
            .then(resp => resp.json())
            .then(data => {
                const strings : Array<string> = Object.keys(data);
                const productMap : Map<Product, number> = new Map<Product, number>();
                strings.forEach((key: string) => {
                    const product : Product = JSON.parse(key);
                    const quantity : number = data[key];
                    productMap.set(product, quantity);
                })
                setCart(productMap);
            });
    }

    const updateTotal = () => {
        CartService
            .getTotal()
            .then(resp => resp.json())
            .then(data => setTotal(data));
    }

    const update = () : void => {
        updateCart();
        updateTotal();
    }

    useEffect(() => {
        refreshProducts();
        updateCart();
        updateTotal();
    }, [])
    
    const refreshProducts = () : void => {
        FoodService
            .getAllFood()
            .then(resp => resp.json())
            .then(data => setFood(data));
        ClothesService
            .getAllClothes()
            .then(resp => resp.json())
            .then(data => setClothes(data));
        ToysService
            .getAllToys()
            .then(resp => resp.json())
            .then(data => setToys(data));
    }

  return (
      <div className="d-flex flex-column bg-light">
        <ProductList
            update={update}
            food={food}
            clothes={clothes}
            toys={toys}
        />
        <Cart
            cart={cart}
            total={total}
            update={update}
            refreshProducts={refreshProducts}
        />
      </div>
  );
}

export default App;
