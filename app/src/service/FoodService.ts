// we also use proxy in package.jsom
const API_BASE_URL = "/api/food";

class FoodService {
    getAllFood = () : Promise<Response> => {
        return fetch(API_BASE_URL);
    }
}

export default Object.freeze(new FoodService());