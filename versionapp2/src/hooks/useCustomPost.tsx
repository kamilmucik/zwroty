import { useEffect, useReducer, useContext} from 'react';
import { ACTION_TYPE } from './postActionTypes';
import { INITIAL_STATE, postReducer } from './postReducer'
import { BASE_API_URL  } from '../config.tsx';
import AppContext from "../store/AppContext";

export const useCustomPost = (query: string, formData: any) => {
    const [state, dispach] = useReducer(postReducer, INITIAL_STATE);

    const appCtx = useContext(AppContext);

    useEffect(() => {
		if (!formData || formData === null) return;
        dispach({type: ACTION_TYPE.FETCH_START, loading: true, moreLoading: true });

        const postData = () => {
            fetch(BASE_API_URL+"/"+query, {
                method: 'POST',
                headers: {
                    Accept: 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData)
            }
            )
            .then( (response) => {
                return response.json();
            })
            .then( (data) => {
                // console.log("data: " + JSON.stringify(data));
                dispach({type: ACTION_TYPE.FETCH_SINGLE_SUCCESS, payload: data.dto });
            })
            .catch( (error) => {
                console.error(error);
                dispach({type: ACTION_TYPE.FETCH_ERROR, error: error});
            });
        }
        
        postData();
	}, [formData]);

  return state;
};