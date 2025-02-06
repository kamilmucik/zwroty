
import { renderHook} from '@testing-library/react-hooks'
// import {  waitFor } from "@testing-library/react";
import { useCustomFetch } from '../../../src/hooks/useCustomFetch'

//https://react-hooks-testing-library.com/usage/advanced-hooks
//https://sugandsingh5566.medium.com/leveling-up-your-react-native-test-automation-harnessing-the-power-of-jest-3a14b5298f8b
//https://www.leighhalliday.com/mock-fetch-jest
//https://vaskort.medium.com/how-to-unit-test-your-custom-react-hook-with-react-testing-library-and-jest-8bdefafdc8a2
//https://github.com/vaskort/custom-hook-test/blob/develop/src/hooks/useFetchedData.js
describe("useCustomFetch", () => {
    let query = 'department/1/1/10/';

    let mockedData = {data: [{id: 6, "name": "PPL(A) skrócony"}, {"id": 1, "name": "PPL(A)"}, {"id": 5, "name": "SPL"}], "status": 200, "totalPage": 1};

    beforeEach(() => {
        fetch.resetMocks();
    //   mockedData = {data: [{id: 6, "name": "PPL(A) skrócony"}, {"id": 1, "name": "PPL(A)"}, {"id": 5, "name": "SPL"}], "status": 200, "totalPage": 1};
    // let mockedData = {
    //     loading: false, 
    //     moreLoading: false, 
    //     totalPage: 2,
    //     currentPage: 2,
    //     payload: [
    //       {"id": 6, "name": "PPL(A) skrócony"}, 
    //       {"id": 1, "name": "PPL(A)"}, 
    //       {"id": 5, "name": "SPL"}
    //     ]
    //   };
  
    //   global.fetch.mockResolvedValue({
    //     json: jest.fn().mockResolvedValue(mockedData),
    //   });
    });

    // afterEach(() => {
    //     jest.restoreAllMocks();
    // });

    it('should return default value of hook', async () => {
        const { result, waitForNextUpdate } = renderHook(() => useCustomFetch(query))
        const { loading, moreLoading, error, moreError, data, totalPage, currentPage, isListEnd } = result.current;
        
        // console.log("result.1: " + JSON.stringify(result.current));
        // result.current.incrementAsync()
      
        // await waitForNextUpdate()

        // console.log("result.2: " + JSON.stringify(result.current));
      
        // expect(result.current.totalPage).toBe(1)
        // expect(data).toBe(null);
        expect(error).toBe(null);
        expect(moreError).toBe(null);
        expect(loading).toBe(false);
        // expect(moreLoading).toBe(false);
        expect(totalPage).toBe(1);
        expect(currentPage).toBe(1);
        expect(isListEnd).toBe(false);
      })

      it("should return data", async () => {
        fetch.mockResponseOnce(JSON.stringify(mockedData));
        // Mock console.log
        // console.log = jest.fn();

        // Call the function
        await useCustomFetch(query);
        const { result, waitForNextUpdate } = renderHook(() => useCustomFetch(query))
        const { loading, moreLoading, error, moreError, data, totalPage, currentPage, isListEnd } = result.current;

        console.log("result.2: " + JSON.stringify(result.current));

      });

    //   it("returns null when exception", async () => {
    //     fetch.mockReject(() => Promise.reject("API is down"));
      
    //     const rate = await useCustomFetch("USD", "CAD");
    //     await useCustomFetch(query);
      
    //     expect(rate).toEqual(null);
    //     expect(fetch).toHaveBeenCalledWith(
    //       "https://api.exchangeratesapi.io/latest?base=USD"
    //     );
    //   });

});