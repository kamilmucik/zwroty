import React, { useContext, useState } from "react";
import {View, ScrollView} from 'react-native';
import  AsyncStorage  from '@react-native-async-storage/async-storage';
import AppContext from "../../store/AppContext";
import PackageJson from '../../../package.json';
import { Button, InputText , InputTextField, InputSwitch }  from '../../components/Form.tsx';
import { showMessage } from "react-native-flash-message";
import styles from './SettingsSheetStyles';


const useSettingsFormState = () => {
  const appCtx = useContext(AppContext);
  const [destinationURL, setDestinationURL] = useState(appCtx.settingsDestinationURL);
  const [isDebugMode, setDebugMode] = useState(appCtx.isDebugMode);
  const [submit, setSubmit] = useState(false);

  let destinationURLValid = false;
  let isDebugModeValid = false;

  async function saveData(key, value) {
    await AsyncStorage.setItem(key,value);
  }

  return {
    destinationURL: {
      value: destinationURL,
      set: setDestinationURL,
      valid: destinationURLValid
    },
    isDebugMode: {
      value: isDebugMode,
      set: setDebugMode,
      valid: isDebugModeValid
    },
    submit: {
      value: submit,
      set: () => {
          setSubmit(true);
          appCtx.setSettingsDestinationURL(destinationURL);
          appCtx.setDebugMode(isDebugMode);
          
          saveData('@storage_versions2',  JSON.stringify({
            destinationURL: destinationURL,
            isDebugMode: isDebugMode
          }));

          showMessage({
            message: "Ustawienia zostały aktualizowane",
            type: "info",
            statusBarHeight: 40
        });
      },
      valid: true
    }
  }
}

const SettingsScreen = () => {
  const { destinationURL, isDebugMode, submit} = useSettingsFormState();

  return (
    <ScrollView  >
      <View  style={styles.mainContainer}>
        <View >
          <InputText
              label="Wersja"
              description={PackageJson.version} />
          <InputTextField 
            label="API Url" 
            onChange={destinationURL.set} 
            value={destinationURL.value}
            />
          <InputSwitch 
            description="Tryb debug" 
            onChange={isDebugMode.set} 
            value={isDebugMode.value}
            />
          
          <Button
              text="Zapisz"
              testID="SettingsScreen.SubmitButton"
              onPress={submit.set} />
        </View>
      </View>
    </ScrollView>
  );
};

export default SettingsScreen;
