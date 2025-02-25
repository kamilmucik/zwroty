
import {useSafeAreaInsets} from "react-native-safe-area-context";
import React, {useEffect, useState} from "react";
import { StyleSheet, Text, View, TouchableOpacity, TextInput, Button, Image, SafeAreaView, ScrollView,PermissionsAndroid, ActivityIndicator } from 'react-native';
import { showMessage, hideMessage } from "react-native-flash-message";

// import {BASE_API_URL} from "../../config";
// import * as ImagePicker from "react-native-image-picker"
import { launchImageLibrary as _launchImageLibrary, launchCamera as _launchCamera } from 'react-native-image-picker';
let launchImageLibrary = _launchImageLibrary;
let launchCamera = _launchCamera;

const OCRTestScreen = ({ navigation }) => {
    const [selectedImage, setSelectedImage] = useState(null);
      const [loading, setLoading] = useState(false);
      const [fileBase64Front, setFileBase64Front] = useState('');
      const [debugInfo, setDebugInfo] = useState('');
      const [debugError, setDebugError] = useState('');
    
    
      useEffect(() => {
        // if (loading) return;
        // sendPart1(fileBase64Front);
        console.log("zmiana: fileBase64Front");
        var retId = sendPart1(fileBase64Front);
    
        console.log("zmiana: " , retId);
      }, [fileBase64Front]);
    
      const sendPart1 = async (_fileBase64Front) => {
        const res = await fetch( 'http://162.19.227.81:8081/productimageversion/ocr-test', {
          method: 'POST',
          headers: {
              Accept: 'application/json',
              'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            versionImageId: 1,
            reason: 'test',
            imgFrontBase64: _fileBase64Front
          })
        })
        .then((response) => response.json())
            .then((responseJson) => {
    
              // console.log("responseJson: " + responseJson.message);
              setDebugInfo("responseJson.1: " + responseJson.message);
              // setObjId(responseJson.dto.id);
              // sendPart2(responseJson.dto.id);
              // sendPart3(responseJson.dto.id);
              // return responseJson.dto.id;
            })
            .catch((error) => {
              // setDebugInfo("Błąd dodawania zdjęcia");
              setDebugInfo("err" + error);
            });
            // setDebugInfo("done");
            // const json = await res;
       
      };
    
      const openImagePicker = () => {
        showMessage({
          message: "Hello World",
          description: "This is our second message",
          type: "success",
        });
        // const options = {
        //   mediaType: 'photo',
        //   includeBase64: true,
        //   maxHeight: 2000,
        //   maxWidth: 2000,
        // };
    
        // launchImageLibrary(options, handleResponse);
      };
    
      const handleCameraLaunch = () => {
        const options = {
          mediaType: 'photo',
          includeBase64: true,
          maxHeight: 2000,
          maxWidth: 2000,
        };
    
        launchCamera(options, handleResponse);
      };
    
      const handleResponse = (response) => {
        if (response.didCancel) {
          console.log('User cancelled image picker');
        } else if (response.error) {
          console.log('Image picker error: ', response.error);
        } else {
          // let imageUri = response.uri || response.assets?.[0]?.uri;
          let imageUri = response.uri || response.assets?.[0]?.base64;
          setFileBase64Front(imageUri);
          // setSelectedImage(imageUri);
        }
      };
    
      return (
        <View style={{ flex: 1, justifyContent: 'center' }}>
          {selectedImage && (
            // <Text>Przód:</Text>
            <Image
                source={{
                  uri: 'data:image/jpeg;base64,' + fileBase64Front,
                }}
    
              style={{ width: 250, height: 300, alignItems: 'center'  }}
              />
          )}
          
          <View style={{ marginTop: 20 }}>
            <Text style={{ alignItems: 'center' }}>
                {debugInfo}
            </Text> 
          </View>
          <View style={{ marginTop: 20 }}>
            <Button title="Choose from Device" onPress={openImagePicker} />
          </View>
          <View style={{ marginTop: 20, marginBottom: 50 }}>
            <Button title="Open Camera" onPress={handleCameraLaunch} />
          </View>
        </View>
      );
};

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

export default OCRTestScreen;
