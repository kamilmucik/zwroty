import { useEffect, useReducer } from 'react';
import { ACTION_TYPE } from './eanActionTypes.tsx';
import { INITIAL_STATE, eanReducer } from './eanReducer'

export const useCustomEANValidator = (inputEan: string) => {
    const [state, dispach] = useReducer(eanReducer, INITIAL_STATE);
	
    const paddingZeros = (num, padlen, padchar) => {
        var pad_char = typeof padchar !== 'undefined' ? padchar : '0';
        var pad = new Array(1 + padlen).join(pad_char);
        return (pad + num).slice(-pad.length);
      }
  
    useEffect(() => {
      var inputEan13 = paddingZeros(inputEan,13);

      // console.log("inputEan13: " + inputEan13);

      if (inputEan13.length === 0 || !inputEan13.trim()) return;
          dispach({type: ACTION_TYPE.EAN_PROCESSING_START });

          const validateEAN = () => {
              let innerEAN = '';
              const antyReg13 = /^[0]{13}$/;

              const reg13 = /^[0-9]{13}$/;
              const reg12 = /^[0-9]{12}$/;
              const reg8 = /^[0-9]{8}$/;
              // const reg001 = /^[0-9]{8,13}$/;

              if (antyReg13.test(inputEan) === true ){
                return false;
              }

              if(reg13.test(inputEan) === false 
                  && reg12.test(inputEan) === false 
                  && reg8.test(inputEan) === false
                  ){
                return false;
              }
              // innerEAN = paddingZeros(inputEan,13);

              let originalCheck = inputEan13.substring(inputEan13.length - 1);
              let eanCode = inputEan13.substring(0, inputEan13.length - 1);

              // Add even numbers together
              let even = Number(eanCode.charAt(1)) +
              Number(eanCode.charAt(3)) +
              Number(eanCode.charAt(5)) +
              Number(eanCode.charAt(7)) +
              Number(eanCode.charAt(9)) +
              Number(eanCode.charAt(11));
              // Multiply this result by 3
              even *= 3;

              // Add odd numbers together
              let odd = Number(eanCode.charAt(0)) +
              Number(eanCode.charAt(2)) +
              Number(eanCode.charAt(4)) +
              Number(eanCode.charAt(6)) +
              Number(eanCode.charAt(8)) +
              Number(eanCode.charAt(10));

              // Add two totals together
              let total = even + odd;

              // Calculate the checksum
              // Divide total by 10 and store the remainder
              let checksum = total % 10;

              // If result is not 0 then take away 10
              if (checksum != 0) {
                checksum = 10 - checksum;
              }

              // Return the result
              if (Number(checksum) != Number(originalCheck)) {
                  dispach({type: ACTION_TYPE.EAN_PROCESSING_FAIL });
                  // console.log("inputEan13: false");
              }
              dispach({type: ACTION_TYPE.EAN_PROCESSING_SUCCESS });
              // console.log("inputEan13: true");
          }

          validateEAN();
    }, [inputEan]);

  return state;
};