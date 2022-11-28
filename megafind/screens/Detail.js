import React, {Component, useState} from 'react';
import { Text, View, Image,
  StyleSheet, SafeAreaView, ScrollView,Modal,Pressable,
  TouchableOpacity, } from 'react-native';

function Detail({ route, navigation  }) {
  const { itemId, ean, artNumber,title,reason, imgFrontBase64,imgBackBase64, imgLeftBase64,imgRightBase64, imgTopBase64,imgBottomBase64} = route.params;
  const [modalVisible, setModalVisible] = useState(false);
  const [modalImage, setModalImage] = useState("");
  const [modalTitle, setModalTitle] = useState("");

  const addImageVersion = () => {
    navigation.navigate('AddItem', {
      _ean: {ean},
      _itemId: {itemId}
    });
  };

  const showSlider = typ => {
    if (typ === 1){
      setModalImage(imgFrontBase64);
      setModalTitle("Przód");
    } else if (typ === 2){
      setModalImage(imgBackBase64);
      setModalTitle("Tył");
    } else if (typ === 3){
      setModalImage(imgLeftBase64);
      setModalTitle("Lewa strona");
    } else if (typ === 4){
      setModalImage(imgRightBase64);
      setModalTitle("Prawa strona");
    } else if (typ === 5){
      setModalImage(imgTopBase64);
      setModalTitle("Góra");
    } else if (typ === 6){
      setModalImage(imgBottomBase64);
      setModalTitle("Dół");
    }
    setModalVisible(true);
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

          <Modal
          visible={modalVisible}
          animationType="slide"
          transparent={true}
          onRequestClose={() => setModalVisible(false)}
         >
          <View style={styles.centeredView}>
            <View style={styles.modalView}>
              <Text style={styles.modalText}>{modalTitle}</Text>
              
              <Pressable
              style={[styles.button, styles.buttonClose]}
              onPress={() => setModalVisible(!modalVisible)}
            >
              <Image
                    source={{
                      uri: 'data:image/jpeg;base64,' + modalImage,
                    }}
                    
                  style={{ minWidth: 250, minHeight: 250, alignItems: 'center'  }}
                  /> 
              <Text style={styles.textStyle}>Zamknij</Text>
              </Pressable>
            </View>
          </View>

         </Modal>
          
          <ScrollView horizontal={true}>
          <View >
              <TouchableOpacity onPress={() => showSlider(1)}>
                <Text>Przód:</Text>      
                <Image
                    source={{
                      uri: 'data:image/jpeg;base64,' + imgFrontBase64,
                    }}
                    
                  style={{ width: 200, height: 200, alignItems: 'center'  }}
                  /> 
              </TouchableOpacity>
            </View>
            <View style={{ marginLeft: 2}}>
              <TouchableOpacity onPress={() => showSlider(2)}>
                <Text>Tył:</Text>  
                <Image
                    source={{
                      uri: 'data:image/jpeg;base64,' + JSON.stringify(imgBackBase64),
                    }}
                    style={{ width: 200, height: 200, alignItems: 'center'  }}
                  />
              </TouchableOpacity>
            </View>
            <View style={{ marginLeft: 2}}>
              <TouchableOpacity onPress={() => showSlider(3)}>
                <Text>Lewa stron:</Text>  
                <Image
                    source={{
                      uri: 'data:image/jpeg;base64,' + JSON.stringify(imgLeftBase64),
                    }}
                    style={{ width: 200, height: 200, alignItems: 'center'  }}
                  />
                </TouchableOpacity>
            </View>
            <View style={{ marginLeft: 2}}>
              <TouchableOpacity onPress={() => showSlider(4)}>
                <Text>Prawa stron:</Text>  
                <Image
                    source={{
                      uri: 'data:image/jpeg;base64,' + JSON.stringify(imgRightBase64),
                    }}
                    style={{ width: 200, height: 200, alignItems: 'center'  }}
                  />
                </TouchableOpacity>
            </View>
            <View style={{ marginLeft: 2}}>
              <TouchableOpacity onPress={() => showSlider(5)}>
                <Text>Góra:</Text>  
                <Image
                    source={{
                      uri: 'data:image/jpeg;base64,' + JSON.stringify(imgTopBase64),
                    }}
                    style={{ width: 200, height: 200, alignItems: 'center'  }}
                  />
                </TouchableOpacity>
            </View>
            <View style={{ marginLeft: 2}}>
              <TouchableOpacity onPress={() => showSlider(6)}>
                <Text>Dół:</Text>  
                <Image
                    source={{
                      uri: 'data:image/jpeg;base64,' + JSON.stringify(imgBottomBase64),
                    }}
                    style={{ width: 200, height: 200, alignItems: 'center'  }}
                  />
                </TouchableOpacity>
            </View>
          </ScrollView>
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
    margin: 10,
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
  centeredView: {
    flex: 1,
    backgroundColor: 'rgba(52, 52, 52, 0.8)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  modalView: {
    alignItems: 'center',
              backgroundColor: 'white',
              marginVertical: 60,
              width: '90%',
              borderWidth: 1,
              borderColor: '#fff',
              borderRadius: 7,
              elevation: 10,
  },
});

export default Detail;