import { GET_ALL_CATEGORIES } from "../actions/categoryAction";

const initialState = {
    categories: [],
  };
  
  const categoryReducer = (state = initialState, action) => {
    switch (action.type) {
      case GET_ALL_CATEGORIES:
        return {
          ...state,
          categories: action.payload,
        };
      default:
        return state;
    }
  };
  
  export default categoryReducer;
  