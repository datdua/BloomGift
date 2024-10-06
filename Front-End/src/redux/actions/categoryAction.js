import axios from 'axios';

export const GET_ALL_CATEGORIES = "GET_ALL_CATEGORIES"

export const getCategory = () => {
    return async (dispatch) => {
      try {
        const response = await axios.get(
          'https://bloomgift-bloomgift.azuremicroservices.io/api/customer/category/list-category'
        );
        if (response.status !== 200) {
          throw new Error(`Error fetching data: ${response.status}`);
        }
        const categories = response.data;
        dispatch({
          type: 'GET_ALL_CATEGORIES',
          payload: categories,
        });
      } catch (error) {
        console.error('Fetch all categories failed:', error);
      }
    };
  };