import axios from "axios";

export const FETCH_PRODUCTS_SUCCESS = "FETCH_PRODUCTS_SUCCESS";
export const SEARCH_PRODUCT = "SEARCH_PRODUCT";
export const GET_PRODUCT_DETAIL = "GET_PRODUCT_DETAIL";
export const GET_NEW_PRODUCT = "GET_NEW_PRODUCT";
export const GET_ALL_PRODUCTS = "GET_ALL_PRODUCTS";
export const GET_FEATURE_PRODUCT = "GET_FEATURE_PRODUCTS"
export const GET_BEST_SELLER_PRODUCTS = "GET_BEST_SELLER_PRODUCT";
export const CREATE_PRODUCT_SUCCESS = "CREATE_PRODUCT_SUCCESS";
export const CREATE_PRODUCT_FAILURE = "CREATE_PRODUCT_FAILURE";
export const GET_PRODUCT_IMAGES = "GET_PRODUCT_IMAGE";

const fetchProductsSuccess = products => ({
  type: FETCH_PRODUCTS_SUCCESS,
  payload: products
});

export const fetchProducts = products => {
  return dispatch => {
    dispatch(fetchProductsSuccess(products));
  };
};

// Create product success action
const createProductSuccess = (message) => ({
  type: CREATE_PRODUCT_SUCCESS,
  payload: message,
});

// Create product failure action
const createProductFailure = (error) => ({
  type: CREATE_PRODUCT_FAILURE,
  payload: error,
});

export const getAllProducts = (addToast) => {
  return async (dispatch) => {
    try {
      const response = await axios.get("https://bloomgift-bloomgift.azuremicroservices.io/api/customer/product/list-product-by-customer", {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('token')
      }});
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
        "https://bloomgift-bloomgift.azuremicroservices.io/api/auth/product-customer/search-product",
        { params }, {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + localStorage.getItem('token') 
        }
        }
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
      const response = await axios.get(`https://bloomgift-bloomgift.azuremicroservices.io/api/product/${productId}`,{
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('token') 
      }});
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

export const getProductBestSeller = (addToast, top) => {
  return async (dispatch) => {
    try {
      // Use string concatenation to prevent axios from encoding the curly braces
      const url = 'https://bloomgift-bloomgift.azuremicroservices.io/api/customer/product/get-product-best-seller/{Top}?top=' + top;
      
      const response = await axios.get(url, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('token')
      }});
      
      dispatch({
        type: GET_BEST_SELLER_PRODUCTS,
        payload: response.data,
      });
      
      return response.data;
    } catch (error) {
      console.error("Get best seller failed:", error);
      if (addToast) {
        addToast("Lấy sản phẩm bán chạy thất bại!", { appearance: "error", autoDismiss: true });
      }
      throw error;
    }
  }
};

export const getNewProduct = (addToast) => {
  return async (dispatch) => {
    try {
      const response = await axios.get(`https://bloomgift-bloomgift.azuremicroservices.io/api/customer/product/new-product`,{
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('token') 
      }});
      dispatch({
        type: GET_NEW_PRODUCT,
        payload: response.data,
      })
      return response.data;
    } catch (error) {
      console.error("Get new product failed:", error);
      if (addToast) addToast("Lấy sản phẩm mới thất bại!", { appearance: "error", autoDismiss: true });
      throw error;
    }
  }
}

export const getFeatureProduct = (addToast) => {
  return async (dispatch) => {
    try {
      const response = await axios.get(`https://bloomgift-bloomgift.azuremicroservices.io/api/customer/product/get-product-feature-true`,{
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('token') 
      }});
      dispatch({
        type: GET_FEATURE_PRODUCT,
        payload: response.data,
      })
      return response.data;
    } catch (error) {
      console.error("Get feature product failed:", error);
      if (addToast) addToast("Lấy sản phẩm nổi bật thất bại!", { appearance: "error", autoDismiss: true });
      throw error;
    }
  }
}

export const getProductByStatusFalse = () => {
  return async (dispatch) => {
    try {
      const response = await axios.get('https://bloomgift-bloomgift.azuremicroservices.io/api/product/products/status?productStatus=false');
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

export const getProductByStatusTrue = () => {
  return async (dispatch) => {
    try {
      const response = await axios.get('https://bloomgift-bloomgift.azuremicroservices.io/api/product/products/status?productStatus=true');
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

export const createProduct = (productRequest, imageFiles) => {
  return async (dispatch) => {
    try {

      const formData = new FormData();

      formData.append('productRequest', JSON.stringify(productRequest));

      imageFiles.forEach((file) => {
        formData.append('imageFiles', file);
      });
      const response = await axios.post(
        'https://bloomgift-bloomgift.azuremicroservices.io/api/product/create',
        formData,
        {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        }
      );
      if (response.status === 201) {
        dispatch(createProductSuccess('Product created successfully'));
      } else {
        throw new Error('Failed to create product');
      }
    } catch (error) {
      console.error('Create product failed:', error);
      dispatch(createProductFailure(error.message));
      return Promise.reject(error);
    }
  };
};

export const getProductImages = (productID) => {
  return async (dispatch) => {
    try {
      const response = await axios.get(`https://bloomgift-bloomgift.azuremicroservices.io/api/product-images/${productID}/images`,{
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + localStorage.getItem('token') 
      }});
      if (response.status !== 200) {
        throw new Error(`Failed to fetch images: ${response.status}`);
      }
      const images = response.data;
      dispatch({
        type: GET_PRODUCT_IMAGES,
        payload: images,
      });
      return images;
    } catch (error) {
      console.error('Fetch images failed:', error);
      return Promise.reject(error);
    }
  };
}