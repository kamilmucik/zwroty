import * as React from 'react';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';

import HomeStackScreen from './HomeStack';
import SettingsScreen from '../screens/SettingsScreen';
import GlobalStyle from "../utils/GlobalStyle";


const Tab = createBottomTabNavigator();

const screenOptions = (iconName, color) => {
  return <MaterialCommunityIcons name={iconName} color={color} size={24} />
 };

const TabNavigator = () => {

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
            tabBarIcon: ({color}) => screenOptions('home', color),
          }} 
          />
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