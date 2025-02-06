import React from 'react';
import { render, fireEvent, cleanup } from '@testing-library/react-native';
import SplashScreen from '../../../src/screens/SplashScreen';
import AsyncStorage from "@react-native-async-storage/async-storage";

const items = {"correct": true, "fastQuizDepartment": {"code": 5, "text": "SPL", "value": "glider"}};

describe("<SplashScreen />", () => {

  beforeEach(async () => {
    await AsyncStorage.setItem('@storage_lkequiz3', items);
  });

  it("should render the spash screen when loaded propertie", async () => {
    JSON.parse = jest.fn().mockImplementationOnce(() => {
      return items;
    });

    render(<SplashScreen />);

    // const item1 = await screen.findByText("test item");
    // const falseItem = screen.queryByText("i shouldn't exist");
    // expect(item1).toBeTruthy();
    // expect(falseItem).toBeFalsy();
  });

  it("should render the spash screen ", async () => {
    JSON.parse = jest.fn().mockImplementationOnce(() => {
      return null;
    });

    render(<SplashScreen />);

    // const item1 = await screen.findByText("test item");
    // const falseItem = screen.queryByText("i shouldn't exist");
    // expect(item1).toBeTruthy();
    // expect(falseItem).toBeFalsy();
  });
});