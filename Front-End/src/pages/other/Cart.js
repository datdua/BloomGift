import PropTypes from "prop-types";
import React, { Fragment, useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useToasts } from "react-toast-notifications";
import MetaTags from "react-meta-tags";
import { BreadcrumbsItem } from "react-breadcrumbs-dynamic";
import { connect, useDispatch, useSelector } from "react-redux";
import {
  addToCart,
  updateCartQuantity,
  deleteFromCart,
  deleteAllFromCart,
  getCartItems
} from "../../redux/actions/cartActions";
import LayoutOne from "../../layouts/LayoutOne";
import Breadcrumb from "../../wrappers/breadcrumb/Breadcrumb";

const Cart = ({
  location,
  cartData,
  currency,
  updateCartQuantity,
  deleteFromCart,
  deleteAllFromCart
}) => {
  const { addToast } = useToasts();
  const { pathname } = location;
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(getCartItems(addToast));
  }, [dispatch, addToast]);

  const handleQuantityChange = (item, change) => {
    const newQuantity = item.quantity + change;
    if (newQuantity > 0) {
      updateCartQuantity(item, newQuantity, addToast);
      dispatch(getCartItems(addToast));
    } else {
      addToast("Số lượng không được nhỏ hơn 1 sản phẩm", {
        appearance: "error",
        autoDismiss: true,
      });
    }
    // if (newQuantity > item.quantity) {
    //   addToast("Số lượng hàng không đủ!", {
    //     appearance: "error",
    //     autoDismiss: true
    //   });
    // }
  }
  
    const updatedCartItems = cartData ? cartData : {};

    const cartItemsArray = Object.values(updatedCartItems);

    const totalPriceCart = cartItemsArray.reduce(
      (total, item) => total + item.price * item.quantity,
      0
    );

    console.log('cartData:', cartData);
    console.log('updatedCartItems:', updatedCartItems);
  
    return (
      <Fragment>
        <MetaTags>
          <title>BloomGift | Giỏ hàng</title>
          <meta
            name="description"
            content="Cart page of BloomGift eCommerce template."
          />
        </MetaTags>
        <BreadcrumbsItem to={process.env.PUBLIC_URL + "/"}>Trang chủ</BreadcrumbsItem>
        <BreadcrumbsItem to={process.env.PUBLIC_URL + pathname}>
          Giỏ hàng
        </BreadcrumbsItem>
        <LayoutOne headerTop="visible">
          <Breadcrumb />
          <div className="cart-main-area pt-90 pb-100">
            <div className="container">
              {cartItemsArray.length >= 1 ? (
                <Fragment>
                  <h3 className="cart-page-title">Your cart items</h3>
                  <div className="row">
                    <div className="col-12">
                      <div className="table-content table-responsive cart-table-content">
                        <table>
                          <thead>
                            <tr>
                              <th>Hình ảnh</th>
                              <th>Tên</th>
                              <th>Giá</th>
                              <th>Số lượng</th>
                              <th>Tổng tiền</th>
                              <th>Hành động</th>
                            </tr>
                          </thead>
                          <tbody>
                            {cartItemsArray.map((item) => (
                              <tr key={item.productID}>
                                <td className="product-thumbnail">
                                  <Link to={process.env.PUBLIC_URL + "/product/" + item.productID}>
                                    <img
                                      className="img-fluid"
                                      src={item.image1}
                                      alt={item.productName}
                                    />
                                  </Link>
                                </td>
                                <td className="product-name">
                                  <Link to={process.env.PUBLIC_URL + "/product/" + item.productID}>
                                    {item.productName}
                                  </Link>
                                  <div className="cart-item-variation">
                                    <span>Size: {item.sizeText}</span>
                                    <span>Store: {item.storeName}</span>
                                  </div>
                                </td>
                                <td className="product-price-cart">
                                  <span className="amount">
                                    {currency.currencySymbol + item.price.toFixed(2)}
                                  </span>
                                </td>
                                <td className="product-quantity">
                                  <div className="cart-plus-minus">
                                    <button
                                      className="dec qtybutton"
                                      onClick={() => handleQuantityChange(item, -1)}
                                    >
                                      -
                                    </button>
                                    <input
                                      className="cart-plus-minus-box"
                                      type="text"
                                      value={item.quantity}
                                      readOnly
                                    />
                                    <button
                                      className="inc qtybutton"
                                      onClick={() => handleQuantityChange(item, 1)}
                                    >
                                      +
                                    </button>
                                  </div>
                                </td>
                                <td className="product-subtotal">
                                  {currency.currencySymbol + (item.price * item.quantity).toFixed(2)}
                                </td>
                                <td className="product-remove">
                                  <button onClick={() => deleteFromCart(item, addToast)}>
                                    <i className="fa fa-times"></i>
                                  </button>
                                </td>
                              </tr>
                            ))}
                          </tbody>
                        </table>
                      </div>
                    </div>
                  </div>
                  {/* Cart Total */}
                  <div className="row">
                  <div className="col-lg-12">
                    <div className="cart-shiping-update-wrapper">
                      <div className="cart-shiping-update">
                        <Link
                          to={process.env.PUBLIC_URL + "/shop-grid-standard"}
                        >
                          Continue Shopping
                        </Link>
                      </div>
                      <div className="cart-clear">
                      <Link to={process.env.PUBLIC_URL + "/checkout"}>
                          Proceed to checkout
                        </Link>
                      </div>
                    </div>
                  </div>
                </div>
                <div className="row">
                  <div className="col-lg-4 col-md-6">
                    <div className="discount-code-wrapper">
                      <div className="title-wrap">
                        <h4 className="cart-bottom-title section-bg-gray">
                          Use Coupon Code
                        </h4>
                      </div>
                      <div className="discount-code">
                        <p>Enter your coupon code if you have one.</p>
                        <form>
                          <input type="text" required name="name" />
                          <button className="cart-btn-2" type="submit">
                            Apply Coupon
                          </button>
                        </form>
                      </div>
                    </div>
                  </div>                
                    <div className="col-lg-4 col-md-6 ml-auto">
                      <div className="cart-page-total">
                        <h2>Cart totals</h2>
                        <ul>
                          <li>
                            Total{" "}
                            <span>{currency.currencySymbol + totalPriceCart.toFixed(2)}</span>
                          </li>
                        </ul>                    
                      </div>
                    </div>
                </div>
                </Fragment>
              ) : (
                <div className="row">
                  <div className="col-lg-12">
                    <div className="item-empty-area text-center">
                      <div className="item-empty-area__icon mb-30">
                        <i className="pe-7s-cart"></i>
                      </div>
                      <div className="item-empty-area__text">
                        No items found in cart <br />{" "}
                        <Link to={process.env.PUBLIC_URL + "/cuahang"}>
                          Shop Now
                        </Link>
                      </div>
                    </div>
                  </div>
                </div>
              )}
            </div>
          </div>
        </LayoutOne>
      </Fragment>
    );
  };

  Cart.propTypes = {
    addToCart: PropTypes.func,
    cartItems: PropTypes.array,
    currency: PropTypes.object,
    decreaseQuantity: PropTypes.func,
    location: PropTypes.object,
    deleteAllFromCart: PropTypes.func,
    deleteFromCart: PropTypes.func,
    cartData: PropTypes.oneOfType([PropTypes.array, PropTypes.object]),
  };

  const mapStateToProps = state => {
    return {
      cartData: state.cartData,
      currency: state.currencyData
    };
  };

  const mapDispatchToProps = dispatch => {
    return {
      addToCart: (item, addToast, quantityCount) => {
        dispatch(addToCart(item, addToast, quantityCount));
      },
      updateCartQuantity: (item, newQuantity, addToast) => {
        if (newQuantity > 0) {
          dispatch(updateCartQuantity(item, newQuantity, addToast));
        } else {
          addToast("Số lượng phải là số dương", { appearance: "error", autoDismiss: true });
        }
      },
      deleteFromCart: (item, addToast) => {
        dispatch(deleteFromCart(item, addToast));
      },
      deleteAllFromCart: addToast => {
        dispatch(deleteAllFromCart(addToast));
      }
    };
  };


  export default connect(mapStateToProps, mapDispatchToProps)(Cart);