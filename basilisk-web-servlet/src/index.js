// Install React and ReactDOM using NPM:
// npm install react react-dom

// index.js
import { StrictMode, React } from 'react';
import ReactDOM from 'react-dom';
import './main/basilisk-ui/styles.css'; // Import CSS for styling
import { BrowserRouter } from 'react-router-dom';

import App from './main/basilisk-ui/App';

ReactDOM.render(
<StrictMode>
    <BrowserRouter>
        <App />
    </BrowserRouter>
</StrictMode>,
document.getElementById('root'));