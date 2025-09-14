import  React, {useContext, useState, useEffect}from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, ActivityIndicator, ImageBackground, Pressable} from 'react-native';
import {
  moderateScale,Colors,
} from '../theme';
import AppContext from "../store/AppContext";
import { BASE_API_URL } from '../config.tsx';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Modal } from 'react-native-paper';
import ImageViewer from 'react-native-image-zoom-viewer';

interface CustomImageProps {
    hash: string;
    label: string;
    refreshTs: string;
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
  const [imageView, setImageView] = useState([]);
  const [modalVisible, setModalVisible] = useState(false);

  const handleZoomClick = () => {
    props.onPressZoom(BASE_API_URL+'/productimageversion/get-image?imageHash='+props.hash+'&ts='+Date.now());
  };
  const handlePosUpClick = () => {
    props.onPressPosUp(BASE_API_URL+'/productimageversion/get-image?imageHash='+props.hash+'&ts='+Date.now());
  };
  const handleDeleteClick = () => {
    props.onPressDelete(BASE_API_URL+'/productimageversion/get-image?imageHash='+props.hash+'&ts='+Date.now());
  };

  useEffect(() => {
    setLoadImage(true);
    loadImage(BASE_API_URL+'/productimageversion/get-image?imageHash='+props.hash+'&ts='+props.refreshTs);
  }, [props.hash, props.refreshTs]);


  const loadImage = async (photo_reference) => {
      try {
        let response = await fetch(`${photo_reference}`, {method: 'GET'})
        setResCode(response?.status)
        setImagePlace(response?.url);
        
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
        <Text>url: {props.refreshTs}</Text>
      </View>
      :<View></View>}
      </View>
        

        <View style={styles.rowContainer}>
          {isLoadImage && 
          <View>
            <ActivityIndicator size='large'/>
          </View>}
          {!isLoadImage ? (
            <View>
              <View style={styles.rowContainer}>
                <View style={styles.columnContainer}>

                  <TouchableOpacity
                      onPress={handleZoomClick} 
                      style={styles.touchable} >
                      <ImageBackground source={{ uri: imagePlace }}  resizeMode="contain" style={styles.image}>
                      </ImageBackground>
                    </TouchableOpacity>
                  </View >
                </View>
                
                <View style={styles.rowContainer}>
                  <View style={styles.pressableIcon}>
                    <TouchableOpacity onPress={props.onPress}  >
                      <Icon name="edit" size={40} color="#900" />
                    </TouchableOpacity>
                  </View>
                  <View style={styles.pressableIcon}>
                    <TouchableOpacity onPress={handlePosUpClick}  >
                      <Icon name="arrow-up" size={40} color="#900" />
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
              <Text>{imagePlace}</Text>
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
    width: moderateScale(400), 
    height: moderateScale(500), 
  },
  view: {
    position: 'absolute',
    backgroundColor: 'transparent'
  },
  rowContainer: {
    marginBottom: moderateScale(30),
    flexDirection: 'row',
    justifyContent: 'center',
    backgroundColor: '#e1f5fe',
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