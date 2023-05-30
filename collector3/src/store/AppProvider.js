import { useReducer, useState, useEffect, useContext } from "react";
import AsyncStorage from '@react-native-async-storage/async-storage';
import AppContext from "./AppContext";

function AppProvider({children}) {

  const [contextValue, setContextValue] = useState([]);
  const [scanPalletCounterValue, setContextScanPalletCounterValue] = useState(0);
  const [scanMultiperValue, setContextScanMultiperValue] = useState(1);
  const [scanCMValue, setContextScanCMValue] = useState(0);
  const [scanPrintValue, setContextScanPrintValue] = useState(0);
  const [scanStorageValue, setContextScanStorageValue] = useState(0);

  const [settingsURLValue, setContextSettingsURLValue] = useState('http://zwroty.e-strix.pl');
  const [settingsPortValue, setContextSettingsPortValue] = useState('801');
  const [settingsInstanceValue, setContextSettingsInstanceValue] = useState('1');
  const [settingsOperatorValue, setContextSettingsOperatorValue] = useState('');

  function setValue(val){
    setContextValue( (currentVal) => [...currentVal,val]);
  }
  function setScanPalletCounterValue(val){
    setContextScanPalletCounterValue( val);
  }
  function setScanMultiperValue(val){
    setContextScanMultiperValue( val);
  }
  function setScanCMValue(val){
    setContextScanCMValue( val);
  }
  function setScanPrintValue(val){
    setContextScanPrintValue( val);
  }
  function setScanStorageValue(val){
    setContextScanStorageValue( val);
  }
  function setSettingsURLValue(val){
    setContextSettingsURLValue( val);
  }
  function setSettingsPortValue(val){
    setContextSettingsPortValue( val);
  }
  function setSettingsInstanceValue(val){
    setContextSettingsInstanceValue( val);
  }
  function setSettingsOperatorValue(val){
    setContextSettingsOperatorValue( val);
  }

  const value = {
    contextValue: contextValue,
    setValue: setValue,
    scanMultiperValue: scanMultiperValue,
    scanCMValue: scanCMValue,
    scanPrintValue: scanPrintValue,
    scanStorageValue: scanStorageValue,
    scanPalletCounterValue: scanPalletCounterValue,
    settingsURLValue: settingsURLValue,
    settingsPortValue: settingsPortValue,
    settingsInstanceValue: settingsInstanceValue,
    settingsOperatorValue: settingsOperatorValue,

    setScanPalletCounterValue: setScanPalletCounterValue,
    setScanMultiperValue: setScanMultiperValue,
    setScanCMValue: setScanCMValue,
    setScanPrintValue: setScanPrintValue,
    setScanStorageValue: setScanStorageValue,
    setSettingsURLValue: setSettingsURLValue,
    setSettingsPortValue: setSettingsPortValue,
    setSettingsInstanceValue: setSettingsInstanceValue,
    setSettingsOperatorValue: setSettingsOperatorValue,
  }

  return <AppContext.Provider value={value}>
      {children}
    </AppContext.Provider>
}
export default AppProvider;
