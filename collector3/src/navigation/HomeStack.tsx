import * as React from 'react';
import { Text,TouchableOpacity } from 'react-native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import HomeScreen from '../screens/HomeScreen';
import ScanScreen from '../screens/ScanScreen';
import ShipmentsScreen from '../screens/ShipmentsScreen';
import SettingsScreen from '../screens/SettingsScreen';

const HomeStack = createNativeStackNavigator();

const HomeStackNavigator = () => {
  return (
    <HomeStack.Navigator>
      <HomeStack.Screen name="Home" component={HomeScreen}
                        options={({ navigation }) => ({
                          title: ''
                        })}/>
      <HomeStack.Screen name="Scan" component={ScanScreen}
                        options={({ navigation, route }) => ({
                          title: 'Zwrot: ' + route.params.retNumber,
                          headerRight: () => (
                            <TouchableOpacity onPress={() => navigation.navigate("Home")} >
                              <Text style={{color: 'black', marginRight: 10}}>Zakończ</Text>
                            </TouchableOpacity>
                          ),
                        })}/>
      <HomeStack.Screen name="Shipments" component={ShipmentsScreen}
                        options={({ navigation }) => ({
                          title: 'Zwroty'
                        })}/>
      <HomeStack.Screen name="Settings"
                        component={SettingsScreen}
                        options={({ navigation }) => ({
                          title: 'Ustawienia'
                        })} />
    </HomeStack.Navigator>
  );
};

export default HomeStackNavigator;