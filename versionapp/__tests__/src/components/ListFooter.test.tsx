import 'react-native';
import React from 'react';
import ListFooter from '../../../src/components/ListFooter.tsx';

// Note: import explicitly to use the types shiped with jest.
import {it} from '@jest/globals';

// Note: test renderer must be required after react-native.
import renderer from 'react-test-renderer';

it('renders correctly', () => {
  renderer.create(<ListFooter />);
});

it('renders correctly with indicator', () => {
    renderer.create(<ListFooter loading={true} />);
  });