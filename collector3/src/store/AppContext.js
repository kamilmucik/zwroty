import React from 'react';

const AppContext = React.createContext({
  scanPalletCounterValue: 0,
  scanMultiperValue: 0,
  scanCMValue: 0,
  scanPrintValue: 0,
  scanStorageValue: 0,
  settingsURLValue: '',
  settingsPortValue: '80',
  settingsInstanceValue: '1',
  settingsOperatorValue: '',
  contextValue: [],
  setValue: (val) => {},
  setScanPalletCounterValue: (val) => {},
  setScanMultiperValue: (val) => {},
  setScanCMValue: (val) => {},
  setScanPrintValue: (val) => {},
  setScanStorageValue: (val) => {},
  setSettingsURLValue: (val) => {},
  setSettingsPortValue: (val) => {},
  setSettingsInstanceValue: (val) => {},
  setSettingsOperatorValue: (val) => {},
});

export default AppContext;