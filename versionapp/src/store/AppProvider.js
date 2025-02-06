import { useState, useRef } from "react";
import AppContext from "./AppContext";

function AppProvider({children}) {
  const cache = useRef([]);
  const [settingsFastQuizDepartment, setContextSettingsFastQuizDepartment] = useState({});
  const [settingsShowCorrectAnswerOnly, setContextSettingsOnlyCorrectValue] = useState(false);

  function setSettingsFastQuizDepartment(val){
    setContextSettingsFastQuizDepartment( val);
  }
  function setSettingsOnlyCorrectValue(val){
    setContextSettingsOnlyCorrectValue( val);
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
    settingsShowCorrectAnswerOnly: settingsShowCorrectAnswerOnly,

    setSettingsFastQuizDepartment: setSettingsFastQuizDepartment,
    setSettingsOnlyCorrectValue: setSettingsOnlyCorrectValue,
    existInCache: existInCache,
    addToCache: addToCache,
  }

  return <AppContext.Provider value={value}>
      {children}
    </AppContext.Provider>
}
export default AppProvider;