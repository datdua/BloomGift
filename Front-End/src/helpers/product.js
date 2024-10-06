import { useToasts } from "react-toast-notifications";
import { SEARCH_PRODUCT, searchProduct } from "../redux/actions/productActions";

// get products
export const getProducts = (products, category, type, limit) => {
  const finalProducts = category
    ? products.filter(
        product => product.category.filter(single => single === category)[0]
      )
    : products;

  if (type && type === "new") {
    const newProducts = finalProducts.filter(single => single.new);
    return newProducts.slice(0, limit ? limit : newProducts.length);
  }
  if (type && type === "bestSeller") {
    return finalProducts
      .sort((a, b) => {
        return b.saleCount - a.saleCount;
      })
      .slice(0, limit ? limit : finalProducts.length);
  }
  if (type && type === "saleItems") {
    const saleItems = finalProducts.filter(
      single => single.discount && single.discount > 0
    );
    return saleItems.slice(0, limit ? limit : saleItems.length);
  }
  return finalProducts.slice(0, limit ? limit : finalProducts.length);
};

// get product discount price
export const getDiscountPrice = (price, discount) => {
  return discount && discount > 0 ? price - price * (discount / 100) : null;
};

// get product cart quantity
export const getProductCartQuantity = (cartItems, product, color, size) => {
  // Convert cartItems object to an array
  const cartItemsArray = Object.values(cartItems);

  let productInCart = cartItemsArray.find(
    single =>
      single.productID === product.id &&
      (single.selectedProductColor
        ? single.selectedProductColor === color
        : true) &&
      (single.selectedProductSize ? single.selectedProductSize === size : true)
  );

  if (cartItemsArray.length >= 1 && productInCart) {
    if (product.variation) {
      return cartItemsArray.find(
        single =>
          single.productID === product.id &&
          single.selectedProductColor === color &&
          single.selectedProductSize === size
      )?.quantity || 0;
    } else {
      return cartItemsArray.find(single => product.id === single.productID)?.quantity || 0;
    }
  } else {
    return 0;
  }
};

//get products based on category
export const getSortedProducts = async (
  sortType,
  sortValue,
  page = 0,
  size = 10
) => {
  try {
    let params = {};

    if (sortType === "category") {
      params.categoryName = sortValue;
    }
    if (sortType === "tag") {
      params.descriptionProduct = sortValue;
    }
    if (sortType === "color") {
      params.colourProduct = sortValue;
    }
    if (sortType === "size") {
      params.sizeProduct = sortValue;
    }
    if (sortType === "filterSort") {
      if (sortValue === "priceHighToLow") {
        params.sort = "price,desc";
      }
      if (sortValue === "priceLowToHigh") {
        params.sort = "price,asc";
      }
    }

    // Make API call using searchProduct
    const response = await searchProduct(
      params.descriptionProduct,
      params.colourProduct,
      params.priceProduct,
      null, // productName not needed here unless specifically searching by name
      params.categoryName,
      null, // createDate can be added if needed
      null, // storeName can be added if needed
      params.sizeProduct,
      page,
      size
    );

    // Handle the response
    if (response && response.content && response.content.length > 0) {
      return response.content; // Return the array of products
    } else {
      console.warn("No products found");    
      return [];
    }
  } catch (error) {
    console.error("Error sorting products:", error);
    return [];
  }
};

// get individual element
const getIndividualItemArray = array => {
  let individualItemArray = array.filter(function(v, i, self) {
    return i === self.indexOf(v);
  });
  return individualItemArray;
};

// get individual categories
export const getIndividualCategories = products => {
  let productCategories = [];
  products &&
    products.map(product => {
      return (
        product.category &&
        product.category.map(single => {
          return productCategories.push(single);
        })
      );
    });
  const individualProductCategories = getIndividualItemArray(productCategories);
  return individualProductCategories;
};

// get individual tags
export const getIndividualTags = products => {
  let productTags = [];
  products &&
    products.map(product => {
      return (
        product.tag &&
        product.tag.map(single => {
          return productTags.push(single);
        })
      );
    });
  const individualProductTags = getIndividualItemArray(productTags);
  return individualProductTags;
};

// get individual colors
export const getIndividualColors = products => {
  let productColors = [];
  products &&
    products.map(product => {
      return (
        product.variation &&
        product.variation.map(single => {
          return productColors.push(single.color);
        })
      );
    });
  const individualProductColors = getIndividualItemArray(productColors);
  return individualProductColors;
};

// get individual sizes
export const getProductsIndividualSizes = products => {
  let productSizes = [];
  products &&
    products.map(product => {
      return (
        product.variation &&
        product.variation.map(single => {
          return single.size.map(single => {
            return productSizes.push(single.name);
          });
        })
      );
    });
  const individualProductSizes = getIndividualItemArray(productSizes);
  return individualProductSizes;
};

// get product individual sizes
export const getIndividualSizes = product => {
  let productSizes = [];
  product.variation &&
    product.variation.map(singleVariation => {
      return (
        singleVariation.size &&
        singleVariation.size.map(singleSize => {
          return productSizes.push(singleSize.name);
        })
      );
    });
  const individualSizes = getIndividualItemArray(productSizes);
  return individualSizes;
};

export const setActiveSort = e => {
  const filterButtons = document.querySelectorAll(
    ".sidebar-widget-list-left button, .sidebar-widget-tag button, .product-filter button"
  );
  filterButtons.forEach(item => {
    item.classList.remove("active");
  });
  e.currentTarget.classList.add("active");
};

export const setActiveLayout = e => {
  const gridSwitchBtn = document.querySelectorAll(".shop-tab button");
  gridSwitchBtn.forEach(item => {
    item.classList.remove("active");
  });
  e.currentTarget.classList.add("active");
};

export const toggleShopTopFilter = e => {
  const shopTopFilterWrapper = document.querySelector(
    "#product-filter-wrapper"
  );
  shopTopFilterWrapper.classList.toggle("active");
  if (shopTopFilterWrapper.style.height) {
    shopTopFilterWrapper.style.height = null;
  } else {
    shopTopFilterWrapper.style.height =
      shopTopFilterWrapper.scrollHeight + "px";
  }
  e.currentTarget.classList.toggle("active");
};
