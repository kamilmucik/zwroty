import { StyleSheet, View, Text, Pressable, FlatList } from 'react-native';
import React, { useState, useEffect, useContext } from "react";
import { useNavigation } from '@react-navigation/native';
import AppContext from "../store/AppContext";

const ShipmentsScreen = () => {
  const navigation = useNavigation();
  const appCtx = useContext(AppContext);
  const [isLoading, setLoading] = useState(false);
  const [shipments, setShipments] = useState([]);

  getShipments = () => {
    fetch(''+appCtx.settingsURLValue+':'+appCtx.settingsPortValue +'/shipment/list')
      .then((response) => response.json())
      // .then((response) => response.json())
      .then(responseData => {console.log(responseData); return responseData;})
      .then((data) => {
        setShipments(data.shipmentsDto);
      })
      .catch((error) => console.error(error))
      .finally(() => setLoading(false));
  }

  useEffect(() => {
    setLoading(true);
    getShipments();
  }, []);

  const renderListItems = ({ item }) => {
    return (
      <Pressable
        onPress={() =>
          navigation.navigate('Scan', {
            retNumber: item.number,
          })
        }
      >
        <Text style={{ fontSize: 16, paddingHorizontal: 12, paddingVertical: 4 }} >
          {item.number}
        </Text>
        <Text style={{ fontSize: 10, paddingHorizontal: 20, paddingBottom: 10 }} >
          {item.productCounter}
        </Text>
        <View
          style={{
            borderWidth: StyleSheet.hairlineWidth,
            borderColor: '#ccc',
          }}
        />
      </Pressable>
    );
  };

  return (
    <View style={{ flex: 1, paddingTop: 10 }}>
      {isLoading ? <Text>Loading...</Text> :
        (
          <FlatList data={shipments} keyExtractor={({ id }) => id.toString()} renderItem={renderListItems} />
        )}
    </View>
  );
};

export default ShipmentsScreen;