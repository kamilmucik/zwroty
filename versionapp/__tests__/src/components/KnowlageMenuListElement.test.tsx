import 'react-native';
import React from 'react';
import { render, screen, fireEvent, cleanup } from '@testing-library/react-native';
import KnowlageMenuListElement from '../../../src/components/KnowlageMenuListElement.tsx';

// Note: import explicitly to use the types shiped with jest.
import {it} from '@jest/globals';
// Note: test renderer must be required after react-native.
import renderer from 'react-test-renderer';


describe('<KnowlageMenuListElement />', () => {
  afterEach(cleanup)

  it('renders correctly', () => {
    renderer.create(<KnowlageMenuListElement />);
  });
  

  it('calls onPress event to triger onPressItem with user as argument', () => {
      const onPressItemMock = jest.fn()
      const id=1;
      const name='name';

      const { getByTestId } = render(<KnowlageMenuListElement id={id} name={name} onPress={onPressItemMock} />)

      fireEvent.press(getByTestId('knowlageMenuListElementTestID'))
      expect(onPressItemMock.mock.calls.length);
  });
});
