import React, {useEffect, useState} from "react";
import { Text, View, TouchableOpacity, Image, ScrollView } from 'react-native';
import { showMessage } from "react-native-flash-message";
import {recognizeImage} from '../../utils/ImageDetailsUtils';
import styles from './OCRTestSheetStyles';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';

import { launchImageLibrary as _launchImageLibrary, launchCamera as _launchCamera } from 'react-native-image-picker';
let launchImageLibrary = _launchImageLibrary;
let launchCamera = _launchCamera;

const OCRTestScreen = ({ navigation }) => {
      const [response, setResponse] = useState<Array<responseType>>([]);
      const [isLoading, setIsLoading] = useState(true);
      const [fileBase64Front, setFileBase64Front] = useState('');
      const [uri, setURI] = useState('');

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
    
      return (
        <ScrollView  >
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
            <Image
                source={{
                  uri: 'data:image/jpeg;base64,' + fileBase64Front,
                }}
                resizeMode="contain"
                style={styles.image} 
              />
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
        </View>
      </ScrollView>
      );
};


export default OCRTestScreen;
