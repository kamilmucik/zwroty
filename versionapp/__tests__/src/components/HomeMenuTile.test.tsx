import 'react-native';
import React from 'react';
import { render, screen, fireEvent, cleanup } from '@testing-library/react-native';
import HomeMenuTile from '../../../src/components/HomeMenuTile.tsx';

// Note: import explicitly to use the types shiped with jest.
import {it} from '@jest/globals';
// Note: test renderer must be required after react-native.
import renderer from 'react-test-renderer';


describe('<HomeMenuTile />', () => {
  afterEach(cleanup)

  it('renders correctly', () => {
    renderer.create(<HomeMenuTile />);
  });
  

  it('calls onPress event to triger onPress', () => {
      const onPressItemMock = jest.fn()
      const id=1;
      const name='name';

      const { getByTestId } = render(<HomeMenuTile id={id} name={name} onPress={onPressItemMock} />)

      fireEvent.press(getByTestId('homeMenuTileTestID-1'))
      expect(onPressItemMock.mock.calls.length);
  });
});
