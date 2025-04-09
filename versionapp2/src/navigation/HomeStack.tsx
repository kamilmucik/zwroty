import * as React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import ScanScreen from '../screens/version/ScanScreen';
import ScanImageScreen from '../screens/version/ScanImageScreen';

const HomeStack = createNativeStackNavigator();

const HomeStackNavigator = () => {
  return (
    <HomeStack.Navigator screenOptions={{unmountOnBlur: true }}
>
    <HomeStack.Screen name="Scan" component={ScanScreen}
                      options={({ navigation, route }) => ({
                        headerShown: false,
                        title: '' ,
                      })}/>
    <HomeStack.Screen name="ScanImage" component={ScanImageScreen}
                        options={({ navigation }) => ({
                          title: 'Skanowanie'
                        })}/>
    </HomeStack.Navigator>
  );
};

export default HomeStackNavigator;
