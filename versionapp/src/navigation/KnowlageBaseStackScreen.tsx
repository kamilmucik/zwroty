import * as React from 'react';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import CategoriesScreen from '../screens/knowlage/CategoriesScreen';
import QuestionsScreen from '../screens/knowlage/QuestionsScreen';
import QuestionsBaseScreen from '../screens/knowlage/QuestionsBaseScreen';

const KnowlageBaseStack = createNativeStackNavigator();

const KnowlageBaseStackNavigator = () => {
  return (
    <KnowlageBaseStack.Navigator>
      <KnowlageBaseStack.Screen name="QuestionBase" component={QuestionsBaseScreen}
                        options={({ navigation, route }) => ({
                          headerShown: false,
                          title: 'Baza pytań',
                        })}/>
      <KnowlageBaseStack.Screen name="Categories" component={CategoriesScreen}
                        options={({ navigation, route }) => ({
                          headerShown: true,
                          title: '' + route.params.departmentName,
                          headerShadowVisible: false, // applied here
                          headerBackTitleVisible: false,
                        })}/>
      <KnowlageBaseStack.Screen name="Questions" component={QuestionsScreen}
                        options={({ navigation, route }) => ({
                          headerShown: true,
                          title: '' + route.params.categoryName,
                          headerShadowVisible: false, // applied here
                          headerBackTitleVisible: false,
                        })}/>
    </KnowlageBaseStack.Navigator>
  );
};

export default KnowlageBaseStackNavigator;