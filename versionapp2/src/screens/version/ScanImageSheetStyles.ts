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
  buttonWrapper: {
    // flex: 1,
    
    flexDirection: 'row',
    // paddingBottom: Platform.OS === 'ios' ? verticalScale(30) : 0,
    // alignItems: 'flex-end',
    justifyContent: 'center',
  },
  button: {
    borderWidth: moderateScale(2),
    borderColor: Colors.white,
    borderRadius: moderateScale(50),
    padding: moderateScale(10),
    alignItems: 'center',
    justifyContent: 'center',
    width: moderateScale(100),
    height: moderateScale(100),
  },

  image: {
    width: moderateScale(300), 
    height: moderateScale(300), 
  },
  icon: {
    tintColor: Colors.white,
    fontSize: moderateScale(70),
    width: '90%',
    height: '90%',
  },
  rowContainer: {
    marginTop: moderateScale(30),
    marginBottom: moderateScale(30),
    flexDirection: 'row',
    justifyContent: 'center',
    gap: horizontalScale(50),
  },
  titleResult: {
    fontSize: moderateScale(20),
    paddingTop: moderateScale(15),
    paddingHorizontal: moderateScale(20),
    color: Colors.black,
    fontWeight: '500',
  },
  textStyleWarn: {
    color: 'red',
    fontSize: 24,
  }
});

export default styles;
