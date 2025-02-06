import React from 'react';
import { render, cleanup, fireEvent, waitFor, act } from '@testing-library/react-native';
import SettingsScreen from '../../../src/screens/SettingsScreen';
import  AsyncStorage  from '@react-native-async-storage/async-storage';

// // jest.mock("@react-native-async-storage/async-storage", () => "AsyncStorage");
jest.mock("react-native-select-dropdown", () => "SelectDropdown");
// https://www.youtube.com/watch?v=VuNPrFH9H0E&t=1s
// https://www.youtube.com/watch?v=_F9A7LqipRw

//https://www.tiaeastwood.com/how-to-mock-and-test-asyncstorage-in-react-native
describe("<SettingsScreen />", () => {
  beforeEach(() => {
    AsyncStorage.clear();
    // console.log(`after the data is being reset :`)
    // console.log(AsyncStorage)
});

  afterEach(cleanup);

  it("render default elements", async () => {
    const {getAllByText, debug} = render(<SettingsScreen />)

    expect(getAllByText("Zapisz").length).toBe(1);
  }); 

  //change InputSwitch value
  //change InputSelect value
  //press submit and save in storage
  it("press submit and save in storage", async () => {

    await AsyncStorage.setItem('@storage_lkequiz3', JSON.stringify({
      correct: 1,
      fastQuizDepartment: ""
      } ))
    // let usernamevalue = await AsyncStorage.getItem('username')

    const {getByTestId, debug} = render(<SettingsScreen />)

    // fireEvent.press(submitButton);
    await act(async () => {
      const submitButton = await waitFor(() => getByTestId('SettingsScreen.SubmitButton'));

      fireEvent.press(submitButton);
    });

    // debug();
  });


  // it("press submit and save in storage", async () => {


  //   const {getByTestId, findByText, debug} = render(<SettingsScreen />)

  //   await act(async () => {
  //     const selectDropDown = await waitFor(() => getByTestId('selectDropdownTestId'));
  //     // const display = selectDropDown.children[0];

  //     // fireEvent.press(selectDropDown);
  //     fireEvent.scroll(selectDropDown, { key: "ArrowDown" });
  //     const existingItem = await findByText('PPL(A) skrócony')

  //     // const dropdownOptions = getAllByRole(selectDropDown, 'option');
  //     console.log(existingItem);
  //   });

  //   debug();
  // });

});