import 'react-native';
import React from 'react';
import QuestionListItem from '../../../src/components/QuestionListItem.tsx';

// Note: import explicitly to use the types shiped with jest.
import {it} from '@jest/globals';

// Note: test renderer must be required after react-native.
import renderer from 'react-test-renderer';

item = {
    code: 'code',
    question: 'question',
    answers: [
        {id: 1, correct: 0, answer: 'answer'},
        {id: 2, correct: 1, answer: 'answer2'},
    ]
}

it('renders correctly', () => {
  renderer.create(<QuestionListItem item={item} />);
});
