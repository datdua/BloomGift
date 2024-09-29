import { 
  FETCH_PRODUCTS_SUCCESS, 
  SEARCH_PRODUCT, 
  GET_PRODUCT_DETAIL, 
  GET_NEW_PRODUCT,
  GET_PRODUCT_IMAGES 
} from "../actions/productActions";

const initState = {
  products: [],
  productDetail: null,
  newProducts: [],
  searchResults: []
};

const productReducer = (state = initState, action) => {
  switch (action.type) {
    case FETCH_PRODUCTS_SUCCESS:
      return {
        ...state,
        products: action.payload
      };
    
    case SEARCH_PRODUCT:
      return {
        ...state,
        searchResults: action.payload
      };
    
    case GET_PRODUCT_DETAIL:
      return {
        ...state,
        productDetail: action.payload
      };
    
    case GET_NEW_PRODUCT:
      return {
        ...state,
        newProducts: action.payload
      };
    case GET_PRODUCT_IMAGES:
      return {
        ...state,
        productImages: action.payload
      };
    default:
      return state;
  }
};

export default productReducer;
