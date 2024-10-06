import PropTypes from "prop-types";
import React, { useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { getCategory } from "../../redux/actions/categoryAction.js"
import { setActiveSort } from "../../helpers/product";

const ShopCategories = ({ getSortParams }) => {
  const dispatch = useDispatch();
  
  // Fetch categories from Redux store
  const categories = useSelector((state) => state.category ? state.category.categories : []);

  // Fetch categories when component mounts
  useEffect(() => {
    dispatch(getCategory());
  }, [dispatch]);

  return (
    <div className="sidebar-widget">
      <h4 className="pro-sidebar-title">Categories</h4>
      <div className="sidebar-widget-list mt-30">
        {categories && categories.length > 0 ? (
          <ul>
            <li>
              <div className="sidebar-widget-list-left">
                <button
                  onClick={(e) => {
                    getSortParams("category", "");
                    setActiveSort(e);
                  }}
                >
                  <span className="checkmark" /> All Categories
                </button>
              </div>
            </li>
            {categories.map((category) => (
              <li key={category.categoryID}>
                <div className="sidebar-widget-list-left">
                  <button
                    onClick={(e) => {
                      getSortParams("category", category.categoryID);
                      setActiveSort(e);
                    }}
                  >
                    <span className="checkmark" /> {category.categoryName}
                  </button>
                </div>
              </li>
            ))}
          </ul>
        ) : (
          "No categories found"
        )}
      </div>
    </div>
  );
};

ShopCategories.propTypes = {
  getSortParams: PropTypes.func.isRequired,
};

export default ShopCategories;
