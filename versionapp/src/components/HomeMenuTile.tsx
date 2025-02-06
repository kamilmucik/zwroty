import React from 'react'
import { StyleSheet, Text, View, Image,Dimensions, Pressable } from 'react-native';
import GlobalStyle from "../utils/GlobalStyle";
import quiz from '../assets/img/quiz.png';
import airplane from '../assets/img/airplane.png';
import airplane2 from '../assets/img/airplane2.png';
import glider from '../assets/img/glider.png';


const tileImages = {
    0: quiz,
    1: airplane,
    5: glider,
    6: airplane2,
}

interface HomeMenuTileProps {
    id: number;
    name: string;
    onPress: () => void;
}

const HomeMenuTile = ({id, name, onPress} : HomeMenuTileProps) => {
    const { width } = Dimensions.get("window");

    const calcTileDimensions = (deviceWidth, tpr) => {
        const margin = (deviceWidth / (tpr * 10));
        const size = ((deviceWidth - margin * (tpr * 2)) / tpr)-5;
        return { size, margin };
    };
    const tileDimensions = calcTileDimensions(width, 2) 

    const handleClick = () => {
        onPress(id,name);
    };

    return (
        <Pressable 
            testID={`homeMenuTileTestID-${id}`}
            onPress={handleClick}>
            <View style={[styles.item, {width: tileDimensions.size, height: tileDimensions.size, marginHorizontal: 8.}]}>
                <Image source={tileImages[id]} style={[{ width: '60%', height: '60%' }, styles.tileImg]} />
                <Text style={[GlobalStyle.AppTextMainColor]}>{name}</Text>
            </View>
        </Pressable>
    )
};

const styles = StyleSheet.create({
    item: {
      backgroundColor: '#ffffff',
       alignItems: 'center',
       justifyContent: 'center',
       marginBottom: 20,
       borderRadius: 4,
    },
    tileImg: {
      borderColor: '#1f89ce',
    }
  });

export default HomeMenuTile;