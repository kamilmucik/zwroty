import {
  View,
  Text,
  ScrollView,
  TextInput,
  SafeAreaView,
  StyleSheet,
} from "react-native";
import React, { useState, useRef, useContext, useEffect } from "react";
import  NumericPad  from  'react-native-numeric-pad';
import SelectDropdown from 'react-native-select-dropdown';
import { DataTable } from 'react-native-paper';
import Toggle from '../components/common/Toggle.component';
import NumericPadModal from '../components/common/NumericPadModal.component';
import imagePrinter from '../assets/print_icon.png';
import imageStorage from '../assets/storage_icon.png';
import AppContext from '../store/AppContext';



const DetailScreen = ({navigation, route}) => {
  const { retNumber } = route.params;
  const [debugInfo, setDebugInfo] = useState('');
  const [isVisible, setVisible] = useState('');
  const [amount, setAmount] = useState('0');
  const numpadRef = useRef(null);
  const eanInputRef = useRef();
  const [artNumber, setArtNumber] = useState(0);
  const [eanValue, setEanValue] = useState('5900116029993');
  const [isLoading, setLoading] = useState(false);
  const [shipmentProduct, setShipmentProduct] = useState([]);


  const appCtx = useContext(AppContext);
  const scanPalletCounterValue = appCtx.scanPalletCounterValue;
  const scanMultiperValue = appCtx.scanMultiperValue;

  const scanCMValue = appCtx.scanCMValue;
  const scanPrintValue = appCtx.scanPrintValue;
  const scanStorageValue = appCtx.scanStorageValue;
  const settingsPortValue = appCtx.settingsPortValue;

  const [selectedOption, setSelectedOption] = useState(1);
  const options = [
    {title: 'Dobre', code: '1'},
    {title: 'Uszkodzone', code: '2'},
    {title: 'Z ceną', code: '3'},
    {title: 'Utylizacja', code: '4'},
  ];

  const setDefaultFocus = () => {
    eanInputRef.current.focus();
  };
  const onModalPress = (place, amount) => {
    setDebugInfo('onModalPress ['+place+']'+amount);
    if (place === 'x'){
      appCtx.setScanMultiperValue(amount);
    }else {
      appCtx.setScanPalletCounterValue(amount);
    }
    setDefaultFocus();
  };
  const onPressCMHandler = (val) => {
    appCtx.setScanCMValue(val);
    setDefaultFocus();
  };
  const onPressStorageHandler = (val) => {
    appCtx.setScanStorageValue(val);
    setDefaultFocus();
  };
  const onPressPrintHandler = (val) => {
    appCtx.setScanPrintValue(val);
    setDefaultFocus();
  };
  const checkStorageHandler = () => {
    appCtx.setScanStorageValue(1);
    setDefaultFocus();
  };
  const uncheckStorageHandler = () => {
    appCtx.setScanStorageValue(0);
    setDefaultFocus();
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

    setDebugInfo('sendDataToServer.scanPrintValue: ' + scanPrintValue);
    if ( scanPrintValue === 1){
      printLabel();
      setDebugInfo('sendDataToServer.scanPrintValue: ' + scanPrintValue);
    } else if (scanStorageValue === 1) {

      updateProduct(ean);
      setDebugInfo('sendDataToServer.scanStorageValue: ' + scanStorageValue);
    }

    getShipmentProductInfo(ean);
    setDefaultFocus();
  }

  const printLabel = async () => {
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
        // .then((response) => response.json())
        .then(responseData => {
          console.log(responseData);
          return responseData;
        })
        .then((data) => {
          console.info(data);
        })
        .catch((error) => console.error(error))
        .finally(() => setLoading(false));
    } catch (error) {
      console.error(error);
    }
    appCtx.setScanMultiperValue(0);
    appCtx.setScanPalletCounterValue(0);
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
  const updateProduct =  (ean) => {
    console.log('updateProduct['+ean+']' + selectedOption + ' : ' + appCtx.scanMultiperValue)
    const requestOptions = {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' }
    };

    try {

      if (selectedOption == 1) {
        setScanCorrect(appCtx.scanMultiperValue);
      } else if (selectedOption == 2) {
        setScanError(appCtx.scanMultiperValue);
      } else if (selectedOption == 3) {
        setScanLabel(appCtx.scanMultiperValue);
      } else if (selectedOption == 4) {
        setScanUtilization(appCtx.scanMultiperValue);
      }

       fetch(''+appCtx.settingsURLValue+':'+appCtx.settingsPortValue +'/shipment_product/updateproduct?' +
        'retNumber='+retNumber+'&' +
        'ean='+ean+'&' +
        'scanCorrect='+scanCorrect+'&' +
        'scanError='+scanError+'&' +
        'scanLabel='+scanLabel+'&' +
        'scanUtilization='+scanUtilization, requestOptions)
        .then((response) => response.json())
        // .then(responseData => {
        //   // console.log(responseData);
        //   return responseData;
        // })
        // .then((data) => {
          // console.info(data);

        // })
        .catch((error) => console.error(error))
        .finally(() =>{
          getShipmentProductInfo(ean);
          setLoading(false);
          }
        );
    } catch (error) {
      console.error(error);
    }
    appCtx.setScanMultiperValue(0);
    appCtx.setScanPalletCounterValue(0);
    setDefaultFocus();
  }

  getShipmentProductInfo = (ean) => {
    setShipmentProduct([]);

    fetch(''+appCtx.settingsURLValue+':'+appCtx.settingsPortValue +'/shipment_product/findbyean?retNumber=' + retNumber + '&ean=' + ean)
      .then((response) => response.json())
      .then(responseData => {console.log(responseData); return responseData;})
      .then((data) => {
        setShipmentProduct(data);
        setArtNumber(data.artNumber);
      })
      .catch((error) => console.error(error))
      .finally(() => setLoading(false));
    setDefaultFocus();
  }

  async function loadProperties() {
    try {
      console.log("loadProperties: " );
      appCtx.setScanPalletCounterValue(0);
      appCtx.setScanMultiperValue(0);
      appCtx.setScanStorageValue(0);

    } catch(e) {
      console.error(e)
    }
    setDefaultFocus();
  }

  useEffect(() => {
    loadProperties();
  }, []);

  return (
    <SafeAreaView style={styles.container}>
        <View style={{flex: 1}}>
          <View style={{flexDirection:'row', alignItems:'center', justifyContent:'center'}}>
            <SelectDropdown
              buttonStyle={styles.dropdown4BtnStyle}
              // buttonTextStyle={styles.dropdown4BtnTxtStyle}
              data={options}
              defaultValueByIndex='0'
              onSelect={(selectedItem, index) => {
                // console.log(selectedItem.title, selectedItem.code, index);
                setSelectedOption(selectedItem.code);
                eanInputRef.current.focus();
              }}
              buttonTextAfterSelection={(selectedItem, index) => {
                // text represented after item is selected
                // if data array is an array of objects then return selectedItem.property to render after item is selected
                return selectedItem;
              }}
              rowTextForSelection={(item, index) => {
                // text represented for each item in dropdown
                // if data array is an array of objects then return item.property to represent item in dropdown
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
              returnValue={debugInfo}
            />
          </View>

          <View style={styles.sectionStyle}>
            <TextInput
              style={styles.input}
              placeholder='EAN'
              autoFocus={true}
              keyboardType='numeric'
              ref={eanInputRef}
              value={eanValue}
              onChange={(event) => sendDataToServer( event.nativeEvent.text)}
              onKeyPress={(event) => sendDataToServer( event.nativeEvent.text)}
              onSubmitEditing={(event) => sendDataToServer( event.nativeEvent.text)}
              onChangeText={(text) => {
                setEanValue(text);
                sendDataToServer(text);
              }}
            />
          </View>
          <Text>
            {debugInfo}
          </Text>


          <ScrollView style={styles.scrollView}>
            <View style={styles.container}>

              <DataTable style={styles.dataTable}>
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
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>Wszystkie</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{shipmentProduct.counter}({shipmentProduct.scanCorrect + shipmentProduct.scanError} )</DataTable.Cell>
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

                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>settingsPortValue</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{settingsPortValue}</DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>scanStorageValue</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{scanStorageValue}</DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>scanPrintValue</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{scanPrintValue}</DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>scanCMValue</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{scanCMValue}</DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>scanMultiperValue</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{scanMultiperValue}</DataTable.Cell>
                </DataTable.Row>
                <DataTable.Row style={styles.dataTableRow}>
                  <DataTable.Cell style={styles.dataTableCellLeft}>scanPalletCounterValue</DataTable.Cell>
                  <DataTable.Cell style={styles.dataTableCell}>{scanPalletCounterValue}</DataTable.Cell>
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
              initVal='false'
              onPress={onPressPrintHandler}
              onCheck={checkPrintHandler}
              onUncheck={uncheckPrintHandler}
              imgScr={imagePrinter}
            />
            <Toggle
              initVal='false'
              onPress={onPressCMHandler}
              onCheck={checkCMHandler}
              onUncheck={uncheckCMHandler}
              valueCheck={<Text>C</Text>}
              valueUnCheck={<Text>M</Text>}
            />
            <Toggle
              initVal='false'
              onPress={onPressStorageHandler}
              onCheck={checkStorageHandler}
              onUncheck={uncheckStorageHandler}
              imgScr={imageStorage}
            />
          </View>
        </View>
    </SafeAreaView>
  );
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

  dropdown4BtnStyle: {
    width: '50%',
    height: 40,
    backgroundColor: '#FFF',
    borderRadius: 4,
    borderWidth: 1,
    borderColor: '#444',
  },
  dropdown4BtnTxtStyle: {color: '#444', textAlign: 'left'},
  dropdown4DropdownStyle: {backgroundColor: '#EFEFEF'},
  dropdown4RowStyle: {backgroundColor: '#EFEFEF', borderBottomColor: '#C5C5C5'},
  dropdown4RowTxtStyle: {color: '#444', textAlign: 'left'},

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
    // marginTop: LAYOUT['spacing-06'],
    // color: COLORS['brand-01']
  },
  amountButton: {
    // color: 'white',
    // fontSize: 6,
    textAlign: 'center',
    borderWidth: 1,
    width: '30%',
    // alignItems: 'center',
    // justifyContent: 'center',
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
    // alignItems: 'stretch',
    // flexDirection:'row',
  },
  dataTableCell: {
    margin: 0,
    bottom: 0,
    // alignItems: 'stretch',

    // flexDirection:'row',
    // overflow: 'visible',
    // // justifyContent: 'center',
    // alignItems: 'stretch',
    // alignSelf: 'stretch',
    // // alignItems: 'stretch',
// borderWidth: 1,
    // textAlignVertical: 'center',
    // flex: 1,
    // minHeight: 120,
    // flexWrap: 'wrap',
    // flexDirection:'row',
    // alignItems: 'stretch',
    // minHeight: 120,
    // flexWrap: 'wrap',
    // display: 'flex',
    // writingDirection: 'auto',
    // justifyContent: 'space-between',
    // flexWrap
    // justifyContent: 'center',
  },
  dataTableCellLeft: {
    margin: 0,
    bottom: 0,
    maxWidth: 80,
    // verticalAlign: 'top',
    textAlignVertical: 'top',
    textAlign: 'left',
    // justifyContent: '',
    // alignItems: 'stretch',
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
