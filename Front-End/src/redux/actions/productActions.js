import axios from "axios";

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

export const getAllProducts = (addToast) => {
  return async (dispatch) => {
    try {
      const response = await axios.get("http://localhost:8080/api/auth/product-customer/list-product-by-customer");
      dispatch(fetchProductsSuccess(response.data));
      return response.data;
    } catch (error) {
      console.error("Get all products failed:", error);
      if (addToast) addToast("Lấy dữ liệu sản phẩm thất bại!", { appearance: "error", autoDismiss: true });
      throw error;
    }
  };
}
