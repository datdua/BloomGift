import React, { useState } from "react";
import PropTypes from 'prop-types';

const ShopSearch = ({ handleSearch, isSearching }) => {
  const [searchTerm, setSearchTerm] = useState("");

  const onSubmit = (e) => {
    e.preventDefault();
    handleSearch("productName", searchTerm);
  };

  const onChange = (e) => {
    setSearchTerm(e.target.value);
  };

  return (
    <div className="sidebar-widget">
      <h4 className="pro-sidebar-title">Search</h4>
      <div className="pro-sidebar-search mb-50 mt-25">
        <form className="pro-sidebar-search-form" onSubmit={onSubmit}>
          <input
            type="text"
            name="productName"
            placeholder="Search by product name..."
            value={searchTerm}
            onChange={onChange}
          />
          <button type="submit" disabled={isSearching === true}>
            {isSearching === true ? "Searching..." : <i className="pe-7s-search" />}
          </button>
        </form>
      </div>
    </div>
  );
};

ShopSearch.propTypes = {
  handleSearch: PropTypes.func,
  isSearching: PropTypes.bool
};

ShopSearch.defaultProps = {
  handleSearch: () => { },
  isSearching: false
};

export default ShopSearch;