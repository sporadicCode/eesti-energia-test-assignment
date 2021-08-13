// we also use proxy in package.jsom
const API_BASE_URL = "/api";

class ProductService {
    getAllProducts() {
        return fetch(API_BASE_URL + "/products");
    }
}

export default Object.freeze(new ProductService());