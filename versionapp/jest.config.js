module.exports = {
  preset: 'react-native',
  transformignorepatterns : ["/node_modules/@react-native-async-storage/async-storage/(?!(lib))"],
  setupFiles: ["./setupTests.js"],
};
