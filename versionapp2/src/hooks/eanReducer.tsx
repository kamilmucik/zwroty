export const INITIAL_STATE = {
    eanProcessing: false,
    eanError: null,
    eanValid: false
}

export const eanReducer = (state = INITIAL_STATE, action) => {
    switch(action.type){
        case "EAN_PROCESSING_START":
                return { ...state, eanProcessing: true, eanValid: false }
        case "EAN_PROCESSING_SUCCESS":
            return {
                ...state,
                eanProcessing: false,
                eanValid: true
            }
        case "EAN_PROCESSING_FAIL":
            return {
                ...state,
                eanProcessing: false,
                eanValid: false
            }
        default:
            return state;
    }
}