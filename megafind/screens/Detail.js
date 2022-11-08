import React, {Component} from 'react';
import { Text, View, Image,
  StyleSheet, SafeAreaView, ScrollView,
  TouchableOpacity, } from 'react-native';

function Detail({ route, navigation  }) {
  const { itemId, ean, artNumber,title,reason, imgFrontBase64,imgBackBase64} = route.params;

  const addImageVersion = () => {
    navigation.navigate('AddItem', {
      _ean: {ean},
      _itemId: {itemId}
    });
  };

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.scrollView}>
    <View>
        <TouchableOpacity
            activeOpacity={0.9}
            onPress={addImageVersion}
            style={styles.loadMoreBtn}>
            <Text style={styles.btnText}>Dodaj nową wersję</Text>
        </TouchableOpacity>
        <Text>EAN: {ean}</Text>      
        <Text>Nr Artykułu: {JSON.stringify(artNumber)}</Text>  
        <Text>Opis: {title}</Text>  
        <Text>Ostatnia zmiana: {reason}</Text>
        
        <Text>Przód:</Text>      
        <Image
              source={{
                uri: 'data:image/jpeg;base64,' + imgFrontBase64,
              }}
              
            style={{ width: 256, height: 256, alignItems: 'center'  }}
            /> 
        <Text>Tył:</Text>  
        <Image
            source={{
              uri: 'data:image/jpeg;base64,' + JSON.stringify(imgFrontBase64),
            }}
            style={{ width: 256, height: 256, alignItems: 'center'  }}
          />
    </View>
      </ScrollView>
     </SafeAreaView>
    );
}

const styles = StyleSheet.create({
  input: {
    color: 'black',
    fontSize: 17,
    textAlign: 'center',
    borderColor: 'black',
    borderBottomWidth: 1
  },
  buttonStyle:{
    color: 'white',
    fontSize: 17,
    textAlign: 'center',
  },
  
  loadMoreBtn: {
    margin: 20,
    padding: 14,
    backgroundColor: '#3740ff',
    borderRadius: 4,
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
  },
  btnText: {
    color: 'white',
    fontSize: 17,
    textAlign: 'center',
  },
  container: {
    flex: 1,
  },
  scrollView: {
    flex: 1,
    marginHorizontal: 8,
  },
});

export default Detail;