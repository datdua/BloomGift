import PropTypes from "prop-types";
import React, { useEffect } from "react";
import { Link } from "react-router-dom";
import { multilanguage } from "redux-multilanguage";

const NavMenu = ({ strings, menuWhiteClass, sidebarMenu, currentLanguageCode }) => {
  useEffect(() => {
    console.log('NavMenu rendered. Current language:', currentLanguageCode);
    console.log('Strings prop:', strings);
    console.log('Strings type:', typeof strings);
    if (strings) {
      console.log('Keys in strings:', Object.keys(strings));
    }
  }, [strings, currentLanguageCode]);

  const getString = (key) => {
    if (!strings || typeof strings !== 'object') {
      console.warn(`Strings is not an object for language ${currentLanguageCode}:`, strings);
      return key;
    }
    const value = strings[key];
    console.log(`Getting string for key "${key}" in language ${currentLanguageCode}:`, value);
    return value || key;
  };

  return (
    <div
      className={` ${
        sidebarMenu
          ? "sidebar-menu"
          : `main-menu ${menuWhiteClass ? menuWhiteClass : ""}`
      } `}
    >
      <nav>
        <ul>
          <li>
            <Link to={process.env.PUBLIC_URL + "/"}>
              {getString("home")}
            </Link>
          </li>
          <li>
            <Link to={process.env.PUBLIC_URL + "/cuahang"}>
              {getString("shop")}
            </Link>
          </li>
          <li>
            <Link to={process.env.PUBLIC_URL + "/combo"}>
              {getString("collection")}
            </Link>
          </li>
          <li>
            <Link to={process.env.PUBLIC_URL + "/blog-standard"}>
              {getString("blog")}
            </Link>
          </li>
          <li>
            <Link to={process.env.PUBLIC_URL + "/contact"}>
              {getString("contact_us")}
            </Link>
          </li>
        </ul>
      </nav>
    </div>
  );
};

NavMenu.propTypes = {
  menuWhiteClass: PropTypes.string,
  sidebarMenu: PropTypes.bool,
  strings: PropTypes.object,
  currentLanguageCode: PropTypes.string
};

export default multilanguage(NavMenu);