import React, { useEffect, useState } from "react";
import { StyleSheet, Image, TouchableOpacity, Text } from "react-native";

export type ToggleProps = {
  imgScr: string;
  valueCheck: string;
  valueUnCheck: string;
  initVal: string;
  val: string;
  isDisabled: string;
  onPress: () => void;
};
const Toggle  = ({ imgScr, valueCheck, valueUnCheck,initVal,val, onPress, isDisabled }: ToggleProps) => {

  const [isToggleOn, setToggleOn] = useState(initVal === 'true' ? true :false);

  function handleClick() {
    setToggleOn(!isToggleOn);
    onPress(isToggleOn?0:1);
  }

  return (
    <TouchableOpacity style={[styles.boxInline, val === 'true' ? styles.boxInlineRed : styles.boxInlineBlue , isDisabled === 'true'? styles.disabled : styles.enabled]}
                    disabled={isDisabled === 'true'? true : false}
                      onPress={handleClick}>
      {imgScr && <Image source={imgScr} style={{ width: 32, height: 32 }} />}
      {val === 'true' ? valueCheck : valueUnCheck}
    </TouchableOpacity>
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
  disabled: {
    backgroundColor: '#999999',
  },
  enabled: {

  }
}
);
export default Toggle;
