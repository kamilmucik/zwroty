import React, {useEffect, useState} from "react";
import { showMessage } from "react-native-flash-message";
import {recognizeImage} from '../../utils/ImageDetailsUtils';
import { Text, View, TouchableOpacity, Image, ScrollView, ActivityIndicator } from 'react-native';
import { Button, InputText , InputSwitch }  from '../../components/Form.tsx';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';

import {useCustomPost} from '../../hooks/useCustomPost'
import styles from './ScanImageSheetStyles';
import { launchImageLibrary as _launchImageLibrary, launchCamera as _launchCamera } from 'react-native-image-picker';
let launchImageLibrary = _launchImageLibrary;
let launchCamera = _launchCamera;

const ScanImageScreen = ({navigation, route}) => {
    const { versionId, hash, ean, artNumber } = route.params;
    const [isLoading, setIsLoading] = useState(true);
    const [changesDetected, setChangesDetected] = useState(false);
    const [fileBase64Front, setFileBase64Front] = useState('');
    const [mergeResponse, setMergeResponse] = useState('Nie rozpoznałem tekstu.');
    const [uri, setURI] = useState('');
    const [formData, setFormData] = useState(null);
    const {loading, singleResult} = useCustomPost('productimageversion/add-image', formData);

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
                    // setResponse(result?.blocks);
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
                message: "Błąd",
                description: "Rozpoznawanie tekstu z obrazka",
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
            message: "Info",
            description: "Przerwanie procesu rozpoznawania",
            type: "info",
            });
        } else if (response.error) {
            showMessage({
            message: "Błąd",
            description: "Rozpoznawanie tekstu z obrazka: " + response.error,
            type: "warning",
            });
        } else {
            let imageUri = response.uri || response.assets?.[0]?.uri;
            let imageB64 = response.uri || response.assets?.[0]?.base64;
            setFileBase64Front(imageB64);
            setURI(imageUri);
    }
    };

    const handleImageVersion =  () => {
        setIsLoading(true);
        setFormData({
            versionId: versionId,
            ean: ean,
            hashGroup: hash,
            artNumber: artNumber,
            description: mergeResponse,
            imgBas64: fileBase64Front
        });
        showMessage({
            message: "Aktualizuje wersje",
            type: "info",
        });
    };

    useEffect(() => {
      // console.log("singleResult: " + JSON.stringify(singleResult));
      if (singleResult?.changesDetected !== undefined){
        setChangesDetected(singleResult?.changesDetected);
      }
      
    }, [singleResult]);

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
          {loading && <ActivityIndicator size='large'/>}
          {changesDetected && <Text style={styles.textStyleWarn}>Uwaga, zmiany w treści!</Text>}
          
          <View style={styles.resultWrapper}>
            <Image
                source={{
                  uri: 'data:image/jpeg;base64,' + fileBase64Front,
                }}
                resizeMode="contain"
                style={styles.image} 
              />
          </View>
          
          {mergeResponse?.length !== 0 ? (
            <View style={styles.resultWrapper}>
                <Text style={styles.textStyle}>
                  {mergeResponse}
                </Text>
            </View>
          ) : isLoading ? (
            <Text style={styles.titleResult}>Czekaj...</Text>
          ) : (
            <Text style={styles.titleResult}>Nie rozpoznałem tekstu... 🙁</Text>
          )}
          <Button 
            text="Zapisz" 
            onPress={handleImageVersion}
            />
            
        </View>
      </ScrollView>
    );
};

export default ScanImageScreen;