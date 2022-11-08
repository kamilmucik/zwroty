import React, {Component, useEffect, useState, useRef} from 'react';
import  QRCodeScanner from 'react-native-qrcode-scanner';
import * as ImagePicker from "react-native-image-picker"
import { StyleSheet, Text, View, TouchableOpacity, TextInput, Button, Image, SafeAreaView, ScrollView,PermissionsAndroid } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

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



const AddItem = ({ route, navigation  }) => {
  
  const { _itemId, _ean, artNumber,title,reason, imgFrontBase64,imgBackBase64} = route.params;
  // const scanner = useRef(null);
  const [debugInfo, setDebugInfo] = useState('');
  const [mainEndpoint, setMainEndpoint] = useState('');
  // const [inputEAN, setInputEAN] = useState('');
  const [inputReason, setInputReason] = useState('');
  const [fileBase64Front, setFileBase64Front] = useState('');
  const [fileBase64Back, setFileBase64Back] = useState('');
  // const [scan, setScan] = useState(false);
  const [result, setResult] = useState(null);

  AsyncStorage.getItem('@storage_Key').then((value) => {
    if (value) {
      setMainEndpoint(value);
    }else{
      setDebugInfo("URL serwera jest niezdefiniowany")
      // this.setState({sourceUrl:'http://www.e-strix.pl/api/megapack'})
    }
  })


  useEffect(()=> {
    setResult(null);
  },[])

  const launchCamera = typ => {
    let options = {
      includeBase64: true,
      mediaType: 'photo',
      maxWidth: 800,
      maxHeight: 600,
      quality: 1.0,
      storageOptions: {
        skipBackup: true,
        path: 'images',
      },
    };

    requestCameraPermission();
    
    ImagePicker.launchCamera(options, async (response) => {
      if (response.didCancel) {
        console.log('User cancelled image picker');
      } else if (response.error) {
        console.log('ImagePicker Error: ', response.error);
      } else if (response.customButton) {
        console.log('User tapped custom button: ', response.customButton);
        alert(response.customButton);
      } else {
        if (typ === 1){
          setFileBase64Front(response.assets[0].base64);
        } else {
          setFileBase64Back(response.assets[0].base64);
        }
      }
    });

  }

const launchScannerCamera = () => {
  setScan(true);
}


  const sendToServer = () => {
    fetch(mainEndpoint + '/productimageversion/add-version', {
      method: 'POST',
      headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        versionImageId: _itemId.itemId,
        reason: inputReason,
        imgFrontBase64: fileBase64Front,
        imgBackBase64: fileBase64Back
      })
  })
  .then((response) => response.json())
      .then((responseJson) => {
        setDebugInfo(responseJson.message);
      })
      .catch((error) => {
        setDebugInfo("Błąd dodawania zdjęcia");
        console.error(error);
      });
  };

  const renderFileData = typ => {

    if (typ === 1 && fileBase64Front){
        return <Image
        source={{
          uri: 'data:image/jpeg;base64,' + fileBase64Front,
        }}
        style={{ width: 200, height: 200 }}
      /> 
    } else if (typ === 2 && fileBase64Back){
        return <Image
        source={{
          uri: 'data:image/jpeg;base64,' + fileBase64Back,
        }}
        style={{ width: 200, height: 200 }}
      /> 
    } else {
      return <Image source={require('../assets/blank.png')}
      style={{ width: 200, height: 200 }}
      />
    }    
  }

  const onSuccess = e => {
    setInputEAN(e.data);
    setScan(false);
  }
    return (
      <SafeAreaView style={styles.container}>
        <ScrollView style={styles.scrollView}>
        <View style={{flex: 1}}>
          <Text style={{ alignItems: 'center' }}>
            {debugInfo}
          </Text> 
          
          <Text>EAN: {_ean.ean}</Text>

          <View style={styles.sectionStyle}>
            <TextInput 
              placeholder='Powód zmian'
              style={styles.inputField}
              value={inputReason}
              onChangeText={(text) => setInputReason(text)}
            />
          </View>
          <TouchableOpacity 
            onPress={() => launchCamera(1)}
            style={styles.chooseImage}>
            {renderFileData(1)}
            <Text style={styles.btnText}>Przód: Zrób zdjęcie</Text>
          </TouchableOpacity>
          <TouchableOpacity 
            onPress={() => launchCamera(2)}
            style={styles.chooseImage}>
            {renderFileData(2)}
            <Text style={styles.btnText}>Tył: Zrób zdjęcie</Text>
          </TouchableOpacity>


          <TouchableOpacity onPress={sendToServer} style={styles.button}  >
              <Text style={styles.buttonText}>Wyślij na serwer</Text>
          </TouchableOpacity>       
        </View>
      </ScrollView>
     </SafeAreaView>
    )
    
  }


const styles = StyleSheet.create({
  container: {
    
    backgroundColor: '#fff'
  },
  chooseImage: {
    
    alignItems: 'center',
  },
  button: {
    margin: 10,
    padding: 14,
    backgroundColor: '#3740ff',
    borderRadius: 4,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
  },

  
  button2: {
    // backgroundColor: '#3740ff',
    borderRadius: 4,
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 4,
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

  btnSection: {
    width: 225,
    height: 50,
    backgroundColor: '#DCDCDC',
    alignItems: 'center',
    justifyContent: 'center',
    borderRadius: 3,
    marginBottom:10
  },
  buttonText: {
    textAlign: 'center',
    fontSize: 15,
    color: '#fff'
  },
  buttonText2: {
    textAlign: 'center',
    fontSize: 20,
    color: '#DCDCDC'
  },
  
  inputField: {
    color: 'black',
    flex: 5,
    fontSize: 14,
    textAlign: 'center',
    borderColor: 'black'
  },

  buttonStyle: {
    fontSize: 16,
    color: 'white',
    backgroundColor: 'green',
    padding: 5,
    margin: 10,
    minWidth: 250,
  },
  buttonTextStyle: {
    padding: 5,
    color: 'white',
    textAlign: 'center',
  },
  textLinkStyle: {
    color: 'blue',
    paddingVertical: 20,
  },
});

export default AddItem;