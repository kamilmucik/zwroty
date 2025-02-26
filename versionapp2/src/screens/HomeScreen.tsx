import React, { useEffect, useState } from "react";
import { StyleSheet, Text } from "react-native";
import GlobalStyle from "../utils/GlobalStyle";
import { useSafeAreaInsets } from 'react-native-safe-area-context';
import { QUIZ_ID, PAGE_SIZE } from '../config';
import CustomList from '../components/CustomList';
import {useCustomFetch} from '../hooks/useCustomFetch'
import HomeMenuTile from '../components/HomeMenuTile';

const HomeScreen = ({ navigation }) => {
  

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
