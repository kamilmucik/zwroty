import React, { useContext, useState, useRef, useEffect } from "react";
import { Text, View, TouchableOpacity, Image, TextInput, SafeAreaView, ActivityIndicator, ScrollView } from 'react-native';
import { DataTable } from 'react-native-paper';
import { useNavigation } from '@react-navigation/native';
import { showMessage } from "react-native-flash-message";
import styles from './ScanSheetStyles';
import QRCodeScanner from 'react-native-qrcode-scanner';
import {useCustomFetch} from '../../hooks/useCustomFetch'
import {useCustomEANValidator} from '../../hooks/useCustomEANValidator'
import {useDebounce} from "../../hooks/useDebounce";
import CustomImage from '../../components/CunstomImage';
import { Button, InputText , InputSwitch }  from '../../components/Form.tsx';
import  AsyncStorage  from '@react-native-async-storage/async-storage';
import AppContext from "../../store/AppContext";
import { BASE_API_URL } from '../../config.tsx';

const ScanScreen = () => {
    const navigation = useNavigation();
    const eanInputRef = useRef();
    const scanner = useRef(null);
    const appCtx = useContext(AppContext);

    const [scan, setScan] = useState(false);
    const [isFocused, setIsFocused] = useState(false);
    const [isLoaded, setIsLoaded] = useState(false);
    const [eanValue, setEanValue] = useState('');
    const [eanScannerValue, setEanScannerValue] = useState('');
    const [ean2SendValue, setEan2SendValue] = useState('');
    const debouncedValue = useDebounce(eanValue);

    const [query, setQuery] = useState('');
    const {loading, singleResult} = useCustomFetch(query);
    const {eanValid} = useCustomEANValidator(debouncedValue);
    const [eanValidValue, setEanValidValue] = useState('false');

    const fetchProduct = async (ean) => {
      setQuery('productimageversion/findbyean?ean='+ean);
    }

  async function loadProperties() {
    // const value = await AsyncStorage.getItem('@storage_versions2');
    // let parsed = JSON.parse(value);
    // if(value !== null && parsed !==null) {
    //   appCtx.setSettingsDestinationURL(parsed.destinationURL);
    // } else {
      appCtx.setSettingsDestinationURL(BASE_API_URL);
    // }
  }

  useEffect(() => {
      loadProperties();
    }, []);

    useEffect(() => {
      if (eanValid === true && ean2SendValue.length > 0){
        setEanValidValue("true");
      } else {
        setEanValidValue("false");
      }

      if (ean2SendValue.length > 0){
        fetchProduct(ean2SendValue);
      }
    }, [ean2SendValue]);

  useEffect(() => {
      setEan2SendValue(eanScannerValue);
  }, [eanScannerValue]);

  const handleSetEAN =  () => {
    setEan2SendValue(eanValue);
    setEanValue('');
    setEanScannerValue('');
  };

  // useEffect(() => {
  //   // console.log("debouncedValue: " + debouncedValue + " - " + debouncedValue.length + " - " + eanValid);
  //   // if (eanValid === true  && debouncedValue.length > 6){
  //     setEan2SendValue(debouncedValue);
  //   // }
    
  // }, [debouncedValue]);

  useEffect(() => {
    if (singleResult?.ean === undefined){
      setIsLoaded(false);
    } else {
      setIsLoaded(true);
    }
    if (singleResult === undefined){
      showMessage({
        message: "Brak w bazie!",
        type: "warning",
        });

    } else {

      setEanValue('');
      setEanScannerValue('');
      setEan2SendValue('');
    }
    
  }, [singleResult]);


    // useEffect(() => {
    // if (isFocused === false){
    //     setDefaultFocus();
    // }
    // }, [isFocused]);

    // const setDefaultFocus = () => {
    //     eanInputRef.current.focus();
    // };

    const launchScannerCamera = () => {
      setScan(true);
    }
    const onSuccess = e => {
      setEanScannerValue('' + e.data);
      setScan(false);
    }

    return !scan ? (
      <ScrollView  >
        <View  style={styles.mainContainer}>
            <View style={styles.rowContainer} >
              <View style={[styles.inputSection]}>
                <TextInput
                  placeholder='EAN'
                  autoFocus={true}
                  keyboardType='numeric'
                  editable={!loading}
                  value={eanValue}
                  style={styles.eanTextInput} 
                  onFocus={() => setIsFocused(true)}
                  onBlur={() => setIsFocused(false)}
                  onChangeText={(event) => {
                    setEanValue(event);
                  }}
                  onSubmitEditing = {handleSetEAN}
                />
                <TouchableOpacity onPress={launchScannerCamera} >
                  <Image source={require('../../assets/img/barcode.png')}
                        style={{ width: 60, height: 38 }}
                  />
                </TouchableOpacity>
              </View>
          </View>
          <View>

          <Text>{appCtx.settingsDestinationURL}/{query}</Text>
          </View>

          <View >
          {loading && 
          <View>
            <ActivityIndicator size='large'/>
          </View>}
          {/* <Text>debouncedValue: {debouncedValue} </Text>
          <Text>eanValue: {eanValue}</Text>
          <Text>ean2SendValue: {ean2SendValue}</Text>
          <Text>eanValidValue: {eanValidValue}</Text> */}
          
          <DataTable  >
            <DataTable.Row >
              <DataTable.Cell >Numer artykułu</DataTable.Cell>
              <DataTable.Cell >{singleResult?.artNumber}</DataTable.Cell>
            </DataTable.Row>
            <DataTable.Row >
              <DataTable.Cell >EAN</DataTable.Cell>
              <DataTable.Cell >
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
                        {singleResult?.ean}
                      </Text>
                    </View>
                </DataTable.Cell>
            </DataTable.Row>
            <DataTable.Row >
              <DataTable.Cell>Nazwa</DataTable.Cell>
              <DataTable.Cell>
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
                        {singleResult?.title}
                      </Text>
                    </View>
              </DataTable.Cell>
            </DataTable.Row>
          </DataTable>

          {isLoaded ? (
            <View >
              {singleResult?.revisions.map(d => (
                <CustomImage 
                  label={d.hashGroup} 
                  hash={d.imgPath} 
                  onPress={() =>
                    navigation.navigate('ScanImage', {
                      hash: d.hashGroup,
                      versionId: singleResult?.id,
                      ean: singleResult?.ean,
                      artNumber: singleResult?.artNumber
                    })
                  }
                />
              ))} 
            </View>

          ) : (
            <Text style={styles.titleResult}></Text>
          )}

{isLoaded ? (
          <Button
              text="Dodaj nowy" 
              onPress={() =>
                navigation.navigate('ScanImage', {
                  hash: null,
                  versionId: singleResult?.id,
                  ean: singleResult?.ean,
                  artNumber: singleResult?.artNumber
                })
              }/>
              ) : (
                <Text style={styles.titleResult}></Text>
              )}
          </View>
        </View>
      </ScrollView>

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
