import React from 'react';

const AppContext = React.createContext({
  cache: [],
  settingsFastQuizDepartment: {},
  settingsDestinationURL: "",
  setSettingsDestinationURL: (val) => {},
  setSettingsFastQuizDepartment: (val) => {},
  existInCache: (val) => {},
  addToCache: (val) => {},
});

export default AppContext;