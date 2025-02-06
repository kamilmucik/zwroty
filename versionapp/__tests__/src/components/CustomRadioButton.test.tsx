import 'react-native';
import React from 'react';

import { render, cleanup, fireEvent } from '@testing-library/react-native';
// import { render, cleanup, fireEvent } from 'react-native-testing-library';
import CustomRadioButton from '../../../src/components/CustomRadioButton.tsx';

// Note: import explicitly to use the types shiped with jest.
import {it} from '@jest/globals';
// Note: test renderer must be required after react-native.
import renderer from 'react-test-renderer';


describe('<CustomRadioButton />', () => {
  afterEach(cleanup)

  it('renders correctly', () => {
    renderer.create(<CustomRadioButton />);
  });

  
  it('calls onSelect event to triger onPressItem when all flags are true', () => {
      const onPressItemMock = jest.fn()
      const params = {
          "label": "123",
          "correct": true,
          "showScore": true,
          "selected": true,
      }

      const { getByTestId } = render(<CustomRadioButton 
            label={params.label} 
            correct={params.correct}
            showScore={params.showScore}
            selected={params.selected}
            onSelect={onPressItemMock} 
            />)

      fireEvent.press(getByTestId('customRadioButtonTestID'))
      expect(onPressItemMock.mock.calls.length);
  });

    it('calls onSelect event to triger onPressItem when correct is false', () => {
        const onPressItemMock = jest.fn()
        const params = {
            "label": "123",
            "correct": false,
            "showScore": true,
            "selected": true,
        }

        const { getByTestId } = render(<CustomRadioButton 
            label={params.label} 
            correct={params.correct}
            showScore={params.showScore}
            selected={params.selected}
            onSelect={onPressItemMock} 
            />)

        fireEvent.press(getByTestId('customRadioButtonTestID'))
        expect(onPressItemMock.mock.calls.length);
    });

    it('calls onSelect event to triger onPressItem when showScore is false', () => {
        const onPressItemMock = jest.fn()
        const params = {
            "label": "123",
            "correct": true,
            "showScore": false,
            "selected": true,
        }

        const { getByTestId } = render(<CustomRadioButton 
            label={params.label} 
            correct={params.correct}
            showScore={params.showScore}
            selected={params.selected}
            onSelect={onPressItemMock} 
            />)

        fireEvent.press(getByTestId('customRadioButtonTestID'))
        expect(onPressItemMock.mock.calls.length);
    });

    it('calls onSelect event to triger onPressItem when selected is false', () => {
        const onPressItemMock = jest.fn()
        const params = {
            "label": "123",
            "correct": true,
            "showScore": true,
            "selected": false,
        }

        const { getByTestId } = render(<CustomRadioButton 
            label={params.label} 
            correct={params.correct}
            showScore={params.showScore}
            selected={params.selected}
            onSelect={onPressItemMock} 
            />)

        fireEvent.press(getByTestId('customRadioButtonTestID'))
        expect(onPressItemMock.mock.calls.length);
    });

});
