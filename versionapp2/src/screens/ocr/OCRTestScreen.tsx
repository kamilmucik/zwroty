import React, {useEffect, useState} from "react";
import { Text, View, TouchableOpacity, ScrollView, Modal, ImageBackground } from 'react-native';
import { showMessage } from "react-native-flash-message";
import {recognizeImage} from '../../utils/ImageDetailsUtils';
import styles from './OCRTestSheetStyles';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import { Button, InputSwitch }  from '../../components/Form.tsx';
import ImageViewer from 'react-native-image-zoom-viewer';
import uuid from 'react-native-uuid';

import { launchImageLibrary as _launchImageLibrary, launchCamera as _launchCamera } from 'react-native-image-picker';
let launchImageLibrary = _launchImageLibrary;
let launchCamera = _launchCamera;
let nextId = 0;

const OCRTestScreen = ({ navigation }) => {
      const [response, setResponse] = useState<Array<responseType>>([]);
      const [isLoading, setIsLoading] = useState(true);
      const [fileBase64Front, setFileBase64Front] = useState('');
      const [uri, setURI] = useState('');
      const [modalVisible, setModalVisible] = useState(false);
      const [selectedImage, setSelectedImage] = useState({});
      const [textextractorId, setTextextractorId] = useState('');
      const [textextractor1, setTextextractor1] = useState<any>([]);
      const [textextractorWords, setTextextractorWords] = useState<any>([]);
      const [mergeResponse, setMergeResponse] = useState('Nie rozpoznałem tekstu.');

    

      useEffect(() => {
        if (uri) {
          processImage(uri);
        }
      }, [uri]);
    
      const processImage = async (url: string) => {
        if (url) {
          try {
            const result = await recognizeImage(url);
            setIsLoading(false);
            if (result?.blocks?.length > 0) {
              setResponse(result?.blocks);
              var str = '';
              for(var i=0; i< result?.blocks.length - 1; i++){
                  str += result?.blocks[i].text + " "; 
              }
              setMergeResponse(str);
            }
          } catch (error) {
            setIsLoading(false);
            // console.log(error);
            showMessage({
              message: "Rozpoznawanie tekstu z obrazka",
              type: "warning",
            });
          }
        }
      };
    
      const openImagePicker = () => {
        const options = {
          mediaType: 'photo',
          includeBase64: true,
          maxHeight: 1920,
          maxWidth: 1080,
        };
    
        launchImageLibrary(options, handleResponse);
      };
    
      const handleCameraLaunch = () => {
        const options = {
          mediaType: 'photo',
          includeBase64: true,
          maxHeight: 1920,
          maxWidth: 1080,
        };
    
        launchCamera(options, handleResponse);
      };
    
      const handleResponse = (response) => {
        if (response.didCancel) {
          console.log('User cancelled image picker');
          showMessage({
            message: "Przerwanie procesu rozpoznawania",
            type: "warn",
          });
        } else if (response.error) {
          showMessage({
            message: "Rozpoznawanie tekstu z obrazka: " + response.error,
            type: "warning",
          });
        } else {
          let imageUri = response.uri || response.assets?.[0]?.uri;
          let imageB64 = response.uri || response.assets?.[0]?.base64;
          setFileBase64Front(imageB64);
          setURI(imageUri);
        }
      };

      const handleSendTextExtractor = () =>{
        fetch("http://162.19.227.81:8004/extract/", {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              "requestId": uuid.v1(),
              "base64Img": fileBase64Front
          })
        }
        )
        .then( (response) => {
            return response.json();
        })
        .then( (data) => {
            // console.log("data: " + JSON.stringify(data));
            setTextextractorId(data.requestId);
        })
        .catch( (error) => {
            console.error(error);

            showMessage({
              message: error,
              type: "warning",
            });
        });

      };
      const handleRefreshTextExtractor = () =>{
        // setTextextractor1(textextractorId);
        fetch("http://162.19.227.81:8004/extract/"+textextractorId, {
          // fetch("http://162.19.227.81:8004/extract/46759c01-ba78-11f0-be65-5d796a7b1ec9", {
          method: 'GET'
        })
        .then( (response) => {
          return response.json();
        })
        .then( (data) => {
          // console.log("useCustomFetch: " + JSON.stringify(data));
          setTextextractorWords(data.words);
        })
        .catch( (error) => {
          // console.error(error);
          showMessage({
            message: error,
            type: "warning",
          });
        });
      };


    const onPressZoom= (url) =>{
      setSelectedImage([{url: url,}]);
      setModalVisible(true);
    }
    
      return (
        <ScrollView  >
          <Modal
            visible={modalVisible}
            transparent={false}
            onRequestClose={() => setModalVisible(!modalVisible)}>
              <ImageViewer imageUrls={selectedImage} />
          </Modal>

          <View  style={styles.mainContainer}>
            <View style={styles.buttonWrapper}>
              <View style={styles.rowContainer}>
                <TouchableOpacity
                  onPress={handleCameraLaunch} 
                  style={styles.button}>
                  <MaterialCommunityIcons name="camera" style={styles.icon} />
                </TouchableOpacity>
                <TouchableOpacity onPress={openImagePicker}
                  style={styles.button}>
                  <MaterialCommunityIcons name="camera-image" style={styles.icon} />
                </TouchableOpacity>
              </View>
            </View>
            <Text style={styles.titleResult}>Wyniki:</Text>
            
            <View style={styles.rowContainer}>
                <TouchableOpacity
                    onPress={ () => onPressZoom('data:image/jpeg;base64,' + fileBase64Front)} 
                    >
                    <ImageBackground 
                        source={{ uri: 'data:image/jpeg;base64,' + fileBase64Front }}  
                        resizeMode="contain" 
                        style={styles.image}>
                    </ImageBackground>
                  </TouchableOpacity>
            </View>
          
          {response?.length !== 0 ? (
            <View style={styles.resultWrapper}>
              {response?.map((block, index) => {
                return (
                  <Text style={styles.textStyle} key={index}>
                    {block?.text}
                  </Text>
                );
              })}
            </View>
          ) : isLoading ? (
            <Text style={styles.titleResult}></Text>
          ) : (
            <Text style={styles.titleResult}>Nie rozpoznałem tekstu... 🙁</Text>
          )}


            <View style={styles.rowContainer}>
              <Button
              style={styles.buttonWrapper}
                  text="Wyslij"
                  testID="SettingsScreen.SubmitButton"
                  onPress={handleSendTextExtractor} />
                <Button
                  text="Pobierz"
                  testID="SettingsScreen.SubmitButton"
                  onPress={handleRefreshTextExtractor} />
                  </View>
              <View style={styles.rowContainer}>
                {/* <Text>Rezultat[{textextractorId}]:</Text>
                <Text></Text> */}
                <View style={styles.resultWrapper}>
                  {textextractorWords?.map((word, index) => {
                    return (
                      <Text style={styles.textStyle} key={index}>
                        {word}
                      </Text>
                    );
                  })}
                </View>
            </View>


        </View>
      </ScrollView>
      );
};


export default OCRTestScreen;
