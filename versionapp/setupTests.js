import fetchMock from "jest-fetch-mock";

import mockasyncstorage from '@react-native-async-storage/async-storage/jest/async-storage-mock';


jest.mock('@react-native-async-storage/async-storage', () => mockasyncstorage);


jest.mock('react-native-safe-area-context', () => {
  const inset = {top: 0, right: 0, bottom: 0, left: 0};
  return {
    ...jest.requireActual('react-native-safe-area-context'),
    SafeAreaProvider: jest.fn(({children}) => children),
    SafeAreaConsumer: jest.fn(({children}) => children(inset)),
    useSafeAreaInsets: jest.fn(() => inset),
    useSafeAreaFrame: jest.fn(() => ({x: 0, y: 0, width: 390, height: 844})),
  };
});


fetchMock.enableMocks();
