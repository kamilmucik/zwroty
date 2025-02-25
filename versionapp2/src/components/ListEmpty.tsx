import { View, Text, Pressable } from "react-native";

interface ListEmptyProps {
    onPress: () => void;
}

const ListEmpty = ({onPress} : ListEmptyProps) => {

    const handleClick = () => {
        onPress();
    };

    return (
      <View>
        <Pressable testID={'listEmptyTestID'} onPress={handleClick}>
          <Text style={{ fontSize: 16, paddingHorizontal: 12,  marginTop:10 }} >
            Odświez
          </Text>
        </Pressable>
        <Text>Brak danych </Text>
      </View>
    )
};

export default ListEmpty;