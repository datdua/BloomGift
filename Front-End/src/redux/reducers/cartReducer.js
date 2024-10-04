import uuid from "uuid/v4";
import {
  ADD_TO_CART,
  DECREASE_QUANTITY,
  DELETE_FROM_CART,
  DELETE_ALL_FROM_CART,
  UPDATE_CART_QUANTITY,
  GET_CART_ITEMS,
} from "../actions/cartActions";

const initState = [];

const cartReducer = (state = initState, action) => {
  // Ensure state is always an array
  const cartItems = Array.isArray(state) ? state : [];
  const product = action.payload;

  switch (action.type) {
    case ADD_TO_CART:
      if (!product) return cartItems;

      const existingItem = cartItems.find(item => {
        if (product.variation === undefined) {
          return item.id === product.id;
        } else {
          return item.id === product.id &&
                 item.selectedProductColor === product.selectedProductColor &&
                 item.selectedProductSize === product.selectedProductSize;
        }
      });

      if (existingItem) {
        return cartItems.map(item =>
          item.cartItemId === existingItem.cartItemId
            ? {
                ...item,
                quantity: product.quantity
                  ? item.quantity + product.quantity
                  : item.quantity + 1,
                selectedProductColor: product.selectedProductColor,
                selectedProductSize: product.selectedProductSize
              }
            : item
        );
      } else {
        return [
          ...cartItems,
          {
            ...product,
            quantity: product.quantity ? product.quantity : 1,
            cartItemId: uuid()
          }
        ];
      }

    case UPDATE_CART_QUANTITY:
      return cartItems.map(item =>
        item.cartItemId === product.productID
          ? { ...item, quantity: product.quantity }
          : item
      );

    case GET_CART_ITEMS:
      return action.payload;

    case DELETE_FROM_CART:
      return cartItems.filter(item => item.cartItemId !== product.cartItemId);

    case DELETE_ALL_FROM_CART:
      return [];

    default:
      return cartItems;
  }
};

export default cartReducer;