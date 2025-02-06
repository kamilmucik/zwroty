import * as React from 'react';
import { View} from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';

interface CustomNavButtonProps {
    name: string;
    color: string;
    size: number;
}

const CustomNavButton = (props : CustomNavButtonProps) => {
    
    return (
        <View>
            <Icon name={props.name} color={props.color} size={props.size} />
        </View>
    )
}


export default CustomNavButton