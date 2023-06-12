import {
  View,
  Text,
  ScrollView,
  TextInput,
  SafeAreaView,
  TouchableOpacity, Image,
  StyleSheet, ActivityIndicator
} from "react-native";
import React, { useState, useRef, useContext, useEffect } from "react";
import SelectDropdown from 'react-native-select-dropdown';
import { DataTable } from 'react-native-paper';
import Toggle from '../components/common/Toggle.component';
import NumericPadModal from '../components/common/NumericPadModal.component';
import imagePrinter from '../assets/print_icon.png';
import imageStorage from '../assets/storage_icon.png';
import AppContext from '../store/AppContext';
import GlobalStyle from "../utils/GlobalStyle";
import { InfoToast } from "../components/common/InfoToast.component";
import QRCodeScanner from 'react-native-qrcode-scanner';

const requestCameraPermission = async () => {
  try {
    const granted = await PermissionsAndroid.request(
      PermissionsAndroid.PERMISSIONS.CAMERA,
      {
        title: "App Camera Permission",
        message:"App needs access to your camera ",
        buttonNeutral: "Ask Me Later",
        buttonNegative: "Cancel",
        buttonPositive: "OK"
      }
    );
    if (granted === PermissionsAndroid.RESULTS.GRANTED) {
      console.log("Camera permission given");
    } else {
      console.log("Camera permission denied");
    }
  } catch (err) {
    console.warn(err);
  }
};
const DetailScreen = ({navigation, route}) => {
  const { retNumber } = route.params;
  const [debugInfo, setDebugInfo] = useState('');
  const [isVisible, setVisible] = useState('');
  const [amount, setAmount] = useState('0');
  const numpadRef = useRef(null);
  const eanInputRef = useRef();
  const [artNumber, setArtNumber] = useState(0);
  const [eanValue, setEanValue] = useState('');
  const [ean2SendValue, setEan2SendValue] = useState('');
  const [isLoading, setLoading] = useState(false);
  const [shipmentProduct, setShipmentProduct] = useState([]);

  const [scan, setScan] = useState(false);
  const scanner = useRef(null);
  const [eanScannerValue, setEanScannerValue] = useState('');

  const appCtx = useContext(AppContext);
  const scanPalletCounterValue = appCtx.scanPalletCounterValue;
  const scanMultiperValue = appCtx.scanMultiperValue;

  const scanCMValue = appCtx.scanCMValue;
  const scanPrintValue = appCtx.scanPrintValue;
  const scanStorageValue = appCtx.scanStorageValue;
  const settingsPortValue = appCtx.settingsPortValue;

  const [selectedOption, setSelectedOption] = useState(1);
  const options = [
    {title: 'Dobre', code: '0'},
    {title: 'Uszkodzone', code: '1'},
    {title: 'Z ceną', code: '2'},
    {title: 'Utylizacja', code: '3'},
  ];

  const [isFocused, setIsFocused] = useState(false);
  useEffect(() => {
    if (isFocused === false){
      setDefaultFocus();
    }
  }, [isFocused]);

  const eanValidate = (inputEan) => {
    // console.log("eanValidate");
    //4009900382250
    let innerEAN = '';

    const reg13 = /^[0-9]{13}$/;
    const reg8 = /^[0-9]{8}$/;

    if(reg13.test(inputEan) === false && reg8.test(inputEan) === false){
      return false;
    }

    if(reg8.test(inputEan) === false){
      innerEAN = inputEan;
    } else {
      innerEAN = '00000' + inputEan;
    }

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

    // console.log("checksum: " + checksum + ' = ' +  originalCheck);
    // console.log("even: " + even );
    // console.log("innerEAN: " + innerEAN );
    // Return the result
    if (Number(checksum) != Number(originalCheck)) {
      return false;
    }
    return true;
  }

  useEffect(() => {
    if (eanValidate(eanValue) === false) {
      setEan2SendValue(eanValue);
    }

    // console.log("eanValue.zmiana: " + eanValue + " : " + eanValidate(eanValue));
  }, [eanValue]);

  useEffect(() => {
    // let eanLength = eanScannerValue.length;
    // if (eanValidate(eanValue)){
    //   sendDataToServer(eanScannerValue);
    //  // getShipmentProductInfo(eanValue);
    // }
    if (eanValidate(eanScannerValue) === false) {
      setEan2SendValue(eanScannerValue);
    }

    // console.log("eanScannerValue.zmiana: " + eanScannerValue + " : " + eanValidate(eanScannerValue));
  }, [eanScannerValue]);

  useEffect(() => {
    if (eanValidate(ean2SendValue)){
      sendDataToServer(ean2SendValue);
    }

    // console.log("ean2SendValue.zmiana: " + ean2SendValue + " : " + eanValidate(ean2SendValue));
  }, [ean2SendValue]);


  useEffect(() => {
    // console.log("zmiana");
    setDefaultFocus();
  }, [appCtx.scanCMValue,appCtx.scanPrintValue,appCtx.scanStorageValue,appCtx.scanMultiperValue,appCtx.scanPalletCounterValue]);

  const setDefaultFocus = () => {
    eanInputRef.current.focus();
  };
  const onModalPress = (place, amount) => {
    if (place === 'x'){
      appCtx.setScanMultiperValue(amount);
    }else {
      appCtx.setScanPalletCounterValue(amount);
    }
    setDefaultFocus();
  };
  const onModalTest = () => {
    console.log("onModalTest");
  }

  const onPressCMHandler = (val) => {
    appCtx.setScanCMValue(val);
    setDefaultFocus();
  };
  const onPressStorageHandler = (val) => {
    // console.log("onPressStorageHandler: " + val);
    appCtx.setScanStorageValue(val);
    // setDefaultFocus();
  };
  const onPressPrintHandler = (val) => {
    appCtx.setScanPrintValue(val);
    // setDefaultFocus();
  };

  const checkCMHandler = () => {
    appCtx.setScanCMValue(1);
    setDefaultFocus();
  };
  const uncheckCMHandler = () => {
    appCtx.setScanCMValue(0);
    setDefaultFocus();
  };

  const checkPrintHandler = () => {
    appCtx.setScanPrintValue(1);
    setDefaultFocus();
  };
  const uncheckPrintHandler = () => {
    appCtx.setScanPrintValue(0);
    setDefaultFocus();
  };

  displayModal = (show) => {
    setVisible(show);
    setDefaultFocus();
  };

  sendDataToServer = (ean) => {
    setShipmentProduct([]);
    setLoading(true);
    if ( scanPrintValue === 1){
      printLabel();
    } else if ( Number(appCtx.scanStorageValue) === 1) {
      if (Number(appCtx.scanMultiperValue) > 0){
        updateProduct(ean);
      } else {
        appCtx.setToastInfoValue('Wybierz ilośc ('+appCtx.scanMultiperValue+') produktów.', 'info');
      }
    }
    getShipmentProductInfo(ean);
    appCtx.setScanMultiperValue(0);
    appCtx.setScanPalletCounterValue(0);
    setEanValue('');
    setEanScannerValue('');
    setSelectedOption(0);
    setDefaultFocus();
  }

  const printLabel = async () => {
    setLoading(true);
    const requestOptions = {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(
        {
          "artNumber":artNumber,
          "returnNumber":""+retNumber+"",
          "palletOption":""+appCtx.scanCMValue+"",
          "counter": appCtx.scanMultiperValue,
          "palletCounter":appCtx.scanPalletCounterValue ,
          "author": appCtx.settingsOperatorValue,
          "returnCode":200
        }
      )
    };

    try {
      await fetch(''+appCtx.settingsURLValue+':'+appCtx.settingsPortValue +'/print/label', requestOptions)
        .catch((error) => {
          appCtx.setToastInfoValue('Nie można pobrac danych! Możliwy problem z siecią internet.', 'error');
        })
        .finally(() => setLoading(false));
    } catch (error) {
      // console.error(error);
    }
    appCtx.setScanMultiperValue(0);
    appCtx.setScanPalletCounterValue(0);
    // scanPrintValue
    // appCtx.setScanStorageValue(val);
    setDefaultFocus();
  }


  let scanCorrect = 0;
  let scanError = 0;
  let scanLabel = 0;
  let scanUtilization = 0;
  function setScanCorrect(val) {scanCorrect = val;}
  function setScanError(val) {scanError = val;}
  function setScanLabel(val) {scanLabel = val;}
  function setScanUtilization(val) {scanUtilization = val;}

  const updateProduct =  async (ean) => {
    const requestOptions = {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' }
    };

    try {

      if (selectedOption == 0) {
        setScanCorrect(appCtx.scanMultiperValue);
      } else if (selectedOption == 1) {
        setScanError(appCtx.scanMultiperValue);
      } else if (selectedOption == 2) {
        setScanLabel(appCtx.scanMultiperValue);
      } else if (selectedOption == 3) {
        setScanUtilization(appCtx.scanMultiperValue);
      }

      await fetch(''+appCtx.settingsURLValue+':'+appCtx.settingsPortValue +'/shipment_product/updateproduct?' +
        'retNumber='+retNumber+'&' +
        'ean='+ean+'&' +
        'scanCorrect='+scanCorrect+'&' +
        'scanError='+scanError+'&' +
        'scanLabel='+scanLabel+'&' +
        'scanUtilization='+scanUtilization, requestOptions)
        .then((response) => response.json())
        .finally(() =>{
          setLoading(false);
          }
        );
        getShipmentProductInfo(ean);
    } catch (error) {
      console.error(error);
      appCtx.setToastInfoValue('Nie można pobrac danych! Możliwy problem z siecią internet.', 'error');
    }
  }

  getShipmentProductInfo = (ean) => {
    setShipmentProduct([]);
    // console.log(''+appCtx.settingsURLValue+':'+appCtx.settingsPortValue +'/shipment_product/findbyean?retNumber=' + retNumber + '&ean=' + ean);
    fetch(''+appCtx.settingsURLValue+':'+appCtx.settingsPortValue +'/shipment_product/findbyean?retNumber=' + retNumber + '&ean=' + ean)
      .then((response) => response.json())
      .then(responseData => { return responseData;})
      .then((data) => {
        if (data.status === 500){
          appCtx.setToastInfoValue('Brak produku w bazie.', 'info');
        } else {
          setShipmentProduct(data);
          setArtNumber(data.artNumber);
        }

      })
      .catch((error) => {
        // console.error(error);
        appCtx.setToastInfoValue('Nie można pobrac danych! Możliwy problem z siecią internet.', 'error');
      })
      .finally(() => setLoading(false));
    setDefaultFocus();
  }

  async function loadProperties() {
    try {
      appCtx.setScanPalletCounterValue(0);
      appCtx.setScanMultiperValue(0);
      appCtx.setScanStorageValue(0);

      appCtx.setScanCMValue(0);
      // setScanCMValue(0);
      appCtx.setScanPrintValue(0);
      setSelectedOption(0);
    } catch(e) {
      console.error(e);
    }
    setDefaultFocus();
  }

  const launchScannerCamera = () => {
    setScan(true);
  }
  const onSuccess = e => {
    setEanScannerValue('' + e.data);
    setScan(false);
  }

  useEffect(() => {

    loadProperties();
  }, []);

  return !scan ? (
    <SafeAreaView style={styles.container}>
    <InfoToast></InfoToast>
      {isLoading ? <ActivityIndicator /> : <Text/>}
        <View style={{ flex: 1}}>


          {appCtx.isDebugMode === 'true' ?
            <View>
              <Text style={{fontSize:6, }}>isMobile: {appCtx.isMobile}</Text>
              <Text style={{fontSize:6, }}>eanScannerValue: {eanScannerValue}</Text>
              <Text style={{fontSize:6, }}>{isFocused ? 'focused' : 'unfocused'}</Text>
              <Text style={{fontSize:6, }}>scanStorageValue: {appCtx.scanStorageValue}</Text>
              <Text style={{fontSize:6, }}>scanPrintValue: {appCtx.scanPrintValue}</Text>
              <Text style={{fontSize:6, }}>scanCMValue: {appCtx.scanCMValue}</Text>
              <Text style={{fontSize:6, }}>scanMultiperValue: {appCtx.scanMultiperValue}</Text>
              <Text style={{fontSize:6, }}>scanPalletCounterValue: {appCtx.scanPalletCounterValue}</Text>
            </View>

            : <View/>}

          <View style={[{flexDirection:'row', paddingLeft:10,zIndex:3}]}>
           <SelectDropdown
              buttonStyle={[GlobalStyle.AppSelectButton]}
              buttonTextStyle={[GlobalStyle.AppSelectButtonText]}
              data={options}

              defaultValueByIndex={selectedOption}
              onFocus={() => setIsFocused(false)}
              onBlur={() => setIsFocused(false)}
              onSelect={(selectedItem, index) => {
                setSelectedOption(selectedItem.code);
                eanInputRef.current.focus();
              }}
              buttonTextAfterSelection={(selectedItem, index) => {
                return selectedItem;
              }}
              rowTextForSelection={(item, index) => {
                return item;
              }}
              renderCustomizedButtonChild={(selectedItem, index) => {
                return (
                  <View >
                    <Text >{selectedItem ? selectedItem.title : 'Wybierz'}</Text>
                  </View>
                );
              }}
              renderCustomizedRowChild={(item, index) => {
                return (
                  <View >
                    <Text >{item.title}</Text>
                  </View>
                );
              }}
            />

            <NumericPadModal
              id='NumericPadModal1'
              title='x'
              titleHeader='Mnożnik'
              initVal='1'
              currentVal={scanMultiperValue}
              innerMinWidth='big'
              onPress={onModalPress}
              onUncheck={onModalTest}
              returnValue={debugInfo}
            />
          </View>

          <View style={[GlobalStyle.AppInputSection]}>
            <TextInput
              style={[GlobalStyle.AppInput]}
              placeholder='EAN'
              autoFocus={true}
              keyboardType='numeric'
              ref={eanInputRef}
              value={eanValue}
              onFocus={() => setIsFocused(true)}
              onBlur={() => setIsFocused(false)}
              // onChange={(event) => sendDataToServer( event.nativeEvent.text)}
              // onKeyPress={(event) => sendDataToServer( event.nativeEvent.text)}
              // onSubmitEditing={(event) => sendDataToServer( event.nativeEvent.text)}
              onChangeText={(event) => {
                setEanValue(event);
                // sendDataToServer(event.nativeEvent.text);
              }}
            />
            {appCtx.isMobile === 'true' ? <TouchableOpacity onPress={launchScannerCamera} style={styles.button2}>
              <Image source={require('../assets/barcode.png')}
                     style={{ width: 60, height: 38 }}
              />
            </TouchableOpacity> : <View/>}

          </View>

          <ScrollView style={styles.scrollView} >
            <View style={styles.container} >
              <DataTable style={styles.dataTable} >
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>Nazwa</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>
                    <View style={{ minHeight: 20, maxWidth:180,
                      alignItems: 'center',
                      color: 'black',
                      justifyContent: 'flex-start',
                      flexDirection: "column",
                      flexWrap: "wrap-reverse",
                    }}>
                      <Text style={{
                        color: 'black',
                      }}>
                        {shipmentProduct.name}
                      </Text>
                    </View>
                  </DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>Kod</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>
                    <View style={{ minHeight: 20, maxWidth:180,
                      alignItems: 'center',
                      color: 'black',
                      justifyContent: 'flex-start',
                      flexDirection: "column",
                      flexWrap: "wrap-reverse",
                    }}>
                      <Text style={{
                        color: 'black',
                      }}>
                        {shipmentProduct.ean}
                      </Text>
                    </View>
                  </DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow} >
                  <DataTable.Cell style={styles.dataTableCellLeft}>Wszystkie</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>
                    <Text >{shipmentProduct.counter}({shipmentProduct.scanCorrect != null ? shipmentProduct.scanCorrect + shipmentProduct.scanError : ''} ) </Text></DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>Uszkodzone</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{shipmentProduct.scanError}</DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>Dobrych</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{shipmentProduct.scanCorrect}</DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>Z ceną</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{shipmentProduct.scanLabel}</DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>Utylizacja</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{shipmentProduct.scanUtilization}</DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>Artykuł</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{shipmentProduct.artNumber}</DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>Log</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>
                    <View style={{ minHeight: 20, maxWidth:180,
                      alignItems: 'center',
                      color: 'black',
                      justifyContent: 'flex-start',
                      flexDirection: "column",
                      flexWrap: "wrap-reverse",
                    }}>
                      <Text style={{
                        color: 'black',
                      }}>
                        {shipmentProduct.scanLog}
                      </Text>
                    </View>
                </DataTable.Cell>
                </DataTable.Row>
              </DataTable>
            </View>
          </ScrollView>

          <View style={styles.wrapInline}>
            <NumericPadModal
              id='NumericPadModal2'
              title='Pal'
              titleHeader='Palety'
              initVal='1'
              currentVal={scanPalletCounterValue}
              innerMinWidth='small'
              onPress={onModalPress}
              returnValue={debugInfo}
              />
            <Toggle
              isDisabled={false}
              initVal={appCtx.scanPrintValue === '1' ? 'true' : 'false'}
              val={scanPrintValue === 1 ? 'true' : 'false'}
              onPress={onPressPrintHandler}
              imgScr={imagePrinter}
            />
            <Toggle
              initVal={appCtx.scanCMValue === '1' ? 'true' : 'false'}
              val={scanCMValue === 1 ? 'true' : 'false'}
              isDisabled={appCtx.scanPrintValue ? 'true' : 'false'}
              onPress={onPressCMHandler}
              valueCheck={<Text>C</Text>}
              valueUnCheck={<Text>M</Text>}
            />
            <Toggle
              initVal={appCtx.scanStorageValue === '1' ? 'true' : 'false'}
              val={scanStorageValue === 1 ? 'true' : 'false'}
              isDisabled={appCtx.scanPrintValue ? 'true' : 'false'}
              onPress={onPressStorageHandler}
              imgScr={imageStorage}
            />
          </View>
        </View>
    </SafeAreaView>
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
  )
    ;
};


