import PropTypes from "prop-types";
import React, { Fragment, useState } from "react";
import { Link, useHistory } from "react-router-dom";
import MetaTags from "react-meta-tags";
import { connect } from "react-redux";
import { BreadcrumbsItem } from "react-breadcrumbs-dynamic";
import { getDiscountPrice } from "../../helpers/product";
import LayoutOne from "../../layouts/LayoutOne";
import Breadcrumb from "../../wrappers/breadcrumb/Breadcrumb";
import { createOrder } from "../../redux/actions/orderAction";
import { useToasts } from "react-toast-notifications";

const Checkout = ({ location, cartItems, currency, createOrder }) => {
  const { pathname } = location;
  const history = useHistory();
  const getDiscountedPrice = (price, discount) => {
    return discount ? price - (price * discount / 100) : price;
  };

  let cartTotalPrice = 0;

  const { addToast } = useToasts();

  const [formData, setFormData] = useState({
    specificAddress: '',
    deliveryProvince: '',
    deliveryDistrict: '',
    deliveryWard: '',
    phone: '',
    note: '',
    email: '', // Added email to the initial state
    deliveryDateTime: new Date().toISOString(),
    promotionID: 0,
    point: 0,
    transfer: true,
    specificAddress: '',
    orderDetailRequests: cartItems.map(item => ({
      productID: item.productID,
      quantity: item.quantity,
      sizeID: item.selectedProductSize || 0, // Adjust according to your needs
    })),
  });

  const today = new Date().toISOString().split("T")[0];

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const isSucess = await createOrder(formData, addToast);
      if (isSucess) {
        history.push('/donhang');
      }
    } catch (error) {
      console.error("Error creating order:", error);
    }
  };

  return (
    <Fragment>
      <MetaTags>
        <title>BloomGift | Thanh toán</title>
        <meta
          name="description"
          content="Checkout page of flone react minimalist eCommerce template."
        />
      </MetaTags>
      <BreadcrumbsItem to={process.env.PUBLIC_URL + "/"}>Trang chủ</BreadcrumbsItem>
      <BreadcrumbsItem to={process.env.PUBLIC_URL + pathname}>
        Thanh toán
      </BreadcrumbsItem>
      <LayoutOne headerTop="visible">
        <Breadcrumb />
        <div className="checkout-area pt-95 pb-100">
          <div className="container">
            {cartItems && cartItems.length >= 1 ? (
              <form onSubmit={handleSubmit}>
                <div className="row">
                  <div className="col-lg-7">
                    <div className="billing-info-wrap">
                      <h3>Thông tin hoá đơn</h3>
                      <div className="row">
                        <div className="col-lg-6 col-md-6">
                          <div className="billing-info mb-20">
                            <label>Tên họ</label>
                            <input
                              type="text"
                              name="fullName"
                              value={formData.fullName || ''} // Ensuring controlled input
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-6 col-md-6">
                          <div className="billing-info mb-20">
                            <label>Tên khách hàng</label>
                            <input
                              type="text"
                              name="customerName"
                              value={formData.customerName || ''} // Ensuring controlled input
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-12">
                          <div className="billing-info mb-20">
                            <label>Tên công ty</label>
                            <input
                              type="text"
                              name="companyName"
                              value={formData.companyName || ''} // Ensuring controlled input
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-12">
                          <div className="billing-info mb-20">
                            <label>Địa chỉ</label>
                            <input
                              className="billing-address"
                              placeholder="House number and street name"
                              type="text"
                              name="address"
                              value={formData.address || ''} // Ensuring controlled input
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-12">
                          <div className="billing-info mb-20">
                            <label>Ngày và thời gian giao hàng</label>
                            <input
                              type="datetime-local"
                              name="deliveryDateTime"
                              value={formData.deliveryDateTime}
                              onChange={handleChange}
                              min={today}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-12">
                          <div className="billing-info mb-20">
                            <label>Quận/ Huyện</label>
                            <input
                              placeholder="Quận 7, Huyện Nhà Bè, ... Etc"
                              type="text"
                              name="deliveryDistrict" // Ensure name matches formData state
                              value={formData.deliveryDistrict || ''} // Ensuring controlled input
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-12">
                          <div className="billing-info mb-20">
                            <label>Tỉnh/ Thành phố</label>
                            <input
                              type="text"
                              name="deliveryProvince" // Ensure name matches formData state
                              value={formData.deliveryProvince || ''} // Ensuring controlled input
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-6 col-md-6">
                          <div className="billing-info mb-20">
                            <label>Phường/ Xã</label>
                            <input
                              type="text"
                              name="deliveryWard" // Ensure name matches formData state
                              value={formData.deliveryWard || ''} // Ensuring controlled input
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-6 col-md-6">
                          <div className="billing-info mb-20">
                            <label>Mã bưu điện/ Zip</label>
                            <input
                              type="number"
                              name="zip"
                              value={formData.zip || ''} // Ensuring controlled input
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-12">
                          <div className="billing-info mb-20">
                            <label>Địa chỉ cụ thể</label>
                            <input
                              className="billing-address"
                              placeholder="Địa chỉ giao tận nơi"
                              type="text"
                              name="specificAddress"
                              value={formData.specificAddress}
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-6 col-md-6">
                          <div className="billing-info mb-20">
                            <label>Số điện thoại</label>
                            <input
                              type="number"
                              name="phone"
                              value={formData.phone || ''} // Ensuring controlled input
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                        <div className="col-lg-6 col-md-6">
                          <div className="billing-info mb-20">
                            <label>Địa chỉ mail</label>
                            <input
                              type="text"
                              name="email"
                              value={formData.email || ''} // Ensuring controlled input
                              onChange={handleChange}
                              required
                            />
                          </div>
                        </div>
                      </div>

                      <div className="additional-info-wrap">
                        <h4>Thông tin thêm</h4>
                        <div className="additional-info">
                          <label>Ghi chú</label>
                          <textarea
                            placeholder="Notes about your order, e.g. special notes for delivery."
                            name="note"
                            value={formData.note || ''} // Ensuring controlled input
                            onChange={handleChange}
                          />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div className="col-lg-5">
                    <div className="your-order-area">
                      <h3>Đơn hàng</h3>
                      <div className="your-order-wrap gray-bg-4">
                        <div className="your-order-product-info">
                          <div className="your-order-top">
                            <ul>
                              <li>Sản phẩm</li>
                              <li>Tổng tiền</li>
                            </ul>
                          </div>
                          <div className="your-order-middle">
                            <ul>
                              {cartItems.map((cartItem, index) => {
                                const discountedPrice = getDiscountedPrice(cartItem.price, cartItem.discount);
                                const finalDiscountedPrice = +(discountedPrice * currency.currencyRate).toFixed(2);
                                cartTotalPrice += finalDiscountedPrice * cartItem.quantity;

                                return (
                                  <li key={index}>
                                    <Link to={process.env.PUBLIC_URL + "/product/" + cartItem.productID}>
                                      {cartItem.productName} × {cartItem.quantity}
                                    </Link>
                                    <span>
                                      {currency.currencySymbol}
                                      {(finalDiscountedPrice * cartItem.quantity).toFixed(2)}
                                    </span>
                                  </li>
                                );
                              })}
                            </ul>
                          </div>

                          <div className="your-order-bottom">
                            <ul>
                              <li>Tổng cộng</li>
                              <li className="total">
                                {currency.currencySymbol}
                                {cartTotalPrice.toFixed(2)}
                              </li>
                            </ul>
                          </div>
                        </div>
                        <div className="place-order">
                          <button className="btn-hover" type="submit">
                            Đặt hàng
                          </button>
                        </div>
                      </div>
                    </div>
                      <div className="cart-tax" style={{marginTop: "20px"}}>
                        <div className="title-wrap">
                          <h4 className="cart-bottom-title section-bg-gray ">
                            Estimate Shipping And Tax
                          </h4>
                        </div>
                        <div className="tax-wrapper">
                          <p>
                            Enter your destination to get a shipping estimate.
                          </p>
                          <div className="tax-select-wrapper">
                            <div className="tax-select">
                              <label>* Country</label>
                              <select className="email s-email s-wid">
                                <option>Bangladesh</option>
                                <option>Albania</option>
                                <option>Åland Islands</option>
                                <option>Afghanistan</option>
                                <option>Belgium</option>
                              </select>
                            </div>
                            <div className="tax-select">
                              <label>* Region / State</label>
                              <select className="email s-email s-wid">
                                <option>Bangladesh</option>
                                <option>Albania</option>
                                <option>Åland Islands</option>
                                <option>Afghanistan</option>
                                <option>Belgium</option>
                              </select>
                            </div>
                            <div className="tax-select">
                              <label>* Zip/Postal Code</label>
                              <input type="text" />
                            </div>
                            <button className="cart-btn-2" type="submit">
                              Get A Quote
                            </button>
                          </div>
                        </div>
                      </div>
                  </div>
                </div>
              </form>
            ) : (
              <h4>Không có sản phẩm nào trong giỏ hàng.</h4>
            )}
          </div>
        </div>
      </LayoutOne>
    </Fragment>
  );
};

Checkout.propTypes = {
  location: PropTypes.object,
  cartItems: PropTypes.array,
  currency: PropTypes.object,
  createOrder: PropTypes.func,
  addToast: PropTypes.func,
};

const mapStateToProps = (state) => {
  return {
    cartItems: state.cartData,
    currency: state.currencyData,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    createOrder: (formData, addToast) => dispatch(createOrder(formData, addToast)),
  };
};

export default connect(mapStateToProps, mapDispatchToProps)(Checkout);
