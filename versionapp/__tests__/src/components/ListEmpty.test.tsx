import 'react-native';
import React from 'react';

import { render, screen, fireEvent } from '@testing-library/react-native';
// import { render, cleanup, fireEvent } from 'react-native-testing-library';
import ListEmpty from '../../../src/components/ListEmpty.tsx';

// Note: import explicitly to use the types shiped with jest.
import {it} from '@jest/globals';
// Note: test renderer must be required after react-native.
import renderer from 'react-test-renderer';


describe('<ListEmpty />', () => {
  // afterEach(cleanup)

  it('renders correctly', () => {
    renderer.create(<ListEmpty />);
  });
  

  it('calls onPress event to triger onPressItem', () => {
      const onPressItemMock = jest.fn()
      // const user = {
      //     "id": "123",
      //     "profile_pic": "http://placekitten.com/g/200/300",
      //     "username": "Lelbil",
      //     "bio": "I am not a cat"
      // }

      const { getByTestId } = render(<ListEmpty onPress={onPressItemMock} />)

      fireEvent.press(getByTestId('listEmptyTestID'))
      expect(onPressItemMock.mock.calls.length);
      // expect(onPressItemMock).toHaveBeenCalledWith(user);
  });
});
