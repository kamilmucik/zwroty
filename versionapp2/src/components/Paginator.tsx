import { View, Text, Pressable, StyleSheet } from "react-native";
import Icon from 'react-native-vector-icons/FontAwesome';
import {
  moderateScale,Colors,
} from '../theme';

interface PaginatorProps {
    currentPage: number;
    totalItems: number;
    totalPages: number;
    pageSize: number;
    incPageAndLoad: (number) => void;
    decPageAndLoad: (number) => void;
}

const Paginator = ( props : PaginatorProps ) => {

    const handleIncPageAndLoadClick = () => {
        let page = props.currentPage + 1;
        if (page < props.totalPages){
            props.incPageAndLoad(page);
        }
    };
    const handleDecPageAndLoadClick = () => {
        let page = props.currentPage - 1;
        if (page >= 0){
            props.decPageAndLoad(page);
        }
    };

    return (
        <View style={styles.rowContainer}>
            <Pressable onPress={handleDecPageAndLoadClick}>
                <View>
                    <Icon name="arrow-left" size={58} color="#900" />
                </View>
            </Pressable>
            <Text style={styles.text}>{props.currentPage +1 } z {props.totalPages}({props.totalItems})</Text>
            <Pressable onPress={handleIncPageAndLoadClick}>
                <View>
                    <Icon name="arrow-right" size={58} color="#900" />
                </View>
            </Pressable>

        </View>
    )
};

export default Paginator;

const styles = StyleSheet.create({
rowContainer: {
    marginBottom: moderateScale(30),
    flexDirection: 'row',
    justifyContent: 'center',
    // borderBottomColor: '#e1f5fe',
    // borderBottomWidth: 1,
    // backgroundColor: '#e1f5fe',
  },
text: {
    // ctintColor: Colors.redThemeColor,
    fontSize: moderateScale(70),
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
    margin: 12
  },
    });