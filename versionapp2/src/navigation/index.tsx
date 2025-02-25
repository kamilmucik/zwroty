import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { useState,useEffect } from 'react';
import TabNavigator from './TabNavigation';
import AppProvider from '../store/AppProvider';
// import FlashMessage from "react-native-flash-message";

const RootNavigator = () => {

  return (
    <AppProvider>
      <NavigationContainer>
        <TabNavigator />

      </NavigationContainer>
      {/* <FlashMessage position="bottom" />  */}
    </AppProvider>
  );
};

export default RootNavigator;