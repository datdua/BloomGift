import PropTypes from "prop-types";
import React, { Fragment, useEffect } from "react";
import { Link } from "react-router-dom";
import { useToasts } from "react-toast-notifications";
import { deleteFromCart, getCartItems } from "../../../redux/actions/cartActions";
import { useDispatch } from "react-redux";

const MenuCart = ({ cartData, currency }) => {
  const { addToast } = useToasts();
  const dispatch = useDispatch();
  const cartItemsArray = Object.values(cartData);
  const totalPriceCart = cartItemsArray.reduce((total, item) => total + (item.price * item.quantity), 0);
  
  useEffect(() => {
    dispatch(getCartItems(addToast));
  }, [dispatch, addToast]);
  
  const handleDeleteFromCart = async (item) => {
    await dispatch(deleteFromCart(item, addToast));
    dispatch(getCartItems(addToast));
  };
  return (
    <div className="shopping-cart-content">
      {cartItemsArray.length > 0 ? (
        <Fragment>
          <ul>
            {cartItemsArray.map((item) => (
              <li className="single-shopping-cart" key={item.productID}>
                <div className="shopping-cart-img">
                  <Link to={`${process.env.PUBLIC_URL}/product/${item.productID}`}>
                    <img
                      alt={item.productName}
                      src={item.image1}
                      className="img-fluid"
                    />
                  </Link>
                </div>
                <div className="shopping-cart-title">
                  <h4>
                    <Link to={`${process.env.PUBLIC_URL}/product/${item.productID}`}>
                      {item.productName}
                    </Link>
                  </h4>
                  <h6>Qty: {item.quantity}</h6>
                  <span>{currency.currencySymbol + item.price}</span>
                  <div className="cart-item-variation">
                    <span>Size: {item.sizeText}</span>
                    <span>Store: {item.storeName}</span>
                  </div>
                </div>
                <div className="shopping-cart-delete">
                  <button onClick={() => handleDeleteFromCart(item, addToast)}>
                    <i className="fa fa-times-circle" />
                  </button>
                </div>
              </li>
            ))}
          </ul>
          <div className="shopping-cart-total">
            <h4>
              Total:{" "}
              <span className="shop-total">
                {currency.currencySymbol + totalPriceCart.toFixed(2)}
              </span>
            </h4>
          </div>
          <div className="shopping-cart-btn btn-hover text-center">
            <Link className="default-btn" to={process.env.PUBLIC_URL + "/cart"}>
              View Cart
            </Link>
            <Link className="default-btn" to={process.env.PUBLIC_URL + "/checkout"}>
              Checkout
            </Link>
          </div>
        </Fragment>
      ) : (
        <p className="text-center">No items added to cart</p>
      )}
    </div>
  );
};

MenuCart.propTypes = {
  cartData: PropTypes.oneOfType([PropTypes.array, PropTypes.object]),
  currency: PropTypes.object,
  deleteFromCart: PropTypes.func
};

export default MenuCart;