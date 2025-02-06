import * as React from 'react';
import { View, TouchableOpacity, Text, StyleSheet} from 'react-native';

interface CustomButtonProps {
    text: string;
    style: string;
    primary: boolean;
    onPress: () => void;
}

const CustomButton = (props : CustomButtonProps) => {

    const handleClick = () => {
        props.onPress();
    };
    
    return (
        <View style={props.style}>
            <TouchableOpacity
                testID={'customButtonTestID'}
                activeOpacity={0.9}
                onPress={handleClick}
                style={[styles.button,  props.primary ? styles.primaryButton : styles.secoundaryButton, {marginTop: 24}]}>
                <Text style={[props.primary ? styles.primaryButtonText : styles.secoundaryButtonText]}>{props.text}</Text>
            </TouchableOpacity>
        </View>
    )
}

const styles = StyleSheet.create({
    button: {
        borderWidth: 1,
        justifyContent: 'center',
        alignItems: 'center',
        width:'100%',
        height: 48,
        borderRadius: 4,
        alignContent: 'center',
    },
    primaryButtonText: {
        color: '#ffffff'
    },
    primaryButton: {
        borderColor: '#1f89ce',
        backgroundColor: '#2399e5',
    },
    secoundaryButton: {
        width:'100%',
        borderColor: '#1f89ce',
    },
    secoundaryButtonText: {

    },
});

export default CustomButton