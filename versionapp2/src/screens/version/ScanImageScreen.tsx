import React, {useEffect, useState, useContext} from "react";
import { showMessage } from "react-native-flash-message";
import {recognizeImage} from '../../utils/ImageDetailsUtils';
import { Text, View, TouchableOpacity, Image, ScrollView, ActivityIndicator, Modal, Pressable, StyleSheet, ImageBackground } from 'react-native';
import { Button, InputTextArea , InputSwitch }  from '../../components/Form.tsx';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import AppContext from "../../store/AppContext";
import { useNavigation } from '@react-navigation/native';
import ImageViewer from 'react-native-image-zoom-viewer';


import {useCustomPost} from '../../hooks/useCustomPost'
import styles from './ScanImageSheetStyles';
import { launchImageLibrary as _launchImageLibrary, launchCamera as _launchCamera } from 'react-native-image-picker';
let launchImageLibrary = _launchImageLibrary;
let launchCamera = _launchCamera;

const ScanImageScreen = ({navigation, route}) => {
    // const navigation = useNavigation();
    const { versionId, hash, ean, artNumber, imgPath, comment } = route.params;
    const [isLoading, setIsLoading] = useState(true);
    const [changesDetected, setChangesDetected] = useState(false);
    const [mergeVerImages, setMergeVerImages] = useState(false);
    const [aiOCR, setAiOCR] = useState(false);
    const [ownComment, setOwnComment] = useState(comment);
    const [fileBase64Front, setFileBase64Front] = useState('');
    const [mergeResponse, setMergeResponse] = useState('Nie rozpoznałem tekstu.');
    const [uri, setURI] = useState('');

    const [formData, setFormData] = useState(null);
    const {loading, singleResult} = useCustomPost('productimageversion/add-image', formData, 'POST', 'FETCH_SINGLE_SUCCESS');
    const [modalVisible, setModalVisible] = useState(false);
    const [selectedImage, setSelectedImage] = useState({});

    const appCtx = useContext(AppContext);

    useEffect(()=>{
        navigation.addListener("blur",()=>{
            navigation.navigate("Scan", {itemId: (new Date()).getTime()});
        })
    },[])

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
            console.log('imageB64: ' + imageB64 );
        }
    };

    const handleMergeVerImages = () => {
        setMergeVerImages(!mergeVerImages);
    }

    const handleAIOCR = () => {
        setAiOCR(!aiOCR);
    }

    const handleImageVersion =  () => {
        setIsLoading(true);
        setFormData({
            versionId: versionId,
            ean: ean,
            hashGroup: hash,
            artNumber: artNumber,
            description: mergeResponse,
            imgBas64: fileBase64Front,
            merge: mergeVerImages,
            comment: ownComment,
            externalOCRCheck: aiOCR
        });
        showMessage({
            message: "Aktualizuje wersje",
            type: "info",
        });
    };

    useEffect(() => {
        // console.log("singleResult: " + JSON.stringify(singleResult));
        // if (singleResult?.changesDetected !== undefined){
        setChangesDetected(singleResult?.changesDetected);
        setMergeResponse(singleResult?.description);
        // setOwnComment(singleResult?.comment);
        // }

    }, [singleResult]);

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
                {loading && <ActivityIndicator size='large'/>}
                {changesDetected && <Text style={styles.textStyleWarn}>Uwaga, zmiany w treści!</Text>}

                {/*<InputSwitch*/}
                {/*  description="Połącz"*/}
                {/*  onChange={handleMergeVerImages}*/}
                {/*  value={mergeVerImages}*/}
                {/*  />*/}
                <InputSwitch
                    description="AI OCR"
                    onChange={handleAIOCR}
                    value={aiOCR}
                />

                <View style={styles.resultWrapper}>
                    {fileBase64Front?.length === 0 ? (
                        <ScrollView horizontal={true} style={styles.viewTest1}>
                            <TouchableOpacity
                                onPress={ () => onPressZoom(appCtx.settingsDestinationURL+'/productimageversion/get-image?imageHash='+imgPath)}
                            >
                                <ImageBackground
                                    source={{ uri: appCtx.settingsDestinationURL+'/productimageversion/get-image?imageHash='+imgPath }}
                                    resizeMode="contain"
                                    style={styles.imageBig}>
                                </ImageBackground>
                            </TouchableOpacity>
                        </ScrollView>

                    ) : (
                        <TouchableOpacity
                            onPress={ () => onPressZoom('data:image/jpeg;base64,' + fileBase64Front)}
                        >
                            <ImageBackground
                                source={{ uri: 'data:image/jpeg;base64,' + fileBase64Front }}
                                resizeMode="contain"
                                style={styles.imageBig}>
                            </ImageBackground>
                        </TouchableOpacity>

                    )}
                </View>
                <View >
                    <InputTextArea
                        label="Komentarz" 
                        onChange={text => setOwnComment(text)}
                        value={ownComment}
                    />
                </View>

                {mergeResponse?.length !== 0 ? (
                    <View>
                        <View style={styles.resultWrapper}>
                            <Text style={styles.textStyle}>
                                {mergeResponse}
                            </Text>
                        </View>
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

export default ScanImageScreen;
