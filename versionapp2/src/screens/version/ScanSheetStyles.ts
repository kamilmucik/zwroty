import {Platform, StyleSheet} from 'react-native';
import {
  Colors,
  horizontalScale,
  moderateScale,
  verticalScale,
} from '../../theme';

const styles = StyleSheet.create({
  rowContainer: {
    marginTop: moderateScale(20),
    marginBottom: moderateScale(10),
    flexDirection: 'row',
    justifyContent: 'center',
    gap: horizontalScale(50),
  },
});

export default styles;
