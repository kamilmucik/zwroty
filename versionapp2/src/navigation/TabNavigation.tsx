import * as React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';

import HomeStackScreen from './HomeStack';

import ScanScreen from '../screens/version/ScanScreen';
import OCRTestScreen from '../screens/ocr/OCRTestScreen';
import SettingsScreen from '../screens/setting/SettingsScreen';
import GlobalStyle from "../utils/GlobalStyle";


const Tab = createBottomTabNavigator();

const screenOptions = (iconName, color) => {
  return <MaterialCommunityIcons name={iconName} color={color} size={24} />
 };

const TabNavigator = ({route, navigation}) => {

  return (
    <Tab.Navigator 
      initialRouteName="HomeStack" 
      activeColor="#18376e"
      sceneContainerStyle={GlobalStyle.AppContainer}
      screenOptions={{ 
        activeTintColor: '#18376e',
        headerShown: false,
        unmountOnBlur: true
        }}>
        <Tab.Screen 
          name="HomeStack" 
          component={HomeStackScreen} 
          options={{
            tabBarLabel: 'Home',
            tabBarIcon: ({color}) => screenOptions('scan-helper', color),
          }} 
          
          />
        <Tab.Screen 
          name="OCR" 
          component={OCRTestScreen} 
          options={{
            tabBarLabel: 'OCR',
            tabBarIcon: ({color}) => screenOptions('ocr', color),
          }}/>
        <Tab.Screen 
          name="Ustawienia" 
          component={SettingsScreen} 
          options={{
            tabBarLabel: 'Ustawienia',
            tabBarIcon: ({color}) => screenOptions('cog', color),
          }}/>
      </Tab.Navigator>
  );
};

export default TabNavigator;