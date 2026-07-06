import  React, {useContext, useState, useEffect}from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, ActivityIndicator, ImageBackground, Alert} from 'react-native';
import {
  moderateScale,Colors,
} from '../theme';
import AppContext from "../store/AppContext";
import { BASE_API_URL } from '../config.tsx';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Modal } from 'react-native-paper';
import ImageViewer from 'react-native-image-zoom-viewer';

interface CustomImageProps {
    id: number;
    hash: string;
    hashGroup: string;
    label: string;
    refreshTs: string;
    comment: string;
    onPress: any;
    onPressDelete: any;
    onPressPosUp: any;
    onPressZoom: any;
}

const pressableIcon = (iconName) => {
  return <MaterialCommunityIcons name={iconName} size={24} />
};

const CustomImage = (props : RowViewProps) => {

  const appCtx = useContext(AppContext);

  const [imagePlace, setImagePlace] = useState<String>();
  const [resCode, setResCode] = useState<String>();
  const [isLoadImage, setLoadImage] = useState(false);

  const handleZoomClick = () => {
    props.onPressZoom(appCtx.settingsDestinationURL+'/productimageversion/get-image?imageHash='+props.hash+'&ts='+Date.now());
  };
  const handlePosUpClick = () => {
    props.onPressPosUp(props.hashGroup);
  };
  const handleDeleteClick = () => {
    Alert.alert('Usuwanie', 'Czy na pewno usunąć wersję?', [
      {
        text: 'Anuluj',
        onPress: () => console.log('Cancel Pressed'),
        style: 'cancel',
      },
      {text: 'Usuń', onPress: () => props.onPressDelete(props.hashGroup)},
    ]);
    
  };

  useEffect(() => {
    setLoadImage(true);
    loadImage(appCtx.settingsDestinationURL+'/productimageversion/get-image?imageHash='+props.hash+'&ts='+Date.now());
  }, [props.hash, props.refreshTs]);


  const loadImage = async (photo_reference) => {
    let attempt = 0;

      try {
        for (attempt; attempt < 3; attempt++){
          let response = await fetch(`${photo_reference}`, {method: 'GET'})
          if (response?.status === 200){
            setResCode(response?.status)
            setImagePlace(response?.url);
            break;
          // } else {
          //   console.log('attempt: ' + attempt );
          }
        }
      } catch (error) {
        console.error(error);
      } 
      finally {
        setLoadImage(false);
      }
    };


    
    return (
<>
<View style={styles.rowContainer}>

      {appCtx.isDebugMode === true ?
      <View>
        <Text>res: {resCode}</Text>
        <Text>url: {imagePlace}</Text>
        <Text>refres: {props.refreshTs}</Text>
      </View>
      :<View></View>}
      </View>
        

        <View style={styles.rowContainer}>
          {!isLoadImage ? (
            <View>
              <View style={styles.rowContainer}>
                <View style={styles.columnContainer}>

                {imagePlace ? (
                  <TouchableOpacity
                      onPress={handleZoomClick} 
                      style={styles.touchable} >
                      <ImageBackground source={{ uri: imagePlace }}  resizeMode="contain" style={styles.image}>
                      </ImageBackground>
                    </TouchableOpacity>
                ) : (<View><Text>Nie mogę pobrać obrazka.</Text></View>)}



                  </View >
                </View>
                
                <View style={styles.rowContainer}>
                  <Text>
                    {props.comment}
                  </Text>
                </View> 
                <View style={styles.rowContainer}>
                  <View style={styles.pressableIcon}>
                    <TouchableOpacity onPress={props.onPress}  >
                      <Icon name="edit" size={40} color="#900" />
                    </TouchableOpacity>
                  </View>
                  <View style={styles.pressableIcon}>
                    <TouchableOpacity onPress={handlePosUpClick}  >
                      <Icon name="level-up" size={40} color="#900" />
                    </TouchableOpacity>
                  </View>
                  <View style={styles.pressableIcon}>
                    <TouchableOpacity onPress={handleDeleteClick}  >
                      <Icon name="trash" size={40} color="#900" />
                    </TouchableOpacity>
                  </View>
                </View> 
              </View>
            ) : (
              <View><ActivityIndicator size='large'/></View>
            )} 
          </View>
</>
      
    )
}

const styles = StyleSheet.create({
  touchable: {
    alignItems: 'center',
    justifyContent: 'center'

  },
  image: {
    flex: 1,
    justifyContent: 'center',
    width: moderateScale(600), 
    height: moderateScale(700), 
  },
  view: {
    position: 'absolute',
    backgroundColor: 'transparent'
  },
  rowContainer: {

    marginBottom: moderateScale(30),
    flexDirection: 'row',
    justifyContent: 'center'
  },
  text: {
    ctintColor: Colors.redThemeColor,
    fontSize: moderateScale(70),
    fontSize: 18,
    fontWeight: 'bold',
    textAlign: 'center'
    
  },
  pressableIcon: {
    flexDirection: 'row',
    width: moderateScale(100),
    fontWeight: 'bold',
    textAlign: 'center',
    borderColor: 'red',
    justifyContent: 'center',
  },


  centeredView: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  modalView: {
    margin: 20,
    backgroundColor: 'white',
    borderRadius: 20,
    padding: 35,
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


export default CustomImage