import PropTypes from "prop-types";
import React, { Fragment, useState, useEffect, useCallback } from "react";
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
import { useToasts } from "react-toast-notifications";

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
  const [isSearching, setIsSearching] = useState(false);
  const pageLimit = 10;
  const { pathname } = location;


  // New state for search parameters
  const [searchParams, setSearchParams] = useState({
    descriptionProduct: "",
    colourProduct: "",
    priceProduct: "",
    productName: "",
    categoryName: "",
    createDate: "",
    storeName: "",
    sizeProduct: ""
  });

  const { addToast } = useToasts();

  const fetchProducts = useCallback(() => {
    setIsSearching(true);
    const apiCall = Object.values(searchParams).some(param => param !== "")
      ? searchProduct(
        addToast,
        searchParams.descriptionProduct,
        searchParams.colourProduct,
        searchParams.priceProduct,
        searchParams.productName,
        searchParams.categoryName,
        searchParams.createDate,
        searchParams.storeName,
        searchParams.sizeProduct,
        currentPage - 1,
        pageLimit
      )
      : getAllProducts(currentPage - 1, pageLimit);

    apiCall
      .then((response) => {
        setSortedProducts(response.content);
        setCurrentData(response.content);
        setIsSearching(false);
      })
      .catch((error) => {
        console.error("Failed to load products", error);
        setIsSearching(false);
      });
  }, [searchParams, currentPage, pageLimit, searchProduct, getAllProducts, addToast]);

  useEffect(() => {
    fetchProducts();
  }, [fetchProducts]);

  const handleSearch = useCallback((paramName, value) => {
    setSearchParams(prevParams => ({
      ...prevParams,
      [paramName]: value
    }));
    setCurrentPage(1);
  }, []);

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

  // Sort products based on user selection
  useEffect(() => {
    const sortedProductsList = getSortedProducts(products, sortType, sortValue);
    const filterSortedProductsList = getSortedProducts(
      sortedProductsList,
      filterSortType,
      filterSortValue
    );
  
    if (Array.isArray(filterSortedProductsList)) {
      setSortedProducts(filterSortedProductsList);
      setCurrentData(filterSortedProductsList.slice(offset, offset + pageLimit));
    } else {
      setSortedProducts([]); 
      setCurrentData([]); 
    }
  }, [offset, products, sortType, sortValue, filterSortType, filterSortValue]);
  

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
                  handleSearch={handleSearch}
                  isSearching={isSearching}
                  searchParams={searchParams}
                />
              </div>
              <div className="col-lg-9 order-1 order-lg-2">
                <ShopTopbar
                  getLayout={getLayout}
                  getFilterSortParams={getFilterSortParams}
                  productCount={products.length}
                  sortedProductCount={Array.isArray(currentData) ? currentData.length : 0} // Safely access length
                />

                {/* shop page content default */}
                <ShopProducts layout={layout} products={products} />

                {/* shop product pagination */}
                <div className="pro-pagination-style text-center mt-30">
                  {sortedProducts && Array.isArray(sortedProducts) && (
                    <Paginator
                      totalRecords={sortedProducts.length} // Safely access the length
                      pageLimit={pageLimit}
                      pageNeighbours={2}
                      setOffset={setOffset}
                      currentPage={currentPage}
                      setCurrentPage={setCurrentPage}
                      pageContainerClass="mb-0 mt-0"
                      pagePrevText="«"
                      pageNextText="»"
                    />
                  )}
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
