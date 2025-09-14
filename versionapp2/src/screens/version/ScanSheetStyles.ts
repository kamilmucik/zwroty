import {Platform, StyleSheet} from 'react-native';
import {
  Colors,
  horizontalScale,
  moderateScale,
  verticalScale,
} from '../../theme';

const styles = StyleSheet.create({
  mainContainer: {
    flex: 1,
    margin: 10,
    marginBottom: moderateScale(20),
  },
  rowContainer: {
    marginTop: moderateScale(20),
    marginBottom: moderateScale(10),
    flexDirection: 'row',
    justifyContent: 'center',
    gap: horizontalScale(50),
  },
  image: {
    width: moderateScale(300), 
    height: moderateScale(300), 
  },
  imageBig: {
    // borderBlockColor: 'black', 
    // borderBottomWidth: 1,
    // flex: 1,
    backgroundColor: '#fff',
    width: moderateScale(1200), 
    height: moderateScale(1200), 
  },
  viewTest1: {
    // flex: 1,
    backgroundColor: '#e1f5fe',
    width: '100%',
    height: '100%',
  },
  viewTest2: {
    // flex: 1,
    backgroundColor: '#e1f5fe',
    // width: moderateScale(1200),
    // height: 'auto',
    height: moderateScale(1200),
  },
  eanTextInput: {
    color: 'black',
    flex: 5,
    fontSize: 14,
    textAlign: 'center',
    borderColor: 'black',

  },
  inputSection: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderBottomWidth: 0.5,
    borderColor: '#000',
    height: 40,
    margin: 10,
    width: "100%", 
    borderRadius: 4,
    // minHeight: 64,
    verticalAlign: 'middle',
    textAlignVertical: 'center',
  }
});

export default styles;
