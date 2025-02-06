import 'react-native';
import React from 'react';
import { render, screen, fireEvent, cleanup } from '@testing-library/react-native';
import CustomNavButton from '../../../src/components/CustomNavButton.tsx';

// Note: import explicitly to use the types shiped with jest.
import {it} from '@jest/globals';
// Note: test renderer must be required after react-native.
import renderer from 'react-test-renderer';

jest.mock("react-native-vector-icons/FontAwesome", () => "FontAwesome");
// jest.mock("react-native-vector-icons/lib/create-icon-set", () => jest.fn());

describe('<CustomNavButton />', () => {
  afterEach(cleanup)

  it('renders correctly', () => {    
    renderer.create(<CustomNavButton name='name' color='red' size={24} />);
  });
  
});
