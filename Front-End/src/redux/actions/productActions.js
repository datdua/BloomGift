import axios from "axios";

export const FETCH_PRODUCTS_SUCCESS = "FETCH_PRODUCTS_SUCCESS";
export const SEARCH_PRODUCT = "SEARCH_PRODUCT";
export const GET_PRODUCT_DETAIL = "GET_PRODUCT_DETAIL";
export const GET_NEW_PRODUCT = "GET_NEW_PRODUCT";

const fetchProductsSuccess = products => ({
  type: FETCH_PRODUCTS_SUCCESS,
  payload: products
});

export const fetchProducts = products => {
  return dispatch => {
    dispatch(fetchProductsSuccess(products));
  };
};

export const getAllProducts = (addToast) => {
  return async (dispatch) => {
    try {
      const response = await axios.get("https://bloomgift.lemonhill-6b585cc3.eastasia.azurecontainerapps.io/api/auth/product-customer/list-product-by-customer");
      dispatch(fetchProductsSuccess(response.data));
      
      return response.data;
    } catch (error) {
      console.error("Get all products failed:", error);
      if (addToast) addToast("Lấy dữ liệu sản phẩm thất bại!", { appearance: "error", autoDismiss: true });
      throw error;
    }
  };
}


export const searchProduct = (
  addToast, 
  descriptionProduct, 
  colourProduct, 
  priceProduct, 
  productName, 
  categoryName, 
  createDate, 
  storeName, 
  sizeProduct, 
  page = 0, 
  size = 10
) => {
  return async (dispatch) => {
    try {
      const params = {};
      if (descriptionProduct) params.descriptionProduct = descriptionProduct;
      if (colourProduct) params.colourProduct = colourProduct;
      if (priceProduct) params.priceProduct = priceProduct;
      if (productName) params.productName = productName;
      if (categoryName) params.categoryName = categoryName;
      if (createDate) params.createDate = createDate;
      if (storeName) params.storeName = storeName;
      if (sizeProduct) params.sizeProduct = sizeProduct;
      params.page = page;
      params.size = size;

      const response = await axios.get(
        "https://bloomgift.lemonhill-6b585cc3.eastasia.azurecontainerapps.io/api/product/search",
        { params }
      );
      
      dispatch({
        type: SEARCH_PRODUCT,
        payload: response.data,
      });
      return response.data;
    } catch (error) {
      console.error("Search product failed:", error);
      if (addToast) addToast("Tìm kiếm sản phẩm thất bại!", { appearance: "error", autoDismiss: true });
      throw error;
    }
  };
};

export const getProductDetail = (addToast, productId) => {
  return async (dispatch) => {
    try {
      const response = await axios.get(`https://bloomgift.lemonhill-6b585cc3.eastasia.azurecontainerapps.io/api/product/${productId}`);
      dispatch({
        type: GET_PRODUCT_DETAIL,
        payload: response.data,
      })
      return response.data;
    } catch (error) {
      console.error("Get product detail failed:", error);
      if (addToast) addToast("Lấy chi tiết sản phẩm thất bại!", { appearance: "error", autoDismiss: true });
      throw error;
    }
  };
}

export const getNewProduct = (addToast) => {
  return async (dispatch) => {
    try {
      const response = await axios.get(`https://bloomgift.lemonhill-6b585cc3.eastasia.azurecontainerapps.io/api/product/new-product`);
      dispatch({
        type: GET_NEW_PRODUCT,
        payload: response.data,
      })
      return response.data;
      } catch (error) {
        console.error("Get new product failed:", error);
        if (addToast) addToast("Lấy sản phẩm mới thất bại!", { appearance:"error", autoDismiss: true });
        throw error;
        }
    }
}