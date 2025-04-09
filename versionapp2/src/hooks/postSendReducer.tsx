export const INITIAL_SEND_STATE = {
    loading: false,
    error: null
}

export const postReducer = (state = INITIAL_SEND_STATE, action) => {
    switch(action.type){
        case "SEND_START":
                return { ...state, loading: true }
        case "SEND_SUCCESS":
            return {
                ...state,
                error: '',
                loading: false
            }
        case "SEND_ERROR":
            return {
                ...state,
                error: action.error,
                loading: false,
            }
        default:
            return state;
    }
}