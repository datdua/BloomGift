import axios from "axios";

export const SELLER_INFO = "SELLER_INFO";

export const fetchSellerInfo = (sellerId) => {
    return async (dispatch) => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                throw new Error("No token found");
            }
            const response = await axios.get('http://localhost:8080/api/seller/store/store-management/get-by-id', {
                params: { storeID: sellerId },
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.status !== 200) {
                throw new Error(`Lỗi khi nhận dữ liệu: ${response.status}`);
            }
            dispatch({
                type: SELLER_INFO,
                payload: response.data
            });
            return response.data;
        } catch (error) {
            console.error("Fetch seller info failed:", error);
            return Promise.reject(error);
        }
    }
}