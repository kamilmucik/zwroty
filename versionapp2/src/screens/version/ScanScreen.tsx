import React, { useContext, useState, useRef, useEffect } from "react";
import { Text, View, TouchableOpacity, Image, TextInput, SafeAreaView, ActivityIndicator, ScrollView, RefreshControl, Modal , Pressable, StyleSheet, Dimensions, ImageBackground} from 'react-native';
import { DataTable } from 'react-native-paper';
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
import {useCustomPost} from '../../hooks/useCustomPost'
import ImageViewer from 'react-native-image-zoom-viewer';




const ScanScreen = ({navigation, route}) => {

    const eanInputRef = useRef();
    const scanner = useRef(null);
    const appCtx = useContext(AppContext);

    const [scan, setScan] = useState(false);
    const [isFocused, setIsFocused] = useState(false);
    const [isLoaded, setIsLoaded] = useState(false);
    const [shouldRefresh, setShouldRefresh] = useState();
    const [eanValue, setEanValue] = useState('');
    const [eanScannerValue, setEanScannerValue] = useState('');
    const [ean2SendValue, setEan2SendValue] = useState('');
    const debouncedValue = useDebounce(eanValue);

    const [query, setQuery] = useState('');
    const {loading, singleResult} = useCustomFetch(query);
    const {eanValid} = useCustomEANValidator(debouncedValue);
    const [eanValidValue, setEanValidValue] = useState('false');
    const [images, setImages] = useState([]);
    const [selectedImage, setSelectedImage] = useState({});

    const [deleteData, setDeleteData] = useState(null);
    const {deleteResult} = useCustomPost('productimageversion/delete-image', deleteData, 'DELETE', "FETCH_DELETE_SUCCESS");
    const [posUpData, setPosUpData] = useState(null);
    const {posUpResult} = useCustomPost('productimageversion/change-order', posUpData, 'POST', "FETCH_POSUP_SUCCESS");

    const [modalVisible, setModalVisible] = useState(false);

    const fetchProduct = async (ean) => {
      setQuery('productimageversion/findbyean?ean='+ean);
    }

  async function loadProperties() {
      appCtx.setSettingsDestinationURL(BASE_API_URL);
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


  useEffect(() => {
    setQuery('productimageversion/findbyean?ean='+singleResult?.ean+'&ts='+Date.now());
  }, [route.params?.itemId]);

  

  const handleRefresh = () => {
    // console.log("refresh: " + singleResult?.ean);
    setQuery('productimageversion/findbyean?ean='+singleResult?.ean+'&ts='+Date.now());
  }

  const handleSetEAN =  () => {
    setEan2SendValue(eanValue);
    setEanValue('');
    setEanScannerValue('');
    setImages([]);
  };

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
    
    setImages(singleResult?.revisions);
    // console.log(singleResult?.revisions.imgPath)
  }, [singleResult]);

  useEffect(() => {
    // console.log("deleteResult: " + JSON.stringify(deleteResult));
    // console.log("posUpResult: " + JSON.stringify(posUpResult));
  //   // console.log("singleResult: " + JSON.stringify(singleResult?.ean));
    handleRefresh();
  }, [ deleteResult?.dto?.orderTimestamp, posUpResult?.dto?.orderTimestamp]);
  

    const launchScannerCamera = () => {
      setScan(true);
    }
    const onSuccess = e => {
      setEanScannerValue('' + e.data);
      setScan(false);
    }

    const onPressZoom= (url) =>{
      setSelectedImage([{url: url,}]);
      setModalVisible(true);
    }
    const onPressPosUp= (hashGroup) =>{
      setPosUpData({
        hashGroup: hashGroup,
      });
    }
    const onPressDelete= (hashGroup) =>{
      setDeleteData({
        hashGroup: hashGroup,
      });
    }

    return !scan ? (
      <ScrollView  refreshControl={
        <RefreshControl refreshing={loading} onRefresh={handleRefresh} />
      }>

<View>

<Modal
  visible={modalVisible}
  transparent={false}
  onRequestClose={() => setModalVisible(!modalVisible)}>
    <ImageViewer imageUrls={selectedImage} />
</Modal>
</View>




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
          {appCtx.isDebugMode === true ?
          <View>
          <Text>{appCtx.settingsDestinationURL}/{query}</Text>
            <Text>{shouldRefresh}</Text>
          </View>
          :<View></View>}
          
          </View>

          <View >
          {loading && 
          <View>
            <ActivityIndicator size='large'/>
          </View>}
          
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
              {images.map(d => (
                <CustomImage 
                  label={d.hashGroup} 
                  hashGroup={d.hashGroup} 
                  hash={d.imgPath} 
                  id={d.id} 
                  refreshTs={shouldRefresh}
                  onPressZoom={onPressZoom}
                  onPressPosUp={onPressPosUp}
                  onPressDelete={onPressDelete}
                  onPress={() =>
                    navigation.navigate('ScanImage', {
                      hash: d.hashGroup,
                      versionId: singleResult?.id,
                      ean: singleResult?.ean,
                      imgPath: d.imgPath,
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


const stylesModal = StyleSheet.create({
  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  modalView: {
    margin: 20,
    backgroundColor: 'white',
    borderRadius: 5,
    padding: 5,
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.25,
    shadowRadius: 4,
    elevation: 5,
  },
  button: {
    borderRadius: 20,
    padding: 10,
    elevation: 2,
  },
  buttonOpen: {
    margin: 15,
    backgroundColor: '#F194FF',
  },
  buttonClose: {
    backgroundColor: '#2196F3',
  },
  textStyle: {
    color: 'white',
    fontWeight: 'bold',
    textAlign: 'center',
  },
  modalText: {
    marginBottom: 15,
    textAlign: 'center',
  },
});

export default ScanScreen;
