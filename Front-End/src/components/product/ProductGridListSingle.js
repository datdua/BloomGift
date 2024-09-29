import PropTypes from "prop-types";
import React, { Fragment, useState } from "react";
import { Link } from "react-router-dom";
import { useToasts } from "react-toast-notifications";
import Rating from "./sub-components/ProductRating";
import ProductModal from "./ProductModal";

const ProductGridListSingle = ({
  product,
  currency,
  addToCart,
  addToWishlist,
  addToCompare,
  cartItems = [],
  wishlistItems = [],
  compareItems = [],
  sliderClassName,
  spaceBottomClass
}) => {
  console.log('Rendering product:', product);
  const [modalShow, setModalShow] = useState(false);
  const { addToast } = useToasts();

  const discountedPrice = product.discount > 0
    ? product.price * (1 - product.discount / 100)
    : null;
  const finalProductPrice = +(product.price * currency.currencyRate).toFixed(2);
  const finalDiscountedPrice = discountedPrice
    ? +(discountedPrice * currency.currencyRate).toFixed(2)
    : null;

  const cartItem = cartItems.find((item) => item.productID === product.productID);
  const wishlistItem = wishlistItems.find((item) => item.productID === product.productID);
  const compareItem = compareItems.find((item) => item.productID === product.productID);

  return (
    <Fragment>
      <div
        className={`col-xl-4 col-sm-6 ${sliderClassName ? sliderClassName : ""}`}
      >
        <div
          className={`product-wrap ${spaceBottomClass ? spaceBottomClass : ""}`}
        >
          <div className="product-img">
            <Link to={`${process.env.PUBLIC_URL}/product/${product.productID}`}>
              <img
                className="default-img"
                src={
                  product.images && product.images.length > 0
                    ? product.images[0].productImage
                    : `${process.env.PUBLIC_URL}/assets/img/product/fashion/1.jpg`
                }
                alt={product.productName || "Product Image"}
              />
              {product.images && product.images.length > 1 && (
                <img
                  className="hover-img"
                  src={product.images[1].productImage}
                  alt={product.productName || "Product Image"}
                />
              )}
            </Link>
            {product.discount > 0 || product.featured ? (
              <div className="product-img-badges">
                {product.discount > 0 ? (
                  <span className="pink">-{product.discount}%</span>
                ) : (
                  ""
                )}
                {product.featured ? <span className="purple">Featured</span> : ""}
              </div>
            ) : (
              ""
            )}
            <div className="product-action">
              <div className="pro-same-action pro-wishlist">
                <button
                  className={wishlistItem ? "active" : ""}
                  disabled={!!wishlistItem}
                  title={wishlistItem ? "Added to wishlist" : "Add to wishlist"}
                  onClick={() => addToWishlist(product, addToast)}
                >
                  <i className="pe-7s-like" />
                </button>
              </div>
              <div className="pro-same-action pro-cart">
                {product.quantity && product.quantity > 0 ? (
                  <button
                    onClick={() => addToCart(product, addToast)}
                    className={cartItem ? "active" : ""}
                    disabled={!!cartItem}
                    title={cartItem ? "Added to cart" : "Add to cart"}
                  >
                    <i className="pe-7s-cart"></i>{" "}
                    {cartItem ? "Added" : "Add to cart"}
                  </button>
                ) : (
                  <button disabled className="active">
                    Out of Stock
                  </button>
                )}
              </div>
              <div className="pro-same-action pro-quickview">
                <button onClick={() => setModalShow(true)} title="Quick View">
                  <i className="pe-7s-look" />
                </button>
              </div>
            </div>
          </div>
          <div className="product-content text-center">
            <h3>
              <Link to={`${process.env.PUBLIC_URL}/product/${product.productID}`}>
                {product.productName}
              </Link>
            </h3>
            {product.rating ? (
              <div className="product-rating">
                <Rating ratingValue={product.rating} />
              </div>
            ) : (
              ""
            )}
            <div className="product-price">
              {discountedPrice !== null ? (
                <Fragment>
                  <span>{currency.currencySymbol + finalDiscountedPrice}</span>{" "}
                  <span className="old">
                    {currency.currencySymbol + finalProductPrice}
                  </span>
                </Fragment>
              ) : (
                <span>{currency.currencySymbol + finalProductPrice} </span>
              )}
            </div>
          </div>
        </div>
        <div className="shop-list-wrap mb-30">
          <div className="row">
            <div className="col-xl-4 col-md-5 col-sm-6">
              <div className="product-list-image-wrap">
                <div className="product-img">
                  <Link to={`${process.env.PUBLIC_URL}/product/${product.productID}`}>
                    <img
                      className="default-img"
                      src={
                        product.images && product.images.length > 0
                          ? product.images[0].productImage
                          : `${process.env.PUBLIC_URL}/assets/img/product/fashion/1.jpg`
                      }
                      alt={product.productName || "Product Image"}
                    />
                    {product.images && product.images.length > 1 && (
                      <img
                        className="hover-img"
                        src={product.images[1].productImage}
                        alt={product.productName || "Product Image"}
                      />
                    )}
                  </Link>
                  {product.discount > 0 || product.featured ? (
                    <div className="product-img-badges">
                      {product.discount > 0 ? (
                        <span className="pink">-{product.discount}%</span>
                      ) : (
                        ""
                      )}
                      {product.featured ? <span className="purple">Featured</span> : ""}
                    </div>
                  ) : (
                    ""
                  )}
                </div>
              </div>
            </div>
            <div className="col-xl-8 col-md-7 col-sm-6">
              <div className="shop-list-content">
                <h3>
                  <Link to={`${process.env.PUBLIC_URL}/product/${product.productID}`}>
                    {product.productName}
                  </Link>
                </h3>
                <div className="product-list-price">
                  {discountedPrice !== null ? (
                    <Fragment>
                      <span>
                        {currency.currencySymbol + finalDiscountedPrice}
                      </span>{" "}
                      <span className="old">
                        {currency.currencySymbol + finalProductPrice}
                      </span>
                    </Fragment>
                  ) : (
                    <span>{currency.currencySymbol + finalProductPrice} </span>
                  )}
                </div>
                {product.rating ? (
                  <div className="rating-review">
                    <div className="product-list-rating">
                      <Rating ratingValue={product.rating} />
                    </div>
                  </div>
                ) : (
                  ""
                )}
                {product.description ? (
                  <p>{product.description}</p>
                ) : (
                  ""
                )}

                <div className="shop-list-actions d-flex align-items-center">
                  <div className="shop-list-btn btn-hover">
                    {product.quantity && product.quantity > 0 ? (
                      <button
                        onClick={() => addToCart(product, addToast)}
                        className={cartItem ? "active" : ""}
                        disabled={!!cartItem}
                        title={cartItem ? "Added to cart" : "Add to cart"}
                      >
                        <i className="pe-7s-cart"></i>{" "}
                        {cartItem ? "Added" : "Add to cart"}
                      </button>
                    ) : (
                      <button disabled className="active">
                        Out of Stock
                      </button>
                    )}
                  </div>

                  <div className="shop-list-wishlist ml-10">
                    <button
                      className={wishlistItem ? "active" : ""}
                      disabled={!!wishlistItem}
                      title={wishlistItem ? "Added to wishlist" : "Add to wishlist"}
                      onClick={() => addToWishlist(product, addToast)}
                    >
                      <i className="pe-7s-like" />
                    </button>
                  </div>
                  <div className="shop-list-compare ml-10">
                    <button
                      className={compareItem ? "active" : ""}
                      disabled={!!compareItem}
                      title={compareItem ? "Added to compare" : "Add to compare"}
                      onClick={() => addToCompare(product, addToast)}
                    >
                      <i className="pe-7s-shuffle" />
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      {/* product modal */}
      <ProductModal
        show={modalShow}
        onHide={() => setModalShow(false)}
        product={product}
        currency={currency}
        discountedPrice={finalDiscountedPrice}
        finalPrice={finalProductPrice}
        cartItem={cartItem}
        wishlistItem={wishlistItem}
        compareItem={compareItem}
        addToCart={addToCart}
        addToWishlist={addToWishlist}
        addToCompare={addToCompare}
        addToast={addToast}
      />
    </Fragment>
  );
};

ProductGridListSingle.propTypes = {
  addToCart: PropTypes.func,
  addToCompare: PropTypes.func,
  addToWishlist: PropTypes.func,
  cartItems: PropTypes.array,
  compareItems: PropTypes.array,
  currency: PropTypes.object,
  product: PropTypes.object,
  sliderClassName: PropTypes.string,
  spaceBottomClass: PropTypes.string,
  wishlistItems: PropTypes.array
};

export default ProductGridListSingle;
