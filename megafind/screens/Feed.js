import React, {useState, useEffect, useRef} from 'react';
import  QRCodeScanner from 'react-native-qrcode-scanner';
import {
  SafeAreaView,
  View,
  Text,
  TouchableOpacity,
  StyleSheet,
  FlatList,
  ActivityIndicator,
  TextInput,
  Image
} from 'react-native';
import PackageJson from '../package'    // where package is the package.json file
import AsyncStorage from '@react-native-async-storage/async-storage';


function Feed({ navigation }) {

  const defaultList = [];

  const scanner = useRef(null);
  const [debugInfo, setDebugInfo] = useState('');
  const [mainEndpoint, setMainEndpoint] = useState('');
  const [loading, setLoading] = useState(true);
  const [dataSource, setDataSource] = useState(defaultList);
  const [searchEan, setSearchEan] = useState('');
  const [scan, setScan] = useState(false);

  useEffect(() => getData(), defaultList);

  

  const getData = () => {
    
    setLoading(true);
    
    AsyncStorage.getItem('@storage_Key').then((value) => {
      setMainEndpoint(value);
    }).done();

    fetch(mainEndpoint+'/productimageversion/findbyean?ean=' + searchEan)
      //Sending the currect offset with get request
      .then((response) => response.json())
      .then((responseJson) => {
        //Successful response
        setDataSource([...responseJson.results]); 
        // setDataSource([...dataSource, ...responseJson.results]);
        setLoading(false);
        setSearchEan('');
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const renderFooter = () => {
    return (
      //Footer View with Load More button
      <View style={styles.footer}>
        <TouchableOpacity
          activeOpacity={0.9}
          onPress={getData}
          //On Click of button load more data
          style={styles.loadMoreBtn}>
          <Text style={styles.btnText}>Load More</Text>
          {loading ? (
            <ActivityIndicator
              color="white"
              style={{marginLeft: 8}} />
          ) : null}
        </TouchableOpacity>
      </View>
    );
  };

  const ItemView = ({item}) => {
    return (
      // Flat List Item
      <View >
        <TouchableOpacity
          activeOpacity={0.9}
          onPress={() => getItem(item)}>
          <View style={styles.containerItem}>
            <View style={styles.squareItemImage} >
                <Image
                source={{
                  uri: 'data:image/jpeg;base64,' + item.imgFrontBase64,
                }}
                style={{ flex:1 }}
              /> 
            </View>
            <View style={styles.squareItem} >
              <Text style={styles.itemStyle} >
                {item.ean.toUpperCase()}
              </Text>
              <Text style={styles.itemStyle2}>
                {item.title}
              </Text>
              <Text style={styles.itemStyle2}>
                {item.reason}
              </Text>
            </View>
          </View>
        </TouchableOpacity>
      </View>
    );
  };

  const ItemSeparatorView = () => {
    return (
      // Flat List Item Separator
      <View
        style={{
          height: 1,
          width: '100%',
          backgroundColor: '#C8C8C8',
        }}
      />
    );
  };

  const getItem = (item) => {
    //Function for click on an item
    // alert('Id : ' + item.id + ' Title : ' + item._ean);
    navigation.navigate('Detail', {
      itemId: item.id,
      ean: item.ean,
      title: item.title,
      artNumber: item.artNumber,
      imgFrontBase64: item.imgFrontBase64,
      imgBackBase64: item.imgBackBase64,
      reason: item.reason
    });

  };

  const launchScannerCamera = () => {
    setScan(true);
  }
  
  const onSuccess = e => {
    // setInputEAN(e.data);
    setScan(false);
  }

  return !scan ? (
    <SafeAreaView style={{flex: 1}}>
      <View style={styles.container}>
      <Text style={styles.versionText}>v: {PackageJson.version} </Text>
      <Text style={{ alignItems: 'center',color: 'gray',fontSize: 10 }}>
        {debugInfo}
      </Text>
      <View style={styles.sectionStyle}>
        <TextInput 
          placeholder='EAN'
          keyboardType = 'numeric'
          style={styles.inputField}
          value={searchEan}
          onChangeText={(text) => setSearchEan(text)}
        />
        <TouchableOpacity onPress={launchScannerCamera} style={styles.button2}>
          <Image source={require('../assets/barcode.png')}
            style={{ width: 60, height: 38 }}
          />
        </TouchableOpacity>
      </View>
      <View >
        <TouchableOpacity
          activeOpacity={0.9}
          onPress={getData}
          style={styles.loadMoreBtn}>
          <Text style={styles.btnText}>Wyszukaj</Text>
          {loading ? (
            <ActivityIndicator
              color="white" />
          ) : null}
        </TouchableOpacity>
      </View>
        <FlatList
          data={dataSource}
          keyExtractor={(item, index) => index.toString()}
          ItemSeparatorComponent={ItemSeparatorView}
          enableEmptySections={true}
          renderItem={ItemView}
          // ListFooterComponent={renderFooter}
        />
      </View>
    </SafeAreaView>
  ): (
    <SafeAreaView style={{flex: 1}}>
      <View style={{flex: 1}}>
        <QRCodeScanner
          onRead={onSuccess}
          ref={scanner}
          reactivate={true}
          showMarker={true}
          bottomContent={
            <View style={{flexDirection: 'row'}}>
              <TouchableOpacity style={styles.sectionStyle} onPress={() => scanner.current.reactivate()}>
                <Text style={styles.buttonText2}>OK. Got it!</Text>
              </TouchableOpacity>
              <TouchableOpacity style={styles.sectionStyle} onPress={() => setScan(false)}>
                <Text style={styles.buttonText2}>Stop</Text>
              </TouchableOpacity>
              </View>
          }
        />
      </View>
    </SafeAreaView>
  ) ;
}

const styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    flex: 1,
  },
  footer: {
    padding: 10,
    justifyContent: 'center',
    alignItems: 'center',
    flexDirection: 'row',
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
  inputField: {
    color: 'black',
    flex: 5,
    fontSize: 14,
    textAlign: 'center',
    borderColor: 'black'
  },
  itemStyle: {
    color: 'black',
    fontSize: 17,
    padding: 1,
  },
  itemStyle2: {
    color: 'black',
    fontSize: 10,
    padding: 1,
  },
  versionText: {
    color: 'gray',
    fontSize: 10,
    textAlign: 'right',
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

  containerItem: {
    // backgroundColor: "#7CA1B4",
    flex: 1,
    // alignItems: "center", // ignore this - we'll come back to it
    justifyContent: "flex-start", // ignore this - we'll come back to it
    flexDirection: "row",
  },
  squareItemImage: {
    // backgroundColor: "#7cb48f",
    flex: 0.2,
    height: 100,
    marginRight: 4,
  },
  squareItem: {
    // backgroundColor: "#7cb48f",
    height: 100,
    flex: 0.8,
    // margin: 4,
  },
});


export default Feed;