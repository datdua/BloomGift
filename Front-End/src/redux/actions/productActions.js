import axios from "axios";

export const GET_ALL_PRODUCTS = "GET_ALL_PRODUCTS";

export const FETCH_PRODUCTS_SUCCESS = "FETCH_PRODUCTS_SUCCESS";

const fetchProductsSuccess = products => ({
  type: FETCH_PRODUCTS_SUCCESS,
  payload: products
});

// fetch products
export const fetchProducts = products => {
  return dispatch => {
    dispatch(fetchProductsSuccess(products));
  };
};

export const getAllProducts = () => {
  return async (dispatch) => {
    try {
      const response = await axios.get('http://localhost:8080/api/product/get-all');
      if (response.status !== 200) {
        throw new Error(`Lỗi khi nhận dữ liệu: ${response.status}`);
      }
      const products = response.data;
      dispatch({
        type: GET_ALL_PRODUCTS,
        payload: products
      });
      console.log(products);
      return products;
    } catch (error) {
      console.error("Fetch all products failed:", error);
      return Promise.reject(error);
    }
  }
}

export const getProductByStatusTrue = () => {
  return async (dispatch) => {
    try {
      const response = await axios.get('http://localhost:8080/api/product/products/status?productStatus=true');
      if (response.status !== 200) {
        throw new Error(`Lỗi khi nhận dữ liệu: ${response.status}`);
      }
      const products = response.data;
      dispatch({
        type: GET_ALL_PRODUCTS,
        payload: products
      });
      return products;
    } catch (error) {
      console.error("Fetch all products failed:", error);
      return Promise.reject(error);
    }
  }
}

export const getProductByStatusFalse = () => {
  return async (dispatch) => {
    try {
      const response = await axios.get('http://localhost:8080/api/product/products/status?productStatus=false');
      if (response.status !== 200) {
        throw new Error(`Lỗi khi nhận dữ liệu: ${response.status}`);
      }
      const products = response.data;
      dispatch({
        type: GET_ALL_PRODUCTS,
        payload: products
      });
      return products;
    } catch (error) {
      console.error("Fetch all products failed:", error);
      return Promise.reject(error);
    }
  }
}