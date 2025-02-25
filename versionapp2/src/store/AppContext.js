import React from 'react';

const AppContext = React.createContext({
  cache: [],
  settingsFastQuizDepartment: {},
  settingsShowCorrectAnswerOnly: false,
  setSettingsFastQuizDepartment: (val) => {},
  setSettingsOnlyCorrectValue: (val) => {},
  existInCache: (val) => {},
  addToCache: (val) => {},
});

export default AppContext;