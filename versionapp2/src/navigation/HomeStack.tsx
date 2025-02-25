import * as React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import HomeScreen from '../screens/ocr/HomeScreen';
import OCRTestScreen from '../screens/ocr/OCRTestScreen';

const HomeStack = createNativeStackNavigator();

const HomeStackNavigator = () => {
  return (
    <HomeStack.Navigator screenOptions={{unmountOnBlur: true }}
>
    <HomeStack.Screen name="Home" component={HomeScreen}
                      options={({ navigation, route }) => ({
                        headerShown: false,
                        title: '' ,
                      })}/>
    <HomeStack.Screen name="OCRTest" component={OCRTestScreen}
                      options={({ navigation, route }) => ({
                        headerShown: false,
                        title: '' ,
                      })}/>
    </HomeStack.Navigator>
  );
};

export default HomeStackNavigator;
