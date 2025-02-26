import { useEffect, useReducer } from 'react';
import { ACTION_TYPE } from './postActionTypes';
import { INITIAL_STATE, postReducer } from './postReducer'
import { BASE_API_URL, API_KEY } from '../config.tsx';
// import PackageJson from '../../package.json';
// import AppContext from "../store/AppContext";

export const useCustomFetch = (query: string, cached = true, stataticData = []) => {

  // console.log("useCustomFetch.1: " + `${BASE_API_URL}/${query}`);
  const [state, dispach] = useReducer(postReducer, INITIAL_STATE);
	useEffect(() => {
		if (!query || !query.trim()) return;
    dispach({type: ACTION_TYPE.FETCH_START, loading: true, moreLoading: true });

    const fetchData = () => {
        fetch(`${BASE_API_URL}/${query}`, {
            method: 'GET'
          })
          .then( (response) => {
            return response.json();
          })
          .then( (data) => {
            dispach({type: ACTION_TYPE.FETCH_SUCCESS, payload: [...data.results] });
          })
          .catch( (error) => {
            dispach({type: ACTION_TYPE.FETCH_ERROR, error: error});
          });
    }
    

		fetchData();
	}, [query]);

  return state;
};