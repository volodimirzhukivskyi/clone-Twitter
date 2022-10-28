import React from "react";
import {createRoot} from "react-dom/client";
import {Provider} from "react-redux";
import App from "@containers/AppContainer";
import store from '../src/redux/store';
import {HashRouter} from "react-router-dom";
import Auth from "./pages/Auth";
import 'normalize.css';
import Tweet from "./components/Tweet";


const reduxStore = store();
const root = createRoot(document.getElementById("root"));

root.render(
  <Provider store={reduxStore}>
  <HashRouter>
    {/*<App/>*/}
    <Tweet/>
  </HashRouter>
</Provider>);
