import PropTypes from "prop-types";
import React, { Fragment, useEffect } from "react";
import MetaTags from "react-meta-tags";
import { BreadcrumbsItem } from "react-breadcrumbs-dynamic";
import { connect } from "react-redux";
import LayoutOne from "../../layouts/LayoutOne";
import Breadcrumb from "../../wrappers/breadcrumb/Breadcrumb";
import RelatedProductSlider from "../../wrappers/product/RelatedProductSlider";
import ProductDescriptionTab from "../../wrappers/product/ProductDescriptionTab";
import ProductImageDescription from "../../wrappers/product/ProductImageDescription";
import { getProductDetail } from "../../redux/actions/productActions";

const Product = ({ location, product, getProductDetail, match }) => {
  const { pathname } = location;
  const productId = match.params.id;

  useEffect(() => {
    if (productId) {
      getProductDetail(null, productId);
    }
  }, [productId, getProductDetail]);

  if (!product) {
    return <div>Loading...</div>;
  }

  return (
    <Fragment>
      <MetaTags>
        <title>BloomGift | Trang sản phẩm</title>
        <meta
          name="description"
          content="Product page of flone react minimalist eCommerce template."
        />
      </MetaTags>

      <BreadcrumbsItem to={process.env.PUBLIC_URL + "/"}>Trang chủ</BreadcrumbsItem>
      <BreadcrumbsItem to={process.env.PUBLIC_URL + pathname}>
        Shop Product
      </BreadcrumbsItem>

      <LayoutOne headerTop="visible">
        {/* breadcrumb */}
        <Breadcrumb />

        {/* product description with image */}
        <ProductImageDescription 
          spaceTopClass="pt-100"
          spaceBottomClass="pb-100"
          product={product}
        />

        {/* product description tab */}
        <ProductDescriptionTab
          spaceBottomClass="pb-90"
          productFullDesc={product}
        />

        {/* related product slider */}
        <RelatedProductSlider
          spaceBottomClass="pb-95"
          category={product.categoryName}
        />
      </LayoutOne>
    </Fragment>
  );
};

Product.propTypes = {
  location: PropTypes.object,
  product: PropTypes.object,
  getProductDetail: PropTypes.func,
  match: PropTypes.object
};

const mapStateToProps = (state) => {
  return {
    product: state.productData.productDetail
  };
};

const mapDispatchToProps = {
  getProductDetail
};

export default connect(mapStateToProps, mapDispatchToProps)(Product);