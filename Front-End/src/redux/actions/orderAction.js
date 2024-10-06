import axios from 'axios';
import { jwtDecode } from 'jwt-decode';


// Function to extract accountID from JWT
const getAccountIDFromToken = () => {
    const token = localStorage.getItem('token'); // Assume the token is stored in localStorage
    if (!token) return null;

    try {
        const decodedToken = jwtDecode(token);
        return decodedToken.accountID;
    } catch (error) {
        console.error('Error decoding token:', error);
        return null;
    }
};

export const createOrder = (formData, addToast) => {
    return async (dispatch) => {
        try {
            const accountID = getAccountIDFromToken();  // Assuming you're getting the account ID from the token
            const response = await axios.post(
                `https://bloomgift-bloomgift.azuremicroservices.io/api/customer/order/create-order?accountID=${accountID}`,
                formData,
                {
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                    }
                }
            );

            if (response.status === 200) {
                // Dispatch success action
                dispatch({
                    type: 'CREATE_ORDER_SUCCESS',
                    payload: response.data
                });
                // Show success toast
                addToast('Đơn hàng đã được tạo', { appearance: 'success', autoDismiss: true });
                return true;
            }
        } catch (error) {
            // Log the error and dispatch fail action
            console.error('Error creating order:', error);
            addToast('Lỗi khi tạo đơn hàng', { appearance: 'error', autoDismiss: true });
            dispatch({
                type: 'CREATE_ORDER_FAIL',
                payload: error.response ? error.response.data : error.message,
            });
            return false;
        }
    }
};
