import React, { useRef, useState } from "react";
import { StyleSheet, Image, TouchableOpacity, Text, Modal, View, TextInput, Pressable } from "react-native";
import NumericPad from "react-native-numeric-pad";
import GlobalStyle from "../../utils/GlobalStyle";

export type NumericPadModalProps = {
  title: string;
  titleHeader: string;
  minWidth: string;
  onPress: () => void;
  onModalTest: () => void;
};

export const NumericPadModal = ({ title, titleHeader, innerMinWidth, initVal, currentVal,returnValue, onPress, onModalTest }: NumericPadModalProps) => {

  const [isVisible, setVisible] = useState(false);
  const [amount, setAmount] = useState(0);
  const numpadRef = useRef(null);

  const [innerTitle, setInnerTitle] = useState('');
  const [innerTitleHeader, setInnerTitleHeader] = useState('');
  const [innerAmount, setInnerAmount] = useState('');


  displayModal = (show, titleHeader,title, initVal, onPress) => {
    // console.log("displayModal: " +  show );
    // this.setState({isVisible: show});
    setVisible(show);
    setInnerTitleHeader(titleHeader);
    setInnerTitle(title);
  
  };

  const innerMinWidth2 = innerMinWidth == 'small' ? '30%' : '48%';

  return (

    <View style={[{
        minHeight: 50,
      alignContent: 'center',
      width: innerMinWidth2,
      minWidth: '30%',
      maxWidth: '50%'}]}>
      <Modal
        animationType = {"fade"}
        style={styles.bottomModal}
        transparent = {false}
        visible = {isVisible}
        onModalTest={onModalTest}
        onRequestClose = {() =>{ 
          console.log("Modal has been closed." + innerTitleHeader);
          setVisible(false);
           } }>
        <View style = {styles.modal}>
          <View style={styles.shadowBox}>
            <Text style={styles.textStyle}>{innerTitleHeader} </Text>
          </View>

          <View style={styles.shadowBox}>
            <TextInput
              style={styles.amountTxt}
              showSoftInputOnFocus={false}
              maxLength={8}
              autoFocus={true}
              editable={false}
              selectTextOnFocus={false}
              value={amount}
            />
            <TouchableOpacity style={styles.amountButton} 
              // onRequestClose={ () => onModalTest()}
              onPress={() => {
                  console.log('amount: ' + Number(amount));
                  // if ( amount.lenght )

                onPress(innerTitle, Number(amount));
                setInnerAmount(Number(amount));
                displayModal(!isVisible);
                setVisible(!isVisible);
            }}>
              <Text style={styles.amountButtonText}>OK</Text>
            </TouchableOpacity>
          </View>
          <View style={styles.keyboardContainer}>
            <NumericPad
              ref={numpadRef}
              numLength={8}
              buttonSize={60}
              activeOpacity={0.1}
              onValueChange={value => setAmount(value)}
              allowDecimal={false}
              // Try them to understand each area :)
              // style={{ backgroundColor: 'black', paddingVertical: 12 }}
              // buttonAreaStyle={{ backgroundColor: 'gray' }}
              // buttonItemStyle={{ backgroundColor: 'red' }}
              rightBottomButton={<Text>Cofnij</Text>}
              onRightBottomButtonPress={() => {numpadRef.current.clear()}
              }
            />
          </View>
        </View>


        {/*/////////////*/}
      </Modal>
      {/* <TouchableOpacity
        style={[styles.boxInline, styles.boxInlineBlue, {minWidth: innerMinWidth2}]}
        onPress={() => {
          console.log('klik: ' + isVisible);
          displayModal(true, titleHeader, title, initVal, onPress, onModalTest);
        }}>
        <Text >{title} {currentVal}</Text>
      </TouchableOpacity> */}
      <Pressable
        style={[styles.boxInline, styles.boxInlineBlue, {minWidth: innerMinWidth2}]}
        onPress={() => {
          console.log('klik: ' + isVisible);
          displayModal(true, titleHeader, title, initVal, onPress, onModalTest);
        }}>
        <Text >{title} {currentVal}</Text>
      </Pressable>

    </View>
  );
};

const styles = StyleSheet.create({
  boxInline: {
    borderTopWidth: 1,
    borderBottomWidth: 1,
    fontSize: 16,
    flexDirection: 'row',
    minHeight: 50,
    minWidth: 58,
    justifyContent: 'center',
    alignItems: 'center',
    marginHorizontal: 8,
  },
  boxInlineBlue: {
    backgroundColor: '#E4F0F5',
    borderTopColor: '#3F87A6',
    borderBottomColor: '#3F87A6',
  },
  boxInlineRed: {
    backgroundColor: '#FFE7E8',
    borderTopColor: '#E66465',
    borderBottomColor: '#E66465',
  },
  shadowBox: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  amountTxt: {
    color: 'black',
    textAlign: 'center',
    fontSize: 38,
    fontWeight: '700',
    lineHeight: 40,
    borderBottomWidth: 1,
    width: '70%',
    alignItems: 'center',
    justifyContent: 'center',
  },
  amountButton: {
    width: '30%',
  },
  amountButtonText: {
    textAlign: 'center',
    fontSize: 38,
    fontWeight: '700',
    lineHeight: 40,
    justifyContent: 'center',
    alignItems: 'center',
  },
});
export default NumericPadModal;
