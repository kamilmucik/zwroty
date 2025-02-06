import * as React from 'react';
import { View,Text, StyleSheet, TouchableOpacity} from 'react-native';

interface CustomRadioButtonProps {
    label: string;
    correct: boolean;
    showScore: boolean;
    selected: boolean;
    onSelect: () => void;
}

const CustomRadioButton = (props : CustomRadioButtonProps) => {

    const handleClick = () => {
        props.onSelect();
    };
    
    return (
        <View style={[styles.answerRow, props.correct && props.showScore ? styles.correctAnswer : styles.badAnswer]} >
            <TouchableOpacity 
                testID={'customRadioButtonTestID'}
                disabled={props.showScore}
                style={[styles.radioButton,{ backgroundColor: props.selected ? '#2db2ff' : '#FFF' }]} 
                onPress={handleClick} 
            > 
            <Text style={[styles.radioButtonText, { color: props.selected ? 'white' : 'grey' }]}> 
              {props.label} 
            </Text> 
        </TouchableOpacity> 
      </View>
    )
}

const styles = StyleSheet.create({
    
  radioButton: {
    padding:2,
    minHeight:50, 
    flexDirection: 'row', 
    alignItems: 'center', 
    justifyContent: 'space-between', 
    width: '100%', 
  }, 
  radioButtonText: { 
      fontSize: 12, 
      margin: 4,
  }, 
  answerRow: {
    flexDirection: 'row', 
    alignItems: 'center', 
    justifyContent: 'space-between', 
    width: '100%', 
  },
  correctAnswer: {
    borderColor: 'green',
    borderLeftWidth: 8,
  },
  badAnswer: {

  }
});

export default CustomRadioButton