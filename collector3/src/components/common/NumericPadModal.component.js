import React, { useRef, useState } from "react";
import { StyleSheet, Image, TouchableOpacity, Text, Modal, View, TextInput } from "react-native";
import NumericPad from "react-native-numeric-pad";

export type NumericPadModalProps = {
  title: string;
  titleHeader: string;
  minWidth: string;
  onPress: () => void;
};

export const NumericPadModal = ({ title, titleHeader, innerMinWidth, initVal, currentVal,returnValue, onPress }: NumericPadModalProps) => {

  const [isVisible, setVisible] = useState('');
  const [amount, setAmount] = useState(1);
  const numpadRef = useRef(null);

  const [innerTitle, setInnerTitle] = useState('');
  const [innerTitleHeader, setInnerTitleHeader] = useState('');
  const [innerAmount, setInnerAmount] = useState('');


  displayModal = (show, titleHeader,title, initVal, onPress) => {
    console.log("displayModal: " +  show );
    // this.setState({isVisible: show});
    setVisible(show);
    setInnerTitleHeader(titleHeader);
    setInnerTitle(title);
  };


  // _renderModalContent = (props) => (
  //   <View style = {styles.modal}>
  //     <View style={styles.shadowBox}>
  //       <Text style={styles.textStyle}>{props.titleHeader}</Text>
  //     </View>
  //
  //     <View style={styles.shadowBox}>
  //       <TextInput
  //         style={styles.amountTxt}
  //         showSoftInputOnFocus={false}
  //         maxLength={8}
  //         autoFocus={true}
  //         editable={false}
  //         selectTextOnFocus={false}
  //         value={amount}
  //       />
  //       <TouchableOpacity style={styles.amountButton} onPress={() => {
  //         displayModal(!isVisible);
  //         console.log("Modal has been closed." + props.title);
  //         // eanInputRef.current.focus();
  //       }}>
  //         <Text style={styles.amountButtonText}>OK</Text>
  //       </TouchableOpacity>
  //     </View>
  //     <View style={styles.keyboardContainer}>
  //       <NumericPad
  //         ref={numpadRef}
  //         numLength={8}
  //         buttonSize={60}
  //         activeOpacity={0.1}
  //         onValueChange={value => setAmount(value)}
  //         allowDecimal={false}
  //         // Try them to understand each area :)
  //         // style={{ backgroundColor: 'black', paddingVertical: 12 }}
  //         // buttonAreaStyle={{ backgroundColor: 'gray' }}
  //         // buttonItemStyle={{ backgroundColor: 'red' }}
  //         rightBottomButton={<Text>Cofnij</Text>}
  //
  //         onRightBottomButtonPress={() => {numpadRef.current.clear()}
  //         }
  //       />
  //     </View>
  //   </View>
  // );

  const innerMinWidth2 = innerMinWidth == 'small' ? 58 : 120;

  return (

    <View >
      <Modal
        animationType = {"fade"}
        style={styles.bottomModal}
        transparent = {false}
        visible = {isVisible}
        onRequestClose = {() =>{ console.log("Modal has been closed." + innerTitleHeader) } }>
        {/*{_renderModalContent(props)}*/}

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
            <TouchableOpacity style={styles.amountButton} onPress={() => {
              onPress(innerTitle, amount);
              setInnerAmount(amount);
              displayModal(false);
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
      <TouchableOpacity
        style={[styles.boxInline, styles.boxInlineBlue, {minWidth: innerMinWidth2}]}
        onPress={() => {
          displayModal(true, titleHeader, title, initVal, onPress);
        }}>
        <Text >{title} {currentVal}</Text>
      </TouchableOpacity>

    </View>
  );
};

const styles = StyleSheet.create({
  boxInline: {
    borderTopWidth: 1,
    borderBottomWidth: 1,
    fontSize: 16,
    flexDirection: 'row',
    minHeight: 56,
    // minWidth: {minWidth},
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
    // marginTop: LAYOUT['spacing-06'],
    // color: COLORS['brand-01']
  },
  amountButton: {
    // color: 'white',
    // fontSize: 6,
    // textAlign: 'center',
    // borderWidth: 1,
    width: '30%',

    // alignItems: 'center',
    // justifyContent: 'center',
  },
  amountButtonText: {
    textAlign: 'center',
    fontSize: 38,
    fontWeight: '700',
    lineHeight: 40,
    // borderRadius: 6,
    justifyContent: 'center',
    alignItems: 'center',
    // width: '50%',
    // backgroundColor: '#2AC062',
  },
});
export default NumericPadModal;
