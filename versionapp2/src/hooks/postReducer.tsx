export const INITIAL_STATE = {
    loading: false,
    moreLoading: false,
    error: null,
    moreError: null,
    data: [],
    singleResult: null,
    deleteResult: null,
    posUpResult: null,
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
                data: [ ...action.payload],
                error: '',
                loading: false,
                moreLoading: false
            }
        case "FETCH_SINGLE_SUCCESS":
            return {
                ...state,
                singleResult: action.payload,
                error: '',
                loading: false,
                moreLoading: false
            }
        case "FETCH_DELETE_SUCCESS":
            return {
                ...state,
                deleteResult: action.payload,
                error: '',
                loading: false,
                moreLoading: false
            }
        case "FETCH_POSUP_SUCCESS":
            return {
                ...state,
                posUpResult: action.payload,
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