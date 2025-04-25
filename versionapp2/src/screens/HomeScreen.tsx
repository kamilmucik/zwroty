import React, { useEffect, useState } from "react";
import { StyleSheet, Text } from "react-native";
import GlobalStyle from "../utils/GlobalStyle";
import CustomList from '../components/CustomList';

const HomeScreen = ({ navigation }) => {

  async function loadProperties() {
    // const value = await AsyncStorage.getItem('@storage_lkequiz3');
    // let parsed = JSON.parse(value);
    console.log("load.destinationURL: " + JSON.stringify(parsed));
    // if(value !== null && parsed !==null) {
      appCtx.setSettingsDestinationURL("http://10.17.0.4:8080");
    // }
  }

useEffect(() => {
    loadProperties();
  }, []);
  

  return (
    <CustomList
      data={data}
      moreLoading={moreLoading}
      horizontal={true}
      numColumns={2}
      viewStyle={[GlobalStyle.AppContainer, GlobalStyle.AppScreenViewBackgroundColor,{
        paddingTop: insets.top,
        paddingBottom: insets.bottom,
        alignItems: 'center',
      }]}
      listStyle={[styles.tileList]}
      contentContainerStyle={[styles.tileListContent,GlobalStyle.AppScreenViewBackgroundColor]}
      displayItem={({item}) => renderItem(item)}
      />
  );
};

const styles = StyleSheet.create({
  tileList: {
  },
  tileListContent: {
    marginTop: 30,
  }
});

export default HomeScreen;
