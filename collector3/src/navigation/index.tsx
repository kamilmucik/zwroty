import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';

import HomeStackNavigator from './HomeStack';
import AppProvider from '../store/AppProvider';

const RootNavigator = () => {
  return (
    <AppProvider>

      <NavigationContainer>
        <HomeStackNavigator />
      </NavigationContainer>
    </AppProvider>
  );
};

export default RootNavigator;
