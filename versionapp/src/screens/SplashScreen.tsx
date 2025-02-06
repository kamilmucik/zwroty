import React, { useContext, useEffect } from "react";
import { View, Image, StyleSheet } from 'react-native';
import AppContext from "../store/AppContext";
import AsyncStorage from "@react-native-async-storage/async-storage";

const SplashScreen = () => {
  const appCtx = useContext(AppContext);

  async function loadProperties() {
    const value = await AsyncStorage.getItem('@storage_lkequiz3');
    let parsed = JSON.parse(value);
    if(value !== null && parsed !==null) {
      appCtx.setSettingsOnlyCorrectValue(parsed.correct);
      appCtx.setSettingsFastQuizDepartment(parsed.fastQuizDepartment);
    }
  }

useEffect(() => {
    loadProperties();
    }, []);

  return (
    <View style={styles.container}>
        <Image source={require('../assets/img/glider.png')} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});

export default SplashScreen;