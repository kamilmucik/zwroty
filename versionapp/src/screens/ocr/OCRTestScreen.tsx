
import {useSafeAreaInsets} from "react-native-safe-area-context";
import React, {useEffect, useState} from "react";
import {Button, InputText} from "../../components/Form";
import {BASE_API_URL} from "../../config";
import {View} from "react-native";


const OCRTestScreen = ({ navigation }) => {
    const insets = useSafeAreaInsets();
    const [currentPage, setCurrentPage] = useState(1);


    return (
        <>
            <View style={{flex: 1,width: '100%', padding: 5}}>

                <InputText
                    label="API"
                    description={BASE_API_URL}/>

            </View>
        </>
    );
};

export default OCRTestScreen;
