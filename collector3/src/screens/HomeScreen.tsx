import { Button, View, Text, StyleSheet, SafeAreaView, ScrollView, TouchableOpacity } from "react-native";
import PackageJson from '../../package';
import React, { useContext, useEffect } from "react";
import AppContext from "../store/AppContext";
import AsyncStorage from "@react-native-async-storage/async-storage";
import GlobalStyle from "../utils/GlobalStyle";

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
  async function loadSettingsIsMobileData(key) {
    const value = await AsyncStorage.getItem(key);
    if(value !== null) {
      appCtx.setIsMobile(value);
    }
    return value;
  }
  async function loadSettingsIsDebugModeData(key) {
    const value = await AsyncStorage.getItem(key);
    if(value !== null) {
      appCtx.setIsDebugMode(value);
    }
    return value;
  }

  async function loadProperties() {
    try {
      console.log("loadProperties: " );
      appCtx.setIsMobile(0);
      appCtx.setIsDebugMode(0);
      loadSettingsURLData('@storage_sourceUrl');
      loadSettingsPortData('@storage_sourcePort');
      loadSettingsInstanceData('@storage_sourceCollectorNo');
      loadSettingsOperatorData('@storage_sourceOperatorName');
      loadSettingsIsMobileData('@storage_isMobile');
      loadSettingsIsDebugModeData('@storage_isDebugMode');
      appCtx.setScanPalletCounterValue(0);
      appCtx.setScanMultiperValue(0);
      appCtx.setToastInfoValue(null, 'info');


    } catch(e) {
      console.error(e)
    }
  }


  useEffect(() => {
    loadProperties();
  }, []);

  return (
    <SafeAreaView style={[GlobalStyle.AppContainer, styles.container]}>
      <Text style={styles.versionText}>v: {PackageJson.version} </Text>
      <ScrollView style={[GlobalStyle.AppScrollView]}>
        <View style={[GlobalStyle.AppContainer]}>
          <View >
            <TouchableOpacity
              activeOpacity={0.9}
              onPress={() => navigation.navigate('Shipments')}
              style={[GlobalStyle.AppButton]}>
              <Text style={[GlobalStyle.AppButtonText]}>Zwroty</Text>
            </TouchableOpacity>
          </View>
          <View >
            <TouchableOpacity
              activeOpacity={0.9}
              onPress={() => navigation.navigate('Settings', {name: 'Jane'})}
              style={[GlobalStyle.AppButton]}>
              <Text style={[GlobalStyle.AppButtonText]}>Ustawienia</Text>
            </TouchableOpacity>
          </View>
        </View>
      </ScrollView>
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    marginTop:40,
  },
  versionText: {
    color: 'gray',
    fontSize: 10,
    textAlign: 'right',
    position: 'absolute',
    bottom: 10,
  },
});

export default HomeScreen;
