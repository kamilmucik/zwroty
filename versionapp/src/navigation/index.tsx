import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import SplashScreen from '../screens/SplashScreen';
import { useState,useEffect } from 'react';
import TabNavigator from './TabNavigation';
import AppProvider from '../store/AppProvider';

const RootNavigator = () => {

  const [isLoading, setIsLoading] = useState(true);
  useEffect(() => {
    // Simulate loading process
    setTimeout(() => {
      setIsLoading(false);
    }, 2000); // Adjust the time as needed
  }, []);

  return (
    <AppProvider>
      <NavigationContainer>
      {isLoading ? <SplashScreen /> :
        <TabNavigator />
  }

      </NavigationContainer>
    </AppProvider>
  );
};

export default RootNavigator;