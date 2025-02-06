import { Text, StyleSheet, Pressable } from "react-native";
import GlobalStyle from "../utils/GlobalStyle";


interface CategoryListItemProps {
    item: any;
    details: bolean;
    onPress: () => void;
}

const CategoryListItem = ({item, details, onPress} : CategoryListItemProps) => {

    const handleClick = () => {
        onPress(item);
    };

    return (
        <Pressable testID={`categoryListItemTestID-${item.id}`} style={[GlobalStyle.AppFlatListStyleItem]} onPress={handleClick}  >
            <Text style={[GlobalStyle.AppTextMainColor,{ fontSize: 18, paddingHorizontal: 12,  marginTop:10, verticalAlign:'middle', flex: 1 }]} >
            {item.name}
            </Text>
            {details ?
            <Text style={{ fontSize: 12, paddingHorizontal: 12, paddingVertical: 4, paddingBottom: 4, marginBottom:4}} >
                {item.code} | {item.time_limit}(min) | {item.question_limit} z {item.max_question_limit} pytań
            </Text>
            : 
            <Text style={{ fontSize: 12, paddingHorizontal: 12, paddingVertical: 4, paddingBottom: 4, marginBottom:4}} >
                {item.code} | {item.max_question_limit} pytań
            </Text>
            }
      </Pressable>
    )
};

const styles = StyleSheet.create({
    
  });

export default CategoryListItem;