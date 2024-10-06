import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

export const ADD_TO_CART = "ADD_TO_CART";
export const UPDATE_CART_QUANTITY = "UPDATE_CART_QUANTITY";
export const DELETE_FROM_CART = "DELETE_FROM_CART";
export const DELETE_ALL_FROM_CART = "DELETE_ALL_FROM_CART";
export const GET_CART_ITEMS = "GET_CART_ITEMS";
export const CLEAR_CART = "CLEAR_CART";


// Function to extract accountID from JWT
const getAccountIDFromToken = () => {
  const token = localStorage.getItem('token'); // Assume the token is stored in localStorage
  if (!token) return null;

  try {
    const decodedToken = jwtDecode(token);
    return decodedToken.accountID;
  } catch (error) {
    console.error('Error decoding token:', error);
    return null;
  }
};

export const addToCart = (
  item,
  addToast,
  quantityCount,
  selectedProductColor,
  selectedProductSize
) => {
  return async (dispatch, getState) => {
    try {
      const accountID = getAccountIDFromToken();
      if (!accountID) {
        throw new Error("No valid account ID found");
      }

      const cartItems = (getState().cartData && getState().cartData.cartItems) || [];
      console.log("Current state:", getState());

      const existingCartItem = cartItems.find(
        (cartItem) =>
          cartItem.productID === item.productID &&
          cartItem.selectedProductSize === selectedProductSize
      );

      if (existingCartItem) {
        dispatch(
          updateCartQuantity(
            existingCartItem,
            existingCartItem.quantity + quantityCount,
            addToast
          )
        );
      } else {
        let url = `https://bloomgift-bloomgift.azuremicroservices.io/api/customer/cart/add?accountId=${accountID}&productId=${item.productID}&quantity=${quantityCount}`;

        if (selectedProductSize) {
          url = `https://bloomgift-bloomgift.azuremicroservices.io/api/customer/cart/add-with-size?accountId=${accountID}&productId=${item.productID}&quantity=${quantityCount}&sizeID=${selectedProductSize}`;
        }

        await axios.post(
          url,
          {},
          {
            headers: {
              "Content-Type": "application/json",
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          }
        );

        if (addToast) {
          addToast("Đã thêm vào giỏ hàng", {
            appearance: "success",
            autoDismiss: true,
          });
        }

        dispatch({
          type: ADD_TO_CART,
          payload: {
            ...item,
            quantity: quantityCount,
            selectedProductColor: selectedProductColor || item.selectedProductColor || null,
            selectedProductSize: selectedProductSize || item.selectedProductSize || null,
          },
        });
      }
    } catch (error) {
      console.error("Error adding to cart:", error);
      if (addToast) {
        addToast("Lỗi khi thêm vào giỏ hàng", {
          appearance: "error",
          autoDismiss: true,
        });
      }
    }
  };
};

export const updateCartQuantity = (item, newQuantity, addToast) => {
  return async dispatch => {
    try {
      const accountID = getAccountIDFromToken();
      if (!accountID) {
        throw new Error('No valid account ID found');
      }

      const response = await axios.put(
        `https://bloomgift-bloomgift.azuremicroservices.io/api/customer/cart/update-quantity?accountId=${accountID}&productId=${item.productID}&quantity=${newQuantity}`,
        {},
        {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        }
      );

      if (response.status === 200 && addToast) {
        addToast("Cập nhật giỏ hàng thành công", {
          appearance: "success",
          autoDismiss: true          
        });
        dispatch(getCartItems(addToast));
      }

      // Dispatch the updated quantity to Redux
      dispatch({
        type: "UPDATE_CART_QUANTITY",
        payload: { 
          productID: item.productID, 
          quantity: newQuantity 
        }
      });
    } catch (error) {
      console.error("Error updating cart:", error);
      const errorMessage = error.response?.data?.message || "Không đủ số lượng sản phẩm"
      addToast(`Cập nhật thất bại: ${errorMessage}`, { appearance: "error", autoDismiss: true });
    }
  };
};


// delete from cart
export const deleteFromCart = (item, addToast) => {
  return async dispatch => {
    try {
      const accountID = getAccountIDFromToken();
      if (!accountID) {
        throw new Error('No valid account ID found');
      }

      await axios.delete(`https://bloomgift-bloomgift.azuremicroservices.io/api/customer/cart/remove?accountId=${accountID}&productId=${item.productID}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });

      if (addToast) {
        addToast("Đã xoá sản phẩm khỏi giỏ hàng", { appearance: "success", autoDismiss: true });
        dispatch(getCartItems(addToast));
      }

      dispatch({ type: DELETE_FROM_CART, payload: item });
    } catch (error) {
      console.error("Error removing from cart:", error);
      if (addToast) {
        addToast("Lỗi khi xoá sản phẩm", { appearance: "error", autoDismiss: true });
      }
    }
  };
};

// delete all from cart
export const deleteAllFromCart = (addToast) => {
  return async dispatch => {
    try {
      const accountID = getAccountIDFromToken();
      if (!accountID) {
        throw new Error('No valid account ID found');
      }

      // Note: There's no specific API for clearing the entire cart, so we'll assume it's handled client-side
      // You may need to implement a server-side endpoint for this if required

      if (addToast) {
        addToast("Đã xoá tất cả sản phẩm khỏi giỏ hàng", {
          appearance: "success",
          autoDismiss: true
        });
      }

      dispatch({ type: DELETE_ALL_FROM_CART });
    } catch (error) {
      console.error("Error clearing cart:", error);
      if (addToast) {
        addToast("Lỗi khi xoá giỏ hàng", { appearance: "error", autoDismiss: true });
      }
    }
  };
};

export const cartItemStock = async (productId) => {
  try {
    const accountID = getAccountIDFromToken();
    if (!accountID) {
      throw new Error('No valid account ID found');
    }

    const response = await axios.get(`https://bloomgift-bloomgift.azuremicroservices.io/api/customer/cart/view/${accountID}`, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });

    const { cartItems } = response.data;
    const item = cartItems[productId];

    if (!item) {
      console.warn(`Product with ID ${productId} not found in cart`);
      return 0; // or another default value
    }

    // Assuming the API returns the available stock for each item
    // If it doesn't, you might need to make another API call to get the stock information
    return item.quantity; // or item.availableStock if that's provided by the API

  } catch (error) {
    console.error("Error fetching cart item stock:", error);
    return 0; // or another default value
  }
};

export const getCartItems = (addToast) => {
  return async (dispatch) => {
    try {
      const accountID = getAccountIDFromToken();
      if (!accountID) {
        console.error("No valid account ID found");
        if (addToast) {
          addToast("Lỗi xác thực, vui lòng đăng nhập lại", {
            appearance: "error",
            autoDismiss: true,
          });
        }
        return;
      }

      const response = await axios.get(
        `https://bloomgift-bloomgift.azuremicroservices.io/api/customer/cart/view/${accountID}`,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );

      if (response.data && response.data.cartItems) {
        dispatch({ type: GET_CART_ITEMS, payload: Object.values(response.data.cartItems) });
      }
    } catch (error) {
      console.error("Error fetching cart items:", error);
      if (addToast) {
        addToast("Lỗi khi lấy dữ liệu giỏ hàng", {
          appearance: "error",
          autoDismiss: true,
        });
      }
    }
  };
};

export const clearCart = () => {
  return {
    type: "CLEAR_CART"
  };
};