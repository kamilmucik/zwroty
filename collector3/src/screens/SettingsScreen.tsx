import React, { useState, useEffect, useContext } from "react";
import { StyleSheet,Text, View, TextInput,SafeAreaView, ScrollView,TouchableOpacity } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import AppContext from "../store/AppContext";
import GlobalStyle from "../utils/GlobalStyle";
import { InfoToast } from "../components/common/InfoToast.component";

const SettingsScreen = ({navigation, route}) => {


  const appCtx = useContext(AppContext);
  const appCtxSettingsURLValue = appCtx.settingsURLValue;
  const appCtxSettingsPortValue = appCtx.settingsPortValue;
  const appCtxSettingsInstanceValue = appCtx.settingsInstanceValue;
  const appCtxSettingsOperatorValue = appCtx.settingsOperatorValue;

  const [loading, setLoading] = useState(false);
  const [sourceUrl, setSourceUrl] = useState(appCtxSettingsURLValue);
  const [sourcePort, setSourcePort] = useState(appCtxSettingsPortValue);
  const [sourceCollectorNo, setSourceCollectorNo] = useState(appCtxSettingsInstanceValue);
  const [sourceOperatorName, setSourceOperatorName] = useState(appCtxSettingsOperatorValue);
  const [debugInfo, setDebugInfo] = useState('');

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

    saveProperties();

    appCtx.setToastInfoValue('Zapisałem zmiany', 'info');
    setLoading(false);
  }

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
          </View>


          <TouchableOpacity
            activeOpacity={0.9}
            onPress={() => saveSettings()}
            style={[GlobalStyle.AppButton]}>
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

export default SettingsScreen;
