import React, { useContext, useState, useRef, useEffect } from "react";
import { Text, View, TouchableOpacity, Image, TextInput, SafeAreaView, ActivityIndicator, FlatList } from 'react-native';
import { DataTable } from 'react-native-paper';
import { showMessage } from "react-native-flash-message";
import styles from './ScanSheetStyles';
import QRCodeScanner from 'react-native-qrcode-scanner';
import {useCustomFetch} from '../../hooks/useCustomFetch'
import CustomTable from '../../components/CustomTable';


type ItemProps = {id: string};
const Item = ({id}: ItemProps) => (
  <View >
    <Text >{id}</Text>
  </View>
);
//https://www.youtube.com/watch?v=VuNPrFH9H0E&t=1s
const ScanScreen = () => {
    const eanInputRef = useRef();
    const scanner = useRef(null);

    const [scan, setScan] = useState(false);
    const [isLoading, setLoading] = useState(false);
    const [isFocused, setIsFocused] = useState(false);
    const [eanValue, setEanValue] = useState('');
    const [eanScannerValue, setEanScannerValue] = useState('');
    const [ean2SendValue, setEan2SendValue] = useState('');

    const [query, setQuery] = useState('productimageversion/findbyean?ean=123');
    const {moreLoading, data} = useCustomFetch(query);

    const fetchProduct = async (ean) => {
      setQuery('productimageversion/findbyean?ean='+ean);
    }

    const paddingZeros = (num, padlen, padchar) => {
      var pad_char = typeof padchar !== 'undefined' ? padchar : '0';
      var pad = new Array(1 + padlen).join(pad_char);
      return (pad + num).slice(-pad.length);
    }
    const eanValidate = (inputEan) => {
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
      innerEAN = paddingZeros(inputEan,13);
  
      let originalCheck = innerEAN.substring(innerEAN.length - 1);
      let eanCode = innerEAN.substring(0, innerEAN.length - 1);
  
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
        return false;
      }
      return true;
    }

    useEffect(() => {
      let condition = eanValidate(ean2SendValue);
      if (condition === true ){
        fetchProduct(ean2SendValue);
        // sendDataToServer(paddingZeros(ean2SendValue,13));
      //   showMessage({
      //   message: "Szukam",
      //   description: "Szukam produktu: " + ean2SendValue,
      //   type: "info",
      // });
      }
    }, [ean2SendValue]);

  useEffect(() => {
      setEan2SendValue(eanScannerValue);
  }, [eanScannerValue]);

  useEffect(() => {
    setEan2SendValue(eanValue);
  }, [eanValue]);

  // useEffect(() => {
  //   // console.log("result.1: " + data[0]);
  //   console.log("useEffect.result.1: " + JSON.stringify(data));
  // }, [data]);




    useEffect(() => {
    if (isFocused === false){
        setDefaultFocus();
    }
    }, [isFocused]);

    const setDefaultFocus = () => {
        eanInputRef.current.focus();
    };

    const launchScannerCamera = () => {
      setScan(true);
    }
    const onSuccess = e => {
      setEanScannerValue('' + e.data);
      // setEanScannerExtendedValue('' + JSON.stringify(e));
      setScan(false);
    }

    return !scan ? (
    <>
        <View>
            <View style={styles.rowContainer} >
            <TextInput
              placeholder='EAN'
              autoFocus={true}
              keyboardType='numeric'
              editable={!isLoading}
              ref={eanInputRef}
              value={eanValue}
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              onChangeText={(event) => {
                setEanValue(event);
              }}
            />
            <TouchableOpacity onPress={launchScannerCamera} >
              <Image source={require('../../assets/img/barcode.png')}
                     style={{ width: 60, height: 38 }}
              />
            </TouchableOpacity>
          </View>
          <View>
          
          {moreLoading && <ActivityIndicator size='large'/>}

          <FlatList
            data={data}
            renderItem={({item}) => <Item id={item.ean} />}
            keyExtractor={item => item.id}
          />

          <DataTable  >
            <DataTable.Row >
              <DataTable.Cell >Numer artykułu</DataTable.Cell>
              <DataTable.Cell ></DataTable.Cell>
            </DataTable.Row>
            <DataTable.Row >
              <DataTable.Cell >EAN</DataTable.Cell>
              <DataTable.Cell >{ean2SendValue}</DataTable.Cell>
            </DataTable.Row>
            <DataTable.Row >
              <DataTable.Cell >Nazwa</DataTable.Cell>
              <DataTable.Cell >{query}</DataTable.Cell>
            </DataTable.Row>
          </DataTable>

          </View>
        </View>
    </>
  ):(
    <SafeAreaView style={{flex: 1}}>
      <View style={{flex: 1}}>
        <QRCodeScanner
          onRead={onSuccess}
          ref={scanner}
          reactivate={true}
          showMarker={true}
          bottomContent={
            <View style={{flexDirection: 'row'}}>
              <TouchableOpacity style={[styles.boxInline, styles.boxInlineBlue, {minWidth: '48%'}]} onPress={() => setScan(false)}>
                <Text style={styles.buttonText2}>Wróc</Text>
              </TouchableOpacity>
            </View>
          }
        />  
      </View>
    </SafeAreaView>
  );
};

export default ScanScreen;
