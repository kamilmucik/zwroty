import React, { useState, useEffect, useContext } from "react";
import { StyleSheet,Text, View, TextInput,SafeAreaView, ScrollView,TouchableOpacity } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import AppContext from "../store/AppContext";

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
    // ✅ Successful assignment - no error
    const str = await AsyncStorage.setItem(key,value);
    // const str = await AsyncStorage.setItem('key','value1234');
    // console.log("saveProperties: " + key + " : " + value);

    return str;
  }
  const saveProperties = () => {
    try {
      console.log("saveProperties: " );

      // saveData('key', sourcePort);
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

    // AsyncStorage.setItem('@storage_Key', sourceUrl)
    // appCtx.setScanPrintValue(val);
    appCtx.setSettingsURLValue(sourceUrl);
    appCtx.setSettingsPortValue(sourcePort);
    appCtx.setSettingsInstanceValue(sourceCollectorNo);
    appCtx.setSettingsOperatorValue(sourceOperatorName);

    saveProperties();

    setDebugInfo("Zapisałem zmiany");
    setLoading(false);
  }

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.scrollView}>
        <View style={{flex: 1}}>
          <View >
            <Text>URL serwera</Text>
            <TextInput
              style={styles.input}
              placeholder='Domyślny url serwera'
              value={sourceUrl}
              onChangeText={(text) => setSourceUrl(text)}
            />
            <Text>Port serwera</Text>
            <TextInput
              style={styles.input}
              placeholder='Port'
              value={sourcePort}
              onChangeText={(text) => setSourcePort(text)}
            />
            <Text>Numer kolektora</Text>
            <TextInput
              style={styles.input}
              placeholder='Domyślny Numer kolektora'
              value={sourceCollectorNo}
              onChangeText={(text) => setSourceCollectorNo(text)}
            />
            <Text>Operator</Text>
            <TextInput
              style={styles.input}
              placeholder='Domyślny Operator'
              value={sourceOperatorName}
              onChangeText={(text) => setSourceOperatorName(text)}
            />
          </View>


          <TouchableOpacity
            activeOpacity={0.9}
            onPress={() => saveSettings()}
            style={styles.loadMoreBtn}>
            <Text style={styles.btnText}>Zapisz</Text>
            {loading ? (
              // <ActivityIndicator
              //   color="white"
              //   style={{marginLeft: 8}} />
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
  container: {
    flex: 1,
  },
  scrollView: {
    flex: 1,
    marginHorizontal: 8,
  },
  sectionStyle: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderWidth: 0.5,
    borderColor: '#000',
    height: 40,
    borderRadius: 5,
    margin: 10,
  },
  input: {
    borderBottomWidth: 1,
    backgroundColor: '#fff',
    color: 'black',
    flex: 5,
    fontSize: 14,
    textAlign: 'center',
    borderColor: 'black'
  },
  buttonStyle:{
    color: 'white',
    fontSize: 17,
    textAlign: 'center',
  },

  loadMoreBtn: {
    margin: 10,
    padding: 14,
    backgroundColor: '#3740ff',
    borderRadius: 4,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
  },
  btnText: {
    color: 'white',
    fontSize: 17,
    textAlign: 'center',
  },
  error: {
    color: 'red',
    // alignSelf: 'center'
  }
});

export default SettingsScreen;
