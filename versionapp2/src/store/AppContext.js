import React from 'react';

const AppContext = React.createContext({
  cache: [],
  settingsFastQuizDepartment: {},
  settingsDestinationURL: "",
  isDebugMode: false,
  setSettingsDestinationURL: (val) => {},
  setSettingsFastQuizDepartment: (val) => {},
  existInCache: (val) => {},
  addToCache: (val) => {},
  setDebugMode: (val) => {},
});

export default AppContext;