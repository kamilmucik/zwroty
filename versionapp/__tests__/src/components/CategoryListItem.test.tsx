import 'react-native';
import React from 'react';
import { render, screen, fireEvent, cleanup } from '@testing-library/react-native';
import CategoryListItem from '../../../src/components/CategoryListItem.tsx';

// Note: import explicitly to use the types shiped with jest.
import {it} from '@jest/globals';
// Note: test renderer must be required after react-native.
import renderer from 'react-test-renderer';


const item = {
    "id": 0,
    "name": "name",
    "code": "code",
    "time_limit": 15,
    "question_limit": 10,
    "max_question_limit": 10
}

describe('<CategoryListItem />', () => {
  afterEach(cleanup)

  it('renders correctly', () => {
    renderer.create(<CategoryListItem item={item} />);
  });
  

  it('calls onPress event to triger onPress when details is true', () => {
    const onPressItemMock = jest.fn()

    const { getByTestId } = render(<CategoryListItem item={item} details={true} onPress={onPressItemMock} />)

    fireEvent.press(getByTestId('categoryListItemTestID-0'))
    expect(onPressItemMock.mock.calls.length);
  });

  it('calls onPress event to triger onPress when details is false', () => {
    const onPressItemMock = jest.fn()

    const { getByTestId } = render(<CategoryListItem item={item} details={false} onPress={onPressItemMock} />)

    fireEvent.press(getByTestId('categoryListItemTestID-0'))
    expect(onPressItemMock.mock.calls.length);
  });
});
