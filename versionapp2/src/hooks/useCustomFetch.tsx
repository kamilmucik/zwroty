import { useEffect, useReducer, useContext } from 'react';
import { ACTION_TYPE } from './postActionTypes';
import { INITIAL_STATE, postReducer } from './postReducer'
import { BASE_API_URL} from '../config.tsx';
import AppContext from "../store/AppContext";

export const useCustomFetch = (query: string, cached = true, stataticData = []) => {
  const [state, dispach] = useReducer(postReducer, INITIAL_STATE);

  const appCtx = useContext(AppContext);

	useEffect(() => {
		if (!query || !query.trim()) return;
    dispach({type: ACTION_TYPE.FETCH_START, loading: true, moreLoading: true });

    const fetchData = () => {
        fetch(BASE_API_URL+"/"+query, {
            method: 'GET'
          })
          .then( (response) => {
            return response.json();
          })
          .then( (data) => {

            // console.log("useCustomFetch: " + JSON.stringify(data));
            dispach({type: ACTION_TYPE.FETCH_SINGLE_SUCCESS, payload: data.results[0] });
          })
          .catch( (error) => {
            console.error(error);
            dispach({type: ACTION_TYPE.FETCH_ERROR, error: error});
          });
    }
    
		fetchData();
	}, [query]);

  return state;
};