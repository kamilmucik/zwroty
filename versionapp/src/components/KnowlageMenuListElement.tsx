import * as React from 'react';
import { View, Text, Image, Pressable} from 'react-native';
import GlobalStyle from "../utils/GlobalStyle";
import airplane from '../assets/img/airplane.png';
import airplane2 from '../assets/img/airplane2.png';
import glider from '../assets/img/glider.png';

const tileImages = {
  1: airplane,
  5: glider,
  6: airplane2,
}

interface KnowlageMenuListElementProps {
    id: number;
    name: string;
    onPress: () => void;
}

const KnowlageMenuListElement = ({id, name, onPress} : KnowlageMenuListElementProps) => {

    const handleClick = () => {
        onPress(id,name);
    };
    
    return (
        <Pressable testID={`knowlageMenuListElementTestID-${id}`} style={[GlobalStyle.AppFlatListStyleItem]} onPress={handleClick} >
         <View style={[GlobalStyle.AppFlatListStyleItem,{ flexDirection: 'row' }]}>
            <Image source={tileImages[id]} style={[{ width: 48, height: 48, marginLeft:8 , marginTop:8  }]} />
            <Text style={[GlobalStyle.AppTextMainColor,{ fontSize: 18, paddingHorizontal: 12, verticalAlign:'middle', flex: 1}]} >
            {name}
            </Text>
        </View>
       </Pressable>
      
    )
}


export default KnowlageMenuListElement