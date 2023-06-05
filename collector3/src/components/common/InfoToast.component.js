import { Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import AppContext from '../../store/AppContext';
import React, { useContext, useEffect } from "react";
import Toggle from "./Toggle.component";

export type InfoToastProps = {
  title: string;
  onPress: () => void;
};
export const InfoToast = ({ title, onPress }: InfoToastProps) => {

  const appCtx = useContext(AppContext);

  // On componentDidMount set the timer
  useEffect(() => {
    const timeId = setTimeout(() => {
      appCtx.setToastInfoValue(null);
    }, 5000);

    return () => {
      clearTimeout(timeId);
    }
  }, [appCtx.toastInfoValue]);

  function handleClick() {
    appCtx.setToastInfoValue(null);
  }

  errorStyle = () => {
    return {
      borderColor: '#C80815',
      backgroundColor: '#FC7676',
      color: '#C80815',
    }
  }
  infoStyle = () => {
    return {
      borderColor: '#1f89ce',
      backgroundColor: '#dfe4e6',
      color: '#1f89ce',
    }
  }

  // const style = appCtx.toastInfoTypeValue == 'info' ? 'info' : 'error';
  return (
    <View>
      {appCtx.toastInfoValue && <View
        style={[styles.view, appCtx.toastInfoTypeValue ==='error' ? errorStyle() : infoStyle()]}
      >
        <Text style={[styles.text, appCtx.toastInfoTypeValue ==='error' ? errorStyle() : infoStyle()]}>
          {appCtx.toastInfoValue}
        </Text>

        {/*<TouchableOpacity*/}
        {/*  style={styles.button}*/}
        {/*  onPress={handleClick}>*/}
        {/*  <Text style={styles.buttonText}>Zamknij</Text>*/}
        {/*</TouchableOpacity>*/}
      </View>}
    </View>
  );
};


const styles = StyleSheet.create({
    view: {
      borderWidth: 1,
      borderColor: '#1f89ce',
      backgroundColor: '#dfe4e6',
      color: '#1f89ce',
      minHeight: 40,
      alignContent: 'center',
      verticalAlign: 'middle',
      textAlign: 'center',
    },
  text: {
    fontSize: 14,
    lineHeight: 36,
    },
  info: {
    borderColor: '#1f89ce',
    backgroundColor: '#dfe4e6',
    color: '#1f89ce',
  },
  error: {
    borderColor: '#C80815',
    backgroundColor: '#FC7676',
    color: '#C80815',
  },
  button: {
    borderColor: '#1f89ce',
    backgroundColor: '#2399e5',
    minWidth: 40,
    alignContent: 'center',
    margin: 10,
    position: 'absolute',
    textAlignVertical: 'center',
    width: '94%',
    bottom: 0,
    },
  buttonText: {
    alignContent: 'center',
    borderWidth: 1,
    fontSize: 18,
    color: '#fff',
    minHeight:40,
    lineHeight: 36,
    textAlign: 'center',
  },

});
export default InfoToast;
