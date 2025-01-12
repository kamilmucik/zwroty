import React, { useState, useEffect, useContext } from "react";
import { StyleSheet,Text, View, TextInput,SafeAreaView, ScrollView,TouchableOpacity,Switch } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import AppContext from "../store/AppContext";
import GlobalStyle from "../utils/GlobalStyle";
import { InfoToast } from "../components/common/InfoToast.component";
import SelectDropdown from 'react-native-select-dropdown'

const SettingsScreen = ({navigation, route}) => {


  const appCtx = useContext(AppContext);
  const appCtxSettingsURLValue = appCtx.settingsURLValue;
  const appCtxSettingsPortValue = appCtx.settingsPortValue;
  const appCtxSettingsInstanceValue = appCtx.settingsInstanceValue;
  const appCtxSettingsOperatorValue = appCtx.settingsOperatorValue;
  const appCtxSettingsIsMobile = appCtx.isMobile;
  const appCtxSettingsIsDebugMode = appCtx.isDebugMode;
  const appCtxSettingsPrinterValue = appCtx.settingsPrinterValue;

  const [loading, setLoading] = useState(false);
  const [sourceUrl, setSourceUrl] = useState(appCtxSettingsURLValue);
  const [sourcePort, setSourcePort] = useState(appCtxSettingsPortValue);
  const [sourceCollectorNo, setSourceCollectorNo] = useState(appCtxSettingsInstanceValue);
  const [sourceOperatorName, setSourceOperatorName] = useState(appCtxSettingsOperatorValue);
  const [sourcePrinterName, setSourcePrinterName] = useState(appCtxSettingsPrinterValue);
  const [debugInfo, setDebugInfo] = useState('');
  const [switchValue, setSwitchValue] = useState(appCtxSettingsIsMobile ==='true');
  const [isDebugModeSwitchValue, setIsDebugModeSwitchValue] = useState(appCtxSettingsIsDebugMode ==='true');
  const [printers, setPrinters] = useState([]);

  useEffect(() => {
    appCtx.setToastInfoValue('Drukarka: ' + sourcePrinterName, 'info');
    getPrinters();
  }, []);


  const getPrinters = async () => {
    const response = await fetch(''+appCtx.settingsURLValue+':'+appCtx.settingsPortValue +'/print/list')
      .then((response) => response.json())
      .then(responseData => { return responseData;})
      .then((data) => {
        if (data.status === 500){
          appCtx.setToastInfoValue('Nie mogę pobrać listy drukarek.', 'info');
        } else {
          setPrinters(data);
        }
      })
      .catch((error) => {
        // console.error(error);
        appCtx.setToastInfoValue('Nie można pobrac danych! Możliwy problem z siecią internet.', 'error');
      })
      // .finally(() => setLoading(false))
    ;
    // setDefaultFocus();
  }

  async function saveData(key, value) {
    const str = await AsyncStorage.setItem(key,value);

    return str;
  }
  const saveProperties = () => {
    try {
      saveData('@storage_sourceUrl', sourceUrl);
      saveData('@storage_sourcePort', sourcePort);
      saveData('@storage_sourceCollectorNo', sourceCollectorNo);
      saveData('@storage_sourceOperatorName', sourceOperatorName);
      saveData('@storage_sourcePrinterName', sourcePrinterName);
      saveData('@storage_isMobile', switchValue?'true':'false');
      saveData('@storage_isDebugMode', isDebugModeSwitchValue?'true':'false');
    } catch(e) {
      console.error(e)
    }
  }

  const saveSettings = typ => {
    setLoading(true);

    appCtx.setSettingsURLValue(sourceUrl);
    appCtx.setSettingsPortValue(sourcePort);
    appCtx.setSettingsInstanceValue(sourceCollectorNo);
    appCtx.setSettingsOperatorValue(sourceOperatorName);
    appCtx.setSettingsPrinterValue(sourcePrinterName);
    appCtx.setIsMobile(switchValue?'true':'false');
    appCtx.setIsDebugMode(isDebugModeSwitchValue?'true':'false');

    saveProperties();

    appCtx.setToastInfoValue('Zapisałem zmiany', 'info');
    setLoading(false);
  }

  const toggleSwitch = (value) => {
    setSwitchValue(value);
  };
  const isDebugModeSwitch = (value) => {
    setIsDebugModeSwitchValue(value);
  };

  return (
    <SafeAreaView style={[GlobalStyle.AppContainer]}>
      <InfoToast></InfoToast>
      <ScrollView style={[GlobalStyle.AppScrollView]}>
        <View style={{flex: 1}}>
          <View >
            <Text>URL serwera</Text>
            <View style={[GlobalStyle.AppInputSection]}>
              <TextInput
                style={[GlobalStyle.AppInput]}
                placeholder='Domyślny url serwera'
                value={sourceUrl}
                onChangeText={(text) => setSourceUrl(text)}
              />
            </View>
            <Text>Port serwera</Text>
            <View style={[GlobalStyle.AppInputSection]}>
              <TextInput
                style={[GlobalStyle.AppInput]}
                placeholder='Port'
                keyboardType='numeric'
                value={sourcePort}
                onChangeText={(text) => setSourcePort(text)}
              />
            </View>
            <Text>Numer kolektora</Text>
            <View style={[GlobalStyle.AppInputSection]}>
              <TextInput
                style={[GlobalStyle.AppInput]}
                placeholder='Domyślny Numer kolektora'
                keyboardType='numeric'
                value={sourceCollectorNo}
                onChangeText={(text) => setSourceCollectorNo(text)}
              />
            </View>
            <Text>Operator</Text>
            <View style={[GlobalStyle.AppInputSection]}>
              <TextInput
                style={[GlobalStyle.AppInput]}
                placeholder='Domyślny Operator'
                value={sourceOperatorName}
                onChangeText={(text) => setSourceOperatorName(text)}
              />
            </View>
            <Text>Drukarka</Text>
            <View style={[GlobalStyle.AppInputSection]}>
              <SelectDropdown
                  buttonTextStyle={[GlobalStyle.AppInput]}
                  buttonStyle={[GlobalStyle.AppInput]}
                  data={printers}
                  onSelect={(selectedItem, index) => { 
                    setSourcePrinterName(selectedItem.name);
                    console.log(selectedItem, index); 
                  }}
                  renderCustomizedRowChild={(item, index) => {
                    return (
                      <View style={{...styles.dropdownItemStyle}}>
                        <Text>{item?.name}</Text>
                      </View>
                    );
                  }}
                  buttonTextAfterSelection={(selectedItem, index) => {
                    return sourcePrinterName;
                  }}
                  defaultValueByIndex={1}
                  defaultValue={sourcePrinterName}
                  defaultButtonText={sourcePrinterName}
                  
                />
            </View>

            <View style={[{flexDirection: 'row'}]}>
              <Text style={{marginTop: 14}}>Czy telefon </Text>
              <Switch
                style={{marginTop: 10}}
                onValueChange={toggleSwitch}
                value={switchValue}
              />
            </View>
            <View style={[{flexDirection: 'row'}]}>
              <Text style={{marginTop: 14}}>Czy tryb debug </Text>
              <Switch
                style={{marginTop: 10}}
                onValueChange={isDebugModeSwitch}
                value={isDebugModeSwitchValue}
              />
            </View>
          </View>

          <TouchableOpacity
            activeOpacity={0.9}
            onPress={() => saveSettings()}
            style={[GlobalStyle.AppButton, {marginTop: 24}]}>
            <Text style={[GlobalStyle.AppButtonText]}>Zapisz</Text>
            {loading ? (
              <Text>Loading...</Text>
            ) : null}
          </TouchableOpacity>
          <Text>
            {debugInfo}
          </Text>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};


const styles = StyleSheet.create({
  dropdownButtonStyle: {
    width: 200,
    height: 50,
    backgroundColor: '#E9ECEF',
    borderRadius: 12,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    paddingHorizontal: 12,
  },
  dropdownButtonTxtStyle: {
    flex: 1,
    fontSize: 18,
    fontWeight: '500',
    color: '#151E26',
  },
  dropdownButtonArrowStyle: {
    fontSize: 28,
  },
  dropdownButtonIconStyle: {
    fontSize: 28,
    marginRight: 8,
  },
  dropdownMenuStyle: {
    backgroundColor: '#E9ECEF',
    borderRadius: 8,
  },
  dropdownItemStyle: {
    width: '100%',
    flexDirection: 'row',
    paddingHorizontal: 12,
    justifyContent: 'center',
    alignItems: 'center',
    paddingVertical: 8,
  },
  dropdownItemTxtStyle: {
    flex: 1,
    fontSize: 18,
    fontWeight: '500',
    color: '#151E26',
  },
  dropdownItemIconStyle: {
    fontSize: 28,
    marginRight: 8,
  },
});

export default SettingsScreen;
