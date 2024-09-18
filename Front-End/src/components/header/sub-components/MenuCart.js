import PropTypes from "prop-types";
import React, { Fragment } from "react";
import { Link } from "react-router-dom";
import { useToasts } from "react-toast-notifications";

const MenuCart = ({ cartData, currency, deleteFromCart }) => {
  let cartTotalPrice = 0;
  const { addToast } = useToasts();

  return (
    <div className="shopping-cart-content">
      {cartData && cartData.length > 0 ? (
        <Fragment>
          <ul>
            {cartData.map((single, key) => {
              const discountedPrice = single.discount > 0
                ? single.price * (1 - single.discount / 100)
                : null;
              const finalProductPrice = (
                single.price * currency.currencyRate
              ).toFixed(2);
              const finalDiscountedPrice = discountedPrice
                ? (discountedPrice * currency.currencyRate).toFixed(2)
                : null;

              discountedPrice != null
                ? (cartTotalPrice += finalDiscountedPrice * single.quantity)
                : (cartTotalPrice += finalProductPrice * single.quantity);

              return (
                <li className="single-shopping-cart" key={key}>
                  <div className="shopping-cart-img">
                    <Link to={`${process.env.PUBLIC_URL}/product/${single.productID}`}>
                      <img
                        alt={single.productName}
                        src={single.images && single.images.length > 0
                          ? single.images[0].productImage // Correct access to productImage
                          : `${process.env.PUBLIC_URL}/assets/img/product/fashion/1.jpg`}
                        className="img-fluid"
                      />
                    </Link>
                  </div>
                  <div className="shopping-cart-title">
                    <h4>
                      <Link to={`${process.env.PUBLIC_URL}/product/${single.productID}`}>
                        {single.productName}
                      </Link>
                    </h4>
                    <h6>Qty: {single.quantity}</h6>
                    <span>
                      {discountedPrice !== null
                        ? currency.currencySymbol + finalDiscountedPrice
                        : currency.currencySymbol + finalProductPrice}
                    </span>
                    {single.colour && single.sizes && single.sizes.length > 0 ? (
                      <div className="cart-item-variation">
                        <span>Color: {single.colour}</span>
                        <span>Size: {single.sizes[0]}</span>
                      </div>
                    ) : null}
                  </div>
                  <div className="shopping-cart-delete">
                    <button onClick={() => deleteFromCart(single, addToast)}>
                      <i className="fa fa-times-circle" />
                    </button>
                  </div>
                </li>
              );
            })}
          </ul>
          <div className="shopping-cart-total">
            <h4>
              Total:{" "}
              <span className="shop-total">
                {currency.currencySymbol + cartTotalPrice.toFixed(2)}
              </span>
            </h4>
          </div>
          <div className="shopping-cart-btn btn-hover text-center">
            <Link className="default-btn" to={process.env.PUBLIC_URL + "/cart"}>
              View Cart
            </Link>
            <Link
              className="default-btn"
              to={process.env.PUBLIC_URL + "/checkout"}
            >
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
  cartData: PropTypes.array,
  currency: PropTypes.object,
  deleteFromCart: PropTypes.func,
};

export default MenuCart;