const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  scrollView: {
    flex: 1,
    marginHorizontal: 8,
  },
  sectionStyle: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderWidth: 0.5,
    borderColor: '#000',
    height: 40,
    borderRadius: 5,
    margin: 10,
  },
  input: {
    color: 'black',
    flex: 5,
    fontSize: 14,
    textAlign: 'center',
    borderColor: 'black',
  },
  buttonStyle: {
    color: 'white',
    fontSize: 17,
    textAlign: 'center',
  },

  button: {
    display: 'flex',
    height: 40,
    borderRadius: 6,
    justifyContent: 'center',
    alignItems: 'center',
    width: '50%',
    backgroundColor: '#2AC062',
  },

  modal: {
    justifyContent: 'center',
    alignItems: 'center',
    height: 300 ,
    width: '90%',
    marginTop: 80,

  },
  shadowBox: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  amountTxt: {
    textAlign: 'center',
    fontSize: 38,
    fontWeight: '700',
    lineHeight: 40,
    borderWidth: 1,
    width: '70%',
    alignItems: 'center',
    justifyContent: 'center',
  },
  amountButton: {
    textAlign: 'center',
    borderWidth: 1,
    width: '30%',
  },
  amountButtonText: {
    textAlign: 'center',
    height: 40,
    borderRadius: 6,
    justifyContent: 'center',
    alignItems: 'center',
    width: '50%',
    backgroundColor: '#2AC062',
  },
  keyboardContainer: {
    borderRadius: 26,
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 16,
    bottom: 0,
  },
  bottomModal: {
    justifyContent: 'flex-end',
    margin: 0,
  },
  dataTable: {
  },
  dataTableRow: {
    margin: 0,
    minHeight: 20,
  },
  dataTableCell: {
    margin: 0,
    bottom: 0,
  },
  dataTableCellLeft: {
    margin: 0,
    bottom: 0,
    maxWidth: 80,
    textAlignVertical: 'top',
    textAlign: 'left',
  },
  boxInline: {
    borderTopWidth: 1,
    borderBottomWidth: 1,
    fontSize: 16,
    flexDirection: 'row',
    minHeight: 56,
    minWidth: 58,
    justifyContent: 'center',
    alignItems: 'center',
    marginHorizontal: 8,
  },
  boxInlineBlue: {
    backgroundColor: '#E4F0F5',
    borderTopColor: '#3F87A6',
    borderBottomColor: '#3F87A6',
  },
  boxInlineRed: {
    backgroundColor: '#FFE7E8',
    borderTopColor: '#E66465',
    borderBottomColor: '#E66465',
  },
  wrapInline: {
    // borderWidth: 1,
    alignItems: 'center',
    minHeight: 56,
    flexDirection: 'row',
    justifyContent: 'center',
  },
});

export default DetailScreen;
