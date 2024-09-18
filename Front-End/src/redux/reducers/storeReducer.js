import { produce } from 'immer';
import { SELLER_INFO } from "../actions/storeActions";

const initialState = {
    store: null,
    isAuthenticated: false,
    loading: false,
    error: null,
};

const storeReducer = (state = initialState, action) => {
    switch (action.type) {
        case SELLER_INFO:
            return produce(state, draft => {
                draft.store = action.payload;
                draft.isAuthenticated = true;
                draft.loading = false;
                draft.error = null;
            });
        default:
            return state;
    }
};

export default storeReducer;