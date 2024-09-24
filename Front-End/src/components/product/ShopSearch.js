import React, { useState } from "react";

const ShopSearch = ({ handleSearch }) => {
  const [searchTerm, setSearchTerm] = useState(""); 

  // Handle form submission
  const onSubmit = (e) => {
    e.preventDefault();
    
    if (searchTerm.trim() !== "") {
      handleSearch(searchTerm);
    }
  };

  return (
    <div className="sidebar-widget">
      <h4 className="pro-sidebar-title">Search</h4>
      <div className="pro-sidebar-search mb-50 mt-25">
        <form className="pro-sidebar-search-form" onSubmit={onSubmit}>
          <input
            type="text"
            placeholder="Search here..."
            value={searchTerm} 
            onChange={(e) => setSearchTerm(e.target.value)} 
          />
          <button type="submit">
            <i className="pe-7s-search" />
          </button>
        </form>
      </div>
    </div>
  );
};

export default ShopSearch;
