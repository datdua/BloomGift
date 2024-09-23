import { FETCH_PRODUCTS_SUCCESS, GET_ALL_PRODUCTS } from "../actions/productActions";

const initState = {
  products: null,
  loading: false,
  error: null
};

const productReducer = (state = initState, action) => {
  switch (action.type) {
    case FETCH_PRODUCTS_SUCCESS:
      return {
        ...state,
        products: action.payload,
        loading: false,
        error: null,
      };
    case GET_ALL_PRODUCTS:
      return {
        ...state,
        products: action.payload,
        loading: false,
        error: null,
      };
    default:
      break;
  }

  return state;
};

export default productReducer;
