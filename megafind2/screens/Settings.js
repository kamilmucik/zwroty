import React, { useState, useEffect } from 'react';
import { StyleSheet,Text, View, TextInput,SafeAreaView, ScrollView,TouchableOpacity,ActivityIndicator } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';


function Settings({ navigation }) {

  const [loading, setLoading] = useState(false);
  const [sourceUrl, setSourceUrl] = useState('http://zwroty.e-strix.pl');
  const [debugInfo, setDebugInfo] = useState('');


  useEffect(() => getData());
  const getData = () => {
    setLoading(true);
    AsyncStorage.getItem('@storage_Key').then((value) => {
      if (value) {
        setSourceUrl(value);
      }else{
        setSourceUrl('http://zwroty.e-strix.pl');
      }
    }).done();

    setLoading(false);
  };

  const saveSettings = typ => {
    setLoading(true);

    AsyncStorage.setItem('@storage_Key', sourceUrl)

    setDebugInfo("Zapisałem zmiany");
    setLoading(false);
  }

  return (
    <SafeAreaView style={styles.container}>
      <ScrollView style={styles.scrollView}>
      <View style={{flex: 1}}>
        <View style={styles.sectionStyle}>
          <TextInput
                style={styles.input}
                placeholder='Domyślny url serwera'
                value={sourceUrl}
                onChangeText={(text) => setSourceUrl(text)}
                />
        </View>
        <TouchableOpacity
            activeOpacity={0.9}
            onPress={() => saveSettings()}
            style={styles.loadMoreBtn}>
            <Text style={styles.btnText}>Zapisz</Text>
            {loading ? (
              <ActivityIndicator
                color="white"
                style={{marginLeft: 8}} />
            ) : null}
          </TouchableOpacity>
          <Text>
            {debugInfo}
          </Text>
        </View>
      </ScrollView>
      </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  scrollView: {
    flex: 1,
    marginHorizontal: 8,
  },
  sectionStyle: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderWidth: 0.5,
    borderColor: '#000',
    height: 40,
    borderRadius: 5,
    margin: 10,
  },
  input: {
    color: 'black',
    flex: 5,
    fontSize: 14,
    textAlign: 'center',
    borderColor: 'black'
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
  error: {
    color: 'red',
    // alignSelf: 'center'
  }
});

export default Settings;
