import * as React from "react";
import { Routes, Route } from "react-router-dom";

import DataLookup from "./pages/DataLookup";
import InsertForm from "./pages/InsertForm";

export default function App() {
  return (
    <div className="App">
      <header>
        <h1>Basilisk Data Storage</h1>
      </header>
      <nav>
        <ul>
          <li>
            <a href="/">Data Collection</a>
          </li>
          <li>
            <a href="/insert">Data Insert</a>
          </li>
        </ul>
      </nav>
      <div>
        <Routes>
          <Route path="/" element={<DataLookup />} />
          <Route path="insert" element={<InsertForm />} />
        </Routes>
      </div>

      <footer>
        <p>&copy; 2024 Basilisk</p>
      </footer>
    </div>
  );
}
