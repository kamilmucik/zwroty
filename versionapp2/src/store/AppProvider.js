import { useState, useRef } from "react";
import AppContext from "./AppContext";

function AppProvider({children}) {
  const cache = useRef([]);

  const [settingsFastQuizDepartment, setContextSettingsFastQuizDepartment] = useState({});
  const [settingsDestinationURL, setContextSettingsDestinationURL] = useState('');
  const [settingsAuthor, setContextSettingsAuthor] = useState('');
  const [settingsPin, setContextSettingsPin] = useState('');
  const [settingsWarehouse, setContextSettingsWarehouse] = useState('');
  const [settingsPageSize, setContextSettingsPageSize] = useState('');
  const [isDebugMode, setContextDebugMode] = useState(0);

  function setSettingsFastQuizDepartment(val){
    setContextSettingsFastQuizDepartment( val);
  }
  function setSettingsDestinationURL(val){
    setContextSettingsDestinationURL( val);
  }
  function setSettingsAuthor(val){
    setContextSettingsAuthor(val);
  }
  function setSettingsPin(val){
    setContextSettingsPin(val);
  }
  function setSettingsWarehouse(val){
    setContextSettingsWarehouse(val);
  }
  function setSettingsPageSize(val){
    setContextSettingsPageSize(val);
  }
  function addToCache(query, data){
    cache.current[query] = {
      data: data
    };
  }
  function existInCache(query){
    return cache.current[query];
  }
  function setDebugMode(val){
    setContextDebugMode(val);
  }

  const value = {
    cache: cache,
    settingsFastQuizDepartment: settingsFastQuizDepartment,
    settingsDestinationURL: settingsDestinationURL,
    settingsAuthor: settingsAuthor,
    settingsPin: settingsPin,
    settingsWarehouse: settingsWarehouse,
    settingsPageSize: settingsPageSize,
    isDebugMode: isDebugMode,

    setSettingsFastQuizDepartment: setSettingsFastQuizDepartment,
    setSettingsDestinationURL: setSettingsDestinationURL,
    setSettingsAuthor: setSettingsAuthor,
    setSettingsPin: setSettingsPin,
    setSettingsWarehouse: setSettingsWarehouse,
    setSettingsPageSize: setSettingsPageSize,
    existInCache: existInCache,
    addToCache: addToCache,
    setDebugMode: setDebugMode,
  }

  return <AppContext.Provider value={value}>
      {children}
    </AppContext.Provider>
}
export default AppProvider;
