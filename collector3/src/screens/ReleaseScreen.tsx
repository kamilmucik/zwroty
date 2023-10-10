import React, { useState, useRef, useEffect, useContext } from "react";
import { StyleSheet,Text, View, TextInput,SafeAreaView, ScrollView,TouchableOpacity, Image, ActivityIndicator } from 'react-native';
import AppContext from "../store/AppContext";
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

///"/release/updateget/" + code

const ReleaseScreen = ({navigation, route}) => {


    const appCtx = useContext(AppContext);
    const appCtxSettingsURLValue = appCtx.settingsURLValue;
    const appCtxSettingsPortValue = appCtx.settingsPortValue;
    const [debugInfo, setDebugInfo] = useState('');
    const eanInputRef = useRef();
    const [eanScannerValue, setEanScannerValue] = useState('');

    // const [eanValue, setEanValue] = useState('20230818200269266736000669C00');
    const [eanValue, setEanValue] = useState('');
    const [ean2SendedValue, setEan2SendedValue] = useState('');
    const [isLoading, setLoading] = useState(false);

    const [scan, setScan] = useState(false);
    const scanner = useRef(null);
    const [isFocused, setIsFocused] = useState(false);

    useEffect(() => {
        // let lenCond = eanValue.length > 27;
        // console.log("ean2SendValue.length: "+ lenCond + " : " + eanValue );
        // if (lenCond) {
        // //   setEan2SendValue12345('');
        //   setEanValue('');
        //   setEanScannerValue('');
        //   appCtx.setToastInfoValue('Błąd kodu kreskowego ('+eanValue+'). Posiada ('+eanValue.length+') znaków.', 'info');
        //   return;
        // }

        let lenCond2 = eanValue.length == 29;
        if (lenCond2) {
            sendDataToServer(eanValue);
        }
    
        // if (eanValidate(eanValue) === false) {
        //   setEan2SendValue(eanValue);
        // }
    
        // let condition = eanValidate(eanValue);
        // // setEanScannerDebugValue("eanValue.condition["+condition+"]: " + ean2SendValue + " : " + eanValidate(eanValue));
        // if (condition === true ){
        //   // setEanScannerDebugValue("eanValue.condition["+condition+"]: " + ean2SendValue + "/" + eanValue + " : " + eanValidate(eanValue));
        //   sendDataToServer(eanValue);
        // }
    
        // console.log("eanValue.zmiana: " + eanValue + " : " + eanValidate(eanValue));
      }, [eanValue]);
    
    useEffect(() => {
        if (isFocused === false){
            setDefaultFocus();
        }
    }, [isFocused]);

    const setDefaultFocus = () => {
        if (eanInputRef.current !== null){
            eanInputRef.current.focus();
        }
      };


    async function loadProperties() {
        try {
            setDefaultFocus();
        } catch(e) {
        console.error(e);
        }
    }

    const launchScannerCamera = () => {
        setScan(true);
    }
    const onSuccess = e => {
        // setDebugInfo('' + e.data);
        setEanValue('' + e.data);
        setScan(false);
    }

    useEffect(() => {
        loadProperties();
    }, []);

    sendDataToServer = (ean) => {
        setLoading(true);
    
        releaseProductInfo(ean);
        setEanValue('');
        setDefaultFocus();
      }

      const releaseProductInfo = (ean) => {
        setLoading(true);
      
        try {
            setDebugInfo(''+appCtx.settingsURLValue+':'+appCtx.settingsPortValue +'/release/updateget/' + ean);
          fetch(''+appCtx.settingsURLValue+':'+appCtx.settingsPortValue +'/release/updateget/' + ean)
            .then((response) => response.json())
            .then(responseData => { return responseData;})
            .then((data) => {
            //   if (data.status === 500){
            //     appCtx.setToastInfoValue('Brak produku w bazie.', 'info');
            //   } else {
                
                
            //   }
                setEan2SendedValue(ean);
      
            })
            .catch((error) => {
              appCtx.setToastInfoValue('Nie można pobrac danych! Możliwy problem z siecią internet.', 'error');
            })
            .finally(() => setLoading(false));
    
        } catch (error) {
          console.error(error);
        }
        appCtx.setScanMultiperValue(0);
        appCtx.setScanPalletCounterValue(0);
        setDefaultFocus();
      }

    return !scan ? (
        <SafeAreaView style={[GlobalStyle.AppContainer]}>
          <InfoToast></InfoToast>
          {isLoading ? <ActivityIndicator /> : <Text/>}
          <ScrollView style={[GlobalStyle.AppScrollView]}>
            <View style={{flex: 1}}>
                <View style={[GlobalStyle.AppInputSection]}>
                <TextInput
                    style={[GlobalStyle.AppInput]}
                    placeholder='Kod'
                    autoFocus={true}
                    keyboardType='text'
                    editable={!isLoading}
                    ref={eanInputRef}
                    value={eanValue}
                    onFocus={() => setIsFocused(true)}
                    onBlur={() => setIsFocused(false)}
                    onChangeText={(event) => {
                        setEanValue(event);
                    }}
                />
                {appCtx.isMobile === 'true' ? <TouchableOpacity onPress={launchScannerCamera} style={styles.button2}>
                <Image source={require('../assets/barcode.png')}
                        style={{ width: 60, height: 38 }}
                />
                </TouchableOpacity> : <View/>}

            </View>
              <Text>
                Informacje
              </Text>
              <Text>
                {ean2SendedValue}
              </Text>
              {appCtx.isDebugMode === 'true' ?
                <Text>
                    {debugInfo}
                </Text>
              : <Text/>}
            </View>
          </ScrollView>
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

};

const styles = StyleSheet.create({
    container: {
      flex: 1,
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
  });

export default ReleaseScreen;