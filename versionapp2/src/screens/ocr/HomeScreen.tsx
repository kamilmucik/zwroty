import React, { useEffect, useState } from "react";
import { StyleSheet, Text } from "react-native";
import GlobalStyle from "../../utils/GlobalStyle";
import { useSafeAreaInsets } from 'react-native-safe-area-context';
import { QUIZ_ID, PAGE_SIZE } from '../../config.tsx';
import CustomList from '../../components/CustomList';
import {useCustomFetch} from '../../hooks/useCustomFetch.tsx'
import HomeMenuTile from '../../components/HomeMenuTile';

const HomeScreen = ({ navigation }) => {
  const insets = useSafeAreaInsets();
  const [currentPage, setCurrentPage] = useState(1);

  const [query, setQuery] = useState('');
  const [moreLoading, setMoreLoading] = useState('');
  const [data, setData] = useState([
      {"id": 0, "name": "OCRTest"},
      {"id": 1, "name": "Skanuj"}
  ]);
  // const {moreLoading, data} = useCustomFetch(query, true);
  // const {moreLoading, data} = useCustomFetch(query, true, [{"id": 0, "name": "3 szybkie"}]);

  const fetchDepartments = async (page) => {
    setQuery(`department/${QUIZ_ID}/${page}/${PAGE_SIZE}/`);
  }

  useEffect(() => {
    fetchDepartments(currentPage);
  }, [currentPage]);

  useEffect(() => {
    setCurrentPage(1);
  }, []);

  const onPressItemHandler = (id, name) => {
      if (id == 0){
        navigation.navigate('OCRTest', {
          quizCategoryName: '3 szybkie',
          quizCategoryId: 0,
          quizTimeLimit: 5,
          quizQuestionLimit: 3
        })
      }else{
        navigation.navigate('Category', {
          departmentId: id,
          departmentName: name,
        })
      }
  };

  const renderItem = (item) => {
    return <HomeMenuTile id={item.id} name={item.name} onPress={ onPressItemHandler } />
  }

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
