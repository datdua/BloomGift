import PropTypes from "prop-types";
import React, { Fragment, useState, useEffect } from "react";
import MetaTags from "react-meta-tags";
import Paginator from "react-hooks-paginator";
import { BreadcrumbsItem } from "react-breadcrumbs-dynamic";
import { connect } from "react-redux";
import { getSortedProducts } from "../../helpers/product";
import LayoutOne from "../../layouts/LayoutOne";
import Breadcrumb from "../../wrappers/breadcrumb/Breadcrumb";
import ShopSidebar from "../../wrappers/product/ShopSidebar";
import ShopTopbar from "../../wrappers/product/ShopTopbar";
import ShopProducts from "../../wrappers/product/ShopProducts";
import { getAllProducts, searchProduct } from "../../redux/actions/productActions";

const ShopGridFullWidth = ({ location, products, getAllProducts, searchProduct }) => {
  const [layout, setLayout] = useState("grid three-column");
  const [sortType, setSortType] = useState("");
  const [sortValue, setSortValue] = useState("");
  const [filterSortType, setFilterSortType] = useState("");
  const [filterSortValue, setFilterSortValue] = useState("");
  const [offset, setOffset] = useState(0);
  const [currentPage, setCurrentPage] = useState(1);
  const [currentData, setCurrentData] = useState([]);
  const [sortedProducts, setSortedProducts] = useState([]);
  const [searchTerm, setSearchTerm] = useState(""); // Add state for search term

  const pageLimit = 15;
  const { pathname } = location;

  useEffect(() => {
    if (searchTerm) {
      // Fetch products based on the search term
      searchProduct(searchTerm)
        .then((products) => {
          setSortedProducts(products);
          setCurrentData(products.slice(offset, offset + pageLimit));
        })
        .catch((error) => {
          console.error("Failed to load search results", error);
        });
    } else {
      // Fetch all products if no search term
      getAllProducts()
        .then((products) => {
          setSortedProducts(products);
          setCurrentData(products.slice(offset, offset + pageLimit));
        })
        .catch((error) => {
          console.error("Failed to load products", error);
        });
    }
  }, [offset, searchTerm, getAllProducts, searchProduct]);

  const getLayout = (layout) => {
    setLayout(layout);
  };

  const getSortParams = (sortType, sortValue) => {
    setSortType(sortType);
    setSortValue(sortValue);
  };

  const getFilterSortParams = (sortType, sortValue) => {
    setFilterSortType(sortType);
    setFilterSortValue(sortValue);
  };

  useEffect(() => {
    const sortedProductsList = getSortedProducts(products, sortType, sortValue);
    const filterSortedProductsList = getSortedProducts(
      sortedProductsList,
      filterSortType,
      filterSortValue
    );
    setSortedProducts(filterSortedProductsList);
    setCurrentData(filterSortedProductsList.slice(offset, offset + pageLimit));
  }, [offset, products, sortType, sortValue, filterSortType, filterSortValue]);

  const handleSearch = (searchQuery) => {
    setSearchTerm(searchQuery);
  };

  return (
    <Fragment>
      <MetaTags>
        <title>Bloom Gift | Gian hàng</title>
        <meta name="description" content="Shop page of flone react minimalist eCommerce template." />
      </MetaTags>

      <BreadcrumbsItem to={process.env.PUBLIC_URL + "/"}>Trang chủ</BreadcrumbsItem>
      <BreadcrumbsItem to={process.env.PUBLIC_URL + pathname}>Shop</BreadcrumbsItem>

      <LayoutOne headerTop="visible">
        {/* breadcrumb */}
        <Breadcrumb />

        <div className="shop-area pt-95 pb-100">
          <div className="container-fluid">
            <div className="row">
              <div className="col-lg-3 order-2 order-lg-1">
                {/* shop sidebar */}
                <ShopSidebar
                  products={products}
                  getSortParams={getSortParams}
                  sideSpaceClass="mr-30"
                  handleSearch={handleSearch} // Add search handler
                />
              </div>
              <div className="col-lg-9 order-1 order-lg-2">
                {/* shop topbar default */}
                <ShopTopbar
                  getLayout={getLayout}
                  getFilterSortParams={getFilterSortParams}
                  productCount={products.length}
                  sortedProductCount={currentData.length}
                />

                {/* shop page content default */}
                <ShopProducts layout={layout} products={currentData} />

                {/* shop product pagination */}
                <div className="pro-pagination-style text-center mt-30">
                  <Paginator
                    totalRecords={sortedProducts.length}
                    pageLimit={pageLimit}
                    pageNeighbours={2}
                    setOffset={setOffset}
                    currentPage={currentPage}
                    setCurrentPage={setCurrentPage}
                    pageContainerClass="mb-0 mt-0"
                    pagePrevText="«"
                    pageNextText="»"
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </LayoutOne>
    </Fragment>
  );
};

ShopGridFullWidth.propTypes = {
  location: PropTypes.object,
  products: PropTypes.array,
  getAllProducts: PropTypes.func.isRequired,
  searchProduct: PropTypes.func.isRequired 
};

const mapStateToProps = (state) => {
  return {
    products: state.productData.products,
  };
};

const mapDispatchToProps = {
  getAllProducts,
  searchProduct, 
};

export default connect(mapStateToProps, mapDispatchToProps)(ShopGridFullWidth);
