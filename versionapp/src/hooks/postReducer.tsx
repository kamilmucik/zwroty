export const INITIAL_STATE = {
    loading: false,
    moreLoading: false,
    error: null,
    moreError: null,
    data: [],
    totalPage: 1,
    currentPage: 1,
    isListEnd: false
}

export const postReducer = (state = INITIAL_STATE, action) => {
    switch(action.type){
        case "FETCH_START":
                return { ...state, loading: true, moreLoading: true }
        case "FETCH_SUCCESS":
            return {
                ...state,
                data: [...state.data, ...action.payload],
                totalPage: action.totalPage,
                error: '',
                loading: false,
                moreLoading: false
            }
        case "FETCH_ERROR":
            return {
                ...state,
                error: action.error,
                loading: false,
                moreLoading: false
            }
        default:
            return state;

    }
}