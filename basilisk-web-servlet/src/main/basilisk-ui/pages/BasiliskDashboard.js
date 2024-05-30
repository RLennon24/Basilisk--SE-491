import React, { useState } from "react";
import DynamicForm from "../DynamicForm.js";
import DataLookup from "../DataLookup.js";

function BasiliskDashboard() {
  const [activeSection, setActiveSection] = useState("data-lookup");

  const handleNavClick = (sectionId) => {
    setActiveSection(sectionId);
  };

  return (
    <div>
      <header>
        <h1>Basilisk Dashboard</h1>
      </header>
      <nav>
        <ul>
          <li>
            <a href="#" onClick={() => handleNavClick("dashboard-section")}>
              Dashboard
            </a>
          </li>
          <li>
            <a href="#" onClick={() => handleNavClick("data-lookup")}>
              Data Collection
            </a>
          </li>
          <li>
            <a href="#" onClick={() => handleNavClick("data-insert")}>
              Data Insert
            </a>
          </li>
          <li>
            <a href="#" onClick={() => handleNavClick("encryption-section")}>
              Encryption
            </a>
          </li>
          <li>
            <a href="#" onClick={() => handleNavClick("settings-section")}>
              Settings
            </a>
          </li>
        </ul>
      </nav>
      <main>
        {activeSection === "data-lookup" && DataLookup()}
        {activeSection === "data-insert" && DynamicForm()}
      </main>
      <footer>
        <p>&copy; 2024 Basilisk</p>
      </footer>
    </div>
  );
}

export default BasiliskDashboard;
