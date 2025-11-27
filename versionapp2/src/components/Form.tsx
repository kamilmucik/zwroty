import { TouchableOpacity, View, Text, Switch, TextInput, StyleSheet } from "react-native"
import GlobalStyle from "../utils/GlobalStyle";

const InputBase = ({ ...props}) => {
  return (
      <View style={{ marginTop: 6}}>
          <View >
              <Text>{props.label}</Text>
          </View>
          <View style={[GlobalStyle.AppFlatListStyleItem,{minHeight: 48, flexDirection: 'row', alignContent: 'center', alignItems: "center"}]}>
                {props.children}
          </View>
      </View>
  )
}

const Input2Column = ({ ...props}) => {

  const child = ({...props}) =>{
    return (
      <>
        <View style={{ width: '75%'}}>
            <Text style={{ fontSize: 14,flex: 1, verticalAlign: 'middle', paddingLeft: 10}}>{props.description}</Text>
        </View>
        <View style={{ width: '25%'}}>
          {props.children}
        </View>
      </>
    );
  }

  return ( <InputBase {...props} children={child(props)} />)
}

const Input1Column = ({ ...props}) => {
  return ( <InputBase  {...props} />)
}

export const InputText = ({ label, description}) => {
  return (
      <Input2Column label={label} description={description} />
  )
}


export const InputTextField = ({ label, description, value, onChange}) => {
  const child = (value, onChange) =>{
    return (
      <TextInput
        placeholder={description}
        value={value}
        onChangeText={onChange}
      />
    );
  }

  return (
      <Input1Column label={label} description={description}  children={child(value,onChange)}/>
  )
}
export const InputTextArea = ({ label, description, value, onChange}) => {
    
    const child = (value, onChange) =>{
    return (
      <TextInput
          placeholder={description}
          editable
          multiline
          numberOfLines={2}
          maxLength={120}
          onChangeText={onChange}
          value={value}
          style={styles.textInput}
        />
    );
  }

  return (
      <Input1Column label={label} description={description}  children={child(value,onChange)}/>
  )
}

export const InputSwitch = ({ label, description, value, onChange}) => {
    const child = (value, onChange) =>{
      return (
       <Switch style={{marginRight: 10, transform:[{ scaleX: 1.2 }, { scaleY: 1.2 }]}} onValueChange={onChange} value={value} /> 
      );
    }
    return (
      <Input2Column label={label} description={description} children={child(value,onChange)} />
    )
}


export const Button = ({text, onPress, ...props}) => {
    return (
        <View >
            <TouchableOpacity {...props} onPress={onPress} style={[GlobalStyle.AppButton, GlobalStyle.AppPrimaryButton, {marginTop: 24}]}>
                <Text style={[GlobalStyle.AppPrimaryButtonText]}>{text}</Text>
            </TouchableOpacity>
        </View>
    )
}


const styles = StyleSheet.create({
  textInput: {
    padding: 4,
    // borderColor: '#000',
    // borderWidth: 1,
    margin: 12,
  },
});