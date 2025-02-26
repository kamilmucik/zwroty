/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import React from 'react';
import { StatusBar } from 'expo-status-bar';

import RootNavigator from './src/navigation';
import SplashScreen from 'react-native-splash-screen';
// import FlashMessage from "react-native-flash-message";

export default function App() {
  React.useEffect(() => {
      SplashScreen.hide();
}, []);

  return (
    <>
      <RootNavigator />
      {/* <StatusBar style="auto" /> */}

      {/* <FlashMessage position="top" />  */}
    </>
  );
}