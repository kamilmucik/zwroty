import React, {useContext, useEffect, useState} from 'react';
import {View, ScrollView} from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import AppContext from '../../store/AppContext';
import {
  Button,
  InputTextField,
} from '../../components/Form.tsx';
import {showMessage} from 'react-native-flash-message';
import styles from './ProfileSheetStyles';
import {useCustomPost} from '../../hooks/useCustomPost';

const useProfileFormState = () => {
  const appCtx = useContext(AppContext);
  const [author, setAuthor] = useState(appCtx.settingsAuthor);
  const [pin, setPin] = useState(appCtx.settingsPin);
  const [destinationURL, setDestinationURL] = useState(
    appCtx.settingsDestinationURL,
  );
  const [submit, setSubmit] = useState(false);
  const [loginData, setLoginData] = useState(null);
  const {loginResult} = useCustomPost(
    'user/login',
    loginData,
    'POST',
    'POST_LOGIN_SUCCESS',
  );


  let authorValid = false;
  let pinValid = false;

  async function saveData(key, value) {
    await AsyncStorage.setItem(key, value);
  }

  useEffect(() => {
    // console.log('loginResult: ' + JSON.stringify(loginResult));
    // console.log('warehouseIds: ' + loginResult?.warehouseIds);
      if (loginResult === null) return;

      if (loginResult?.message !== undefined) {
        showMessage({
          message: loginResult?.message,
          type: 'danger',
          position: 'top',
        });
      } else {
        appCtx.setSettingsWarehouse(loginResult?.warehouseIds);
        showMessage({
          message: 'Logowanie poprawne',
          type: 'info',
          position: 'top',
        });
      }

  }, [loginResult]);

  return {
    author: {
      value: author,
      set: setAuthor,
      valid: authorValid,
    },
    pin: {
      value: pin,
      set: setPin,
      valid: pinValid,
    },

    submit: {
      value: submit,
      set: () => {
        setSubmit(true);
        appCtx.setSettingsAuthor(author);
        appCtx.setSettingsPin(pin);
        setLoginData({
          username: author,
          pin: pin
        });

      },
      valid: true,
    },
  };
};

const ProfileScreen = () => {
  const {author, pin, submit} = useProfileFormState();

  return (
    <ScrollView>
      <View style={styles.mainContainer}>
        <View>
          <InputTextField label="Login" onChange={author.set} value={author.value} />
          <InputTextField label="PIN" onChange={pin.set} value={pin.value} />
          <Button
            text="Zaloguj"
            testID="ProfileScreen.SubmitButton"
            onPress={submit.set}
          />
        </View>
      </View>
    </ScrollView>
  );
};

export default ProfileScreen;
