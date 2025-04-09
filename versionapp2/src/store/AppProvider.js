import { useState, useRef } from "react";
import AppContext from "./AppContext";

function AppProvider({children}) {
  const cache = useRef([]);

  const [settingsFastQuizDepartment, setContextSettingsFastQuizDepartment] = useState({});
  const [settingsDestinationURL, setContextSettingsDestinationURL] = useState('');

  function setSettingsFastQuizDepartment(val){
    setContextSettingsFastQuizDepartment( val);
  }
  function setSettingsDestinationURL(val){
    setContextSettingsDestinationURL( val);
  }
  function addToCache(query, data){
    cache.current[query] = {
      data: data
    };
  }
  function existInCache(query){
    return cache.current[query];
  }

  const value = {
    cache: cache,
    settingsFastQuizDepartment: settingsFastQuizDepartment,
    settingsDestinationURL: settingsDestinationURL,

    setSettingsFastQuizDepartment: setSettingsFastQuizDepartment,
    setSettingsDestinationURL: setSettingsDestinationURL,
    existInCache: existInCache,
    addToCache: addToCache,
  }

  return <AppContext.Provider value={value}>
      {children}
    </AppContext.Provider>
}
export default AppProvider;