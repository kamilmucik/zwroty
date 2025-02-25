import * as React from 'react';
import { View, ActivityIndicator } from 'react-native';

interface ListFooterProps {
    loading: boolean;
}

const ListFooter = ({loading}: ListFooterProps) => {

    return (
        <View >
         {loading && <ActivityIndicator size='large'/>}
        </View>
    )
}


export default ListFooter