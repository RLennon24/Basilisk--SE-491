// Install React and ReactDOM using NPM:
// npm install react react-dom

// index.js
import React from 'react';
import ReactDOM from 'react-dom';
import './main/components/basilisk-ui-mockup/styles.css'; // Import CSS for styling
import BasiliskDashboard from './main/components/basilisk-ui-mockup/basilisk-dashboard';


// Render the BasiliskDashboard component to the root element in the HTML document
ReactDOM.render(<BasiliskDashboard />, document.getElementById('root'));
