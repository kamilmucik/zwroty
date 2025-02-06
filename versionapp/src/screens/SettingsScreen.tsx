import React, { useContext, useState } from "react";
import { StyleSheet, View, KeyboardAvoidingView} from 'react-native';
import  AsyncStorage  from '@react-native-async-storage/async-storage';
import AppContext from "../store/AppContext";
import PackageJson from '../../package';
import GlobalStyle from "../utils/GlobalStyle";
import { useSafeAreaInsets } from 'react-native-safe-area-context';
import { BASE_API_URL } from '../config.tsx';
import { Button, InputText , InputSwitch, InputSelect }  from '../components/Form';
import { MainDepartments } from '../store/MainDepartments';


const useSettingsFormState = () => {
  const appCtx = useContext(AppContext);
  const [fastQuizDepartment, setFastQuizDepartment] = useState(appCtx.settingsFastQuizDepartment);
  const [correctOnly, setCorrectOnly] = useState(appCtx.settingsShowCorrectAnswerOnly);
  const [submit, setSubmit] = useState(false);

  let isFastQuizDepartmentValid = false;
  let isCorrectOnlyValid = false;

  async function saveData(key, value) {
    await AsyncStorage.setItem(key,value);
  }

  return {
    fastQuizDepartment: {
      value: fastQuizDepartment,
      set: setFastQuizDepartment,
      valid: isFastQuizDepartmentValid
    },
    correctOnly: {
      value: correctOnly,
      set: setCorrectOnly,
      valid: isCorrectOnlyValid
    },
    submit: {
      value: submit,
      set: () => {
        setSubmit(true);
        appCtx.setSettingsOnlyCorrectValue(correctOnly);
        appCtx.setSettingsFastQuizDepartment(fastQuizDepartment);
        //TODO: prepare service
        saveData('@storage_lkequiz3',  JSON.stringify({
          correct: correctOnly,
          fastQuizDepartment: fastQuizDepartment
          }));
      },
      valid: true
    }
  }
}
//https://www.youtube.com/watch?v=VuNPrFH9H0E&t=1s
const SettingsScreen = () => {
  const insets = useSafeAreaInsets();
  const {correctOnly, fastQuizDepartment, submit} = useSettingsFormState();

  return (
    <KeyboardAvoidingView style={[GlobalStyle.AppContainer, GlobalStyle.AppScreenViewBackgroundColor,{
      paddingTop: insets.top,
      paddingBottom: insets.bottom,
      alignItems: 'center',
    }]}
     behavior="position">
      <View style={{flex: 1,width: '100%', padding: 5}}>
        <InputText
            label="Wersja"
            description={PackageJson.version} />

        <InputText
            label="API"
            description={BASE_API_URL}/>

        <Button
            text="Zapisz"
            testID="SettingsScreen.SubmitButton"
            onPress={submit.set} />
      </View>
    </KeyboardAvoidingView>
  );
};

const styles = StyleSheet.create({
});
export default SettingsScreen;
