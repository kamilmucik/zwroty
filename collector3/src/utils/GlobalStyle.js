import {StyleSheet} from "react-native";

export default StyleSheet.create({
  AppContainer: {
    flex: 1
  },
  AppButton: {
    borderColor: '#1f89ce',
    backgroundColor: '#2399e5',
    minWidth: 50,
    alignContent: 'center',
    margin: 10,
  },
  AppButtonText: {
    color: '#fff',
    textAlign: 'center',

    fontSize: 20,
    lineHeight: 46,
  },
  AppScrollView: {
    flex: 1,
    marginHorizontal: 8,
  },
  AppInputSection: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderBottomWidth: 0.5,
    borderColor: '#000',
    height: 40,
    margin: 10,
  },
  AppSection: {

    margin: 10,
  },
  AppInput: {
    color: 'black',
    flex: 5,
    fontSize: 14,
    textAlign: 'center',
    borderColor: 'black',
  },
  AppSelectButton: {
    backgroundColor: '#dfe4e6',
    borderBottomColor: '#1f89ce',
    borderBottomWidth: 1,
    borderTopColor: '#1f89ce',
    borderTopWidth: 1,
  },
  AppSelectButtonText: {

  },
})
