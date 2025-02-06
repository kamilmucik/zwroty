import 'react-native';
import React from 'react';
import { render, screen, fireEvent, cleanup } from '@testing-library/react-native';
import CustomButton from '../../../src/components/CustomButton.tsx';

// Note: import explicitly to use the types shiped with jest.
import {it} from '@jest/globals';
// Note: test renderer must be required after react-native.
import renderer from 'react-test-renderer';


describe('<CustomButton />', () => {
  afterEach(cleanup)

  it('renders correctly', () => {
    renderer.create(<CustomButton />);
  });
  

  it('calls onPress event to triger onPress', () => {
      const onPressItemMock = jest.fn()

      const { getByTestId } = render(<CustomButton text='text' primary={true} onPress={onPressItemMock} />)

      fireEvent.press(getByTestId('customButtonTestID'))
      expect(onPressItemMock.mock.calls.length);
  });
});
