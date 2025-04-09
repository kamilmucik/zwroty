import  React, {useContext}from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity} from 'react-native';
import { BASE_API_URL } from '../config.tsx';
import {
  moderateScale,
  horizontalScale,
  verticalScale
} from '../theme';
import AppContext from "../store/AppContext";

interface CustomImageProps {
    hash: string;
    label: string;
    onPress: any;
}

const CustomImage = (props : RowViewProps) => {

  const appCtx = useContext(AppContext);
    
    return (
        <View style={styles.rowContainer}>
            <TouchableOpacity
                onPress={props.onPress} >
                <Image
                  style={styles.image} 
                  resizeMode="contain"
                  source={{
                    uri: appCtx.settingsDestinationURL+'/productimageversion/get-image?imageHash='+props.hash
                  }}
                />
              </TouchableOpacity>
          </View>
    )
}

const styles = StyleSheet.create({
  image: {
    flex: 0.8,
    borderStyle: 'dashed',
    width: moderateScale(300), 
    height: moderateScale(300), 
  },
  rowContainer: {
    marginBottom: moderateScale(30),
    flexDirection: 'row',
    justifyContent: 'center',
  },
});


export default CustomImage