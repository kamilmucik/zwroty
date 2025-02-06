import * as React from 'react';
import { View, Text} from 'react-native';

interface RowViewProps {
    leftText: string;
    rightText: string;
}

const RowView = (props : RowViewProps) => {
    
    return (
        <View style={{ flexDirection: 'row' }}>
            <View style={{ width: '25%'}}>
                <Text style={{ fontSize: 14}}>{props.leftText}</Text>
            </View>
            <View style={{ width: '75%'}}>
                <Text style={{ fontSize: 14, fontWeight: 'bold' , textAlign: 'left'}}>{props.rightText}</Text>
            </View>
        </View>
    )
}


export default RowView