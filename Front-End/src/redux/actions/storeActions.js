import axios from "axios";

export const SELLER_INFO = "SELLER_INFO";
export const UPDATE_SELLER_INFO = "UPDATE_SELLER_INFO";
export const GET_ALL_PRODUCTS = "GET_ALL_PRODUCTS";

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
            const storeData = response.data;
            dispatch({
                type: SELLER_INFO,
                payload: {
                    storeID: storeData.storeID,
                    storeName: storeData.storeName,
                    type: storeData.type.trim(),
                    storePhone: storeData.storePhone,
                    storeAddress: storeData.storeAddress,
                    email: storeData.email,
                    bankAccountName: storeData.bankAccountName,
                    bankNumber: storeData.bankNumber,
                    bankAddress: storeData.bankAddress,
                    taxNumber: storeData.taxNumber,
                    storeStatus: storeData.storeStatus,
                    storeAvatar: storeData.storeAvatar,
                    identityCard: storeData.identityCard.trim(),
                    identityName: storeData.identityName,
                    roleName: storeData.roleName
                }
            });
            return storeData;
        } catch (error) {
            console.error("Fetch seller info failed:", error);
            return Promise.reject(error);
        }
    }
}

export const updateSellerInfo = (sellerId, sellerInfo) => {
    return async (dispatch) => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                throw new Error("No token found");
            }
            const response = await axios.put(`http://localhost:8080/api/seller/store/store-management/update/${sellerId}`, sellerInfo, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                }
            });
            if (response.status !== 200) {
                throw new Error(`Lỗi khi cập nhật dữ liệu: ${response.status}`);
            }
            const updatedStoreData = response.data;
            dispatch({
                type: UPDATE_SELLER_INFO,
                payload: updatedStoreData
            });
            return updatedStoreData;
        } catch (error) {
            console.error("Update seller info failed:", error);
            return Promise.reject(error);
        }
    }
}

