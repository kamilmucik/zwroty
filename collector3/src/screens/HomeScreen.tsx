import { Button, View, Text, StyleSheet, SafeAreaView, ScrollView } from 'react-native';
import PackageJson from '../../package';
import { useContext, useEffect } from "react";
import AppContext from "../store/AppContext";
import AsyncStorage from "@react-native-async-storage/async-storage";

const HomeScreen = ({ navigation }) => {

  const appCtx = useContext(AppContext);


  async function loadSettingsURLData(key) {
    const value = await AsyncStorage.getItem(key);
    if(value !== null) {
      appCtx.setSettingsURLValue(value);
    }
    return value;
  }
  async function loadSettingsPortData(key) {
    const value = await AsyncStorage.getItem(key);
    if(value !== null) {
      appCtx.setSettingsPortValue(value);
    }
    return value;
  }
  async function loadSettingsInstanceData(key) {
    const value = await AsyncStorage.getItem(key);
    if(value !== null) {
      appCtx.setSettingsInstanceValue(value);
    }
    return value;
  }
  async function loadSettingsOperatorData(key) {
    const value = await AsyncStorage.getItem(key);
    if(value !== null) {
      appCtx.setSettingsOperatorValue(value);
    }
    return value;
  }

  async function loadProperties() {
    try {
      console.log("loadProperties: " );
      loadSettingsURLData('@storage_sourceUrl');
      loadSettingsPortData('@storage_sourcePort');
      loadSettingsInstanceData('@storage_sourceCollectorNo');
      loadSettingsOperatorData('@storage_sourceOperatorName');
      appCtx.setScanPalletCounterValue(0);
      appCtx.setScanMultiperValue(0);

    } catch(e) {
      console.error(e)
    }
  }


  useEffect(() => {
    loadProperties();
  }, []);

  return (
    <SafeAreaView style={{flex: 1}}>
      <ScrollView style={styles.scrollView}>
        <View style={styles.container}>
          <Text style={styles.versionText}>v: {PackageJson.version} </Text>
          <View style={styles.sectionStyle}>
            <Button
              title="Zwroty"
              style={styles.button}
              onPress={() => navigation.navigate('Shipments')}
            />
          </View>
          <View style={styles.sectionStyle}>
            <Button
              title="Ustawienia"
              style={styles.button}
              onPress={() => navigation.navigate('Settings', {name: 'Jane'})}
            />
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  button: {
    backgroundColor: '#3740ff',
  },
  scrollView: {
    flex: 1,
    marginHorizontal: 8,
  },
  sectionStyle: {
    margin: 10,
  },
  versionText: {
    color: 'gray',
    fontSize: 10,
    textAlign: 'right',
  },
});

export default HomeScreen;
