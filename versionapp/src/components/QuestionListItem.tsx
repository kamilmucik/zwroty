import { View, Text, StyleSheet, FlatList } from "react-native";
import GlobalStyle from "../utils/GlobalStyle";


interface QuestionListItemProps {
    item: any;
}

const QuestionListItem = ({item} : QuestionListItemProps) => {

    const renderAnswerListItems = ({ item }) => {
        return (
          <View>
            {item.correct == 1 ? (
              <Text style={{fontWeight: 'bold',fontSize: 16, paddingHorizontal: 12, paddingVertical: 4, paddingBottom: 4 }}>{item.answer} </Text>
            ): (
              <Text style={{ fontSize: 10, paddingHorizontal: 12, paddingVertical: 4, paddingBottom: 4 }} >
              {item.answer}
              </Text>
            )}
          </View>
        );
      };

    return (
        <View style={[GlobalStyle.AppFlatListStyleItem]}>
            <Text style={[GlobalStyle.AppTextMainColor,{ fontSize: 8, paddingHorizontal: 12,  marginTop:10 }]} >
            {item.code}
            </Text>
            <Text style={[GlobalStyle.AppTextMainColor,{ fontSize: 18}]} >
            {item.question}
            </Text>
            <FlatList
                data={item.answers}
                renderItem={renderAnswerListItems}
                keyExtractor={itemAnswer => itemAnswer.id.toString()}
                />

      </View>
    )
};

const styles = StyleSheet.create({
    
  });

export default QuestionListItem;