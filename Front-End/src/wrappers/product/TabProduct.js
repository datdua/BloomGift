import React, { useEffect, useState } from "react";
import PropTypes from "prop-types";
import Tab from "react-bootstrap/Tab";
import Nav from "react-bootstrap/Nav";
import SectionTitle from "../../components/section-title/SectionTitle";
import ProductGrid from "./ProductGrid";
import { useDispatch } from "react-redux";
import { getFeatureProduct, getNewProduct, getProductBestSeller } from "../../redux/actions/productActions.js";

const TabProduct = ({
  spaceTopClass,
  spaceBottomClass,
  bgColorClass,
  category,
  addToast // Assuming you pass `addToast` for notifications
}) => {
  const [newProducts, setNewProducts] = useState([]);
  const [bestSellerProducts, setBestSellerProducts] = useState([]);
  const [featureProducts, setFeatureProducts] = useState([]);
  const dispatch = useDispatch();

  // Fetch New Products
  useEffect(() => {
    const fetchNewProducts = async () => {
      try {
        const response = await dispatch(getNewProduct(addToast));
        setNewProducts(response);
      } catch (error) {
        console.error("Failed to fetch new products:", error);
      }
    };
    fetchNewProducts();
  }, [dispatch, addToast]);

  // Fetch Best Seller Products
  useEffect(() => {
    const fetchBestSellerProducts = async () => {
      try {
        const response = await dispatch(getProductBestSeller(addToast, 4)); // Fetching top products
        setBestSellerProducts(response);
      } catch (error) {
        console.error("Failed to fetch best seller products:", error);
      }
    };
    fetchBestSellerProducts();
  }, [dispatch, addToast]);

  // Fetch Featured Products
  useEffect(() => {
    const fetchFeatureItems = async () => {
      try {
        const response = await dispatch(getFeatureProduct(addToast));
        setFeatureProducts(response);
      } catch (error) {
        console.error("Failed to fetch featured products:", error);
      }
    };
    fetchFeatureItems();
  }, [dispatch, addToast]);

  return (
    <div
      className={`product-area ${spaceTopClass ? spaceTopClass : ""} ${spaceBottomClass ? spaceBottomClass : ""} ${bgColorClass ? bgColorClass : ""}`}
    >
      <div className="container">
        <SectionTitle titleText="ƯU ĐÃI HIỆN CÓ!" positionClass="text-center" />
        <Tab.Container defaultActiveKey="bestSeller">
          <Nav variant="pills" className="product-tab-list pt-30 pb-55 text-center">
            <Nav.Item>
              <Nav.Link eventKey="newArrival">
                <h4>Mặt hàng mới</h4>
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link eventKey="bestSeller">
                <h4>Bán chạy</h4>
              </Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link eventKey="featureItems">
                <h4>Đề xuất</h4>
              </Nav.Link>
            </Nav.Item>
          </Nav>
          <Tab.Content>
            <Tab.Pane eventKey="newArrival">
              <div className="row">
                <ProductGrid
                  category={category}
                  products={newProducts} 
                  limit={8}
                  spaceBottomClass="mb-25"
                />
              </div>
            </Tab.Pane>
            <Tab.Pane eventKey="bestSeller">
              <div className="row">
                <ProductGrid
                  category={category}
                  products={bestSellerProducts} 
                  limit={8}
                  spaceBottomClass="mb-25"
                />
              </div>
            </Tab.Pane>
            <Tab.Pane eventKey="featureItems">
              <div className="row">
                <ProductGrid
                  category={category}
                  products={featureProducts} // Pass featured products to ProductGrid
                  limit={8}
                  spaceBottomClass="mb-25"
                />
              </div>
            </Tab.Pane>
          </Tab.Content>
        </Tab.Container>
      </div>
    </div>
  );
};

TabProduct.propTypes = {
  bgColorClass: PropTypes.string,
  category: PropTypes.string,
  spaceBottomClass: PropTypes.string,
  spaceTopClass: PropTypes.string,
  addToast: PropTypes.func
};

export default TabProduct;
