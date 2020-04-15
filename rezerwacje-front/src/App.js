import React, { Component } from 'react';
import { Router } from 'react-router-dom';
import { createBrowserHistory } from 'history';
// import { Chart } from 'react-chartjs-2';
import { ThemeProvider } from '@material-ui/styles';
// import validate from 'validate.js';

// import { chartjs } from './helpers';
import theme from './theme';
// import 'react-perfect-scrollbar/dist/css/styles.css';
import './assets/scss/index.scss';
// import validators from './common/validators';
import Routes from './Routes';

const browserHistory = createBrowserHistory();

// Chart.helpers.extend(Chart.elements.Rectangle.prototype, {
//   draw: chartjs.draw
// });

// validate.validators = {
//   ...validate.validators,
//   ...validators
// };

export default class App extends Component {
  render() {
    return (
      <ThemeProvider theme={theme}>
        <Router history={browserHistory}>
          <Routes />
        </Router>
      </ThemeProvider>
    );
  }
}


// import React from "react";
// import { BrowserRouter, Switch, Route } from "react-router-dom";

// import { ThemeProvider } from '@material-ui/styles';

// import theme from './theme';

// import "./App.css";
// import Home from "./components/Home/Home";
// import Rooms from "./components/Rooms/Rooms";

// function App() {
//   return (
//     <div>
//       <ThemeProvider theme={theme}>
//         <BrowserRouter>
//           <Switch>
//             <Route exact path="/" component={Home} />
//             <Route exact path="/rooms" component={Rooms} />
//           </Switch>
//         </BrowserRouter>
//       </ThemeProvider>
//     </div>
//   );
// }

// export default App;
