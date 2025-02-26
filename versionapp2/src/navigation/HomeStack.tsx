import * as React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import ScanScreen from '../screens/version/ScanScreen';

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
    </HomeStack.Navigator>
  );
};

export default HomeStackNavigator;
