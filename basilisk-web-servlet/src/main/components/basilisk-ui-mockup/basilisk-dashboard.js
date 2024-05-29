// src/main/components/basilisk-ui-mockup/basilisk-dashboard.js

// initially made for bas-5 by maheen khan: create ui mock-up 

import React, { useState } from 'react';
import InsertRequestLogic from './InsertRequestLogic.js';
import DynamicForm from './DynamicForm.js';

// Define a component for the Basilisk Dashboard
function BasiliskDashboard() {
    const [activeSection, setActiveSection] = useState('dashboard-section');

    const handleNavClick = (sectionId) => {
        setActiveSection(sectionId);
    }
    return (
        <div>
            <header>
                <h1>Basilisk Dashboard</h1>
            </header>
            <nav>
                <ul>
                    <li><a href="#" onClick={() => handleNavClick('dashboard-section')}>Dashboard</a></li>
                    <li><a href="#" onClick={() => handleNavClick('files-section')}>Files</a></li>
                    <li><a href="#" onClick={() => handleNavClick('encryption-section')}>Encryption</a></li>
                    <li><a href="#" onClick={() => handleNavClick('settings-section')}>Settings</a></li>
                    <li><a href="#" onClick={() => handleNavClick('data-collection-section')}>Data Collection</a></li>
                </ul>
            </nav>
            <main>
                {/* Content for each section */}
                <section id="dashboard-section" className={activeSection === 'dashboard-section' ? 'active' : 'hidden'}>
                    <h2>Dashboard</h2>
                    {/* Dashboard content goes here */ }
                </section>
                <section id="files-section" className={activeSection === 'files-section' ? 'active' : 'hidden'}>
                    <h2>Files</h2>
                    {/* Files content goes here */}
                </section>
                <section id="encryption-section" className={activeSection === 'encryption-section' ? 'active' : 'hidden'}>
                    <h2>Encryption</h2>
                    {/* Insert the InsertRequestLogic component here */}
                    <InsertRequestLogic />
                </section>
                <section id="settings-section" className={activeSection === 'settings-section' ? 'active' : 'hidden'}>
                    <h2>Settings</h2>
                    {/* Insert the DynamicForm component here */}
                    <DynamicForm />
                </section>
                <section id="data-collection-section" className={activeSection === 'data-collection-section' ? 'active' : 'hidden'}>
                    <h2>My Data Collection</h2>
                    <div className="data-collection">
                        {/* Render existing data items */}
                        <div className="data-item">
                            <h3>Facebook</h3>
                            <p>••••••••••••••••••••</p>
                            <div className="tags">
                                <span>#Passwords</span>
                                <span>#Socials</span>
                                <button>Add tags</button>
                            </div>
                            <div className="roles">
                                <button>Viewer</button>
                                <button>Add roles</button>
                            </div>
                        </div>
                        <div className="data-item">
                            <h3>Chase Bank</h3>
                            <p>AFMqTSTjxAbrqhv...</p>
                            <div className="tags">
                                <span>#Passwords</span>
                                <span>#Finance</span>
                                <button>Add tags</button>
                            </div>
                            <div className="roles">
                                <button>Editor</button>
                                <button>Add roles</button>
                            </div>
                        </div>
                        <div className="data-item">
                            <h3>Gmail</h3>
                            <p>••••••••••••••••••••</p>
                            <div className="tags">
                                <span>#Passwords</span>
                                <span>#Socials</span>
                                <button>Add tags</button>
                            </div>
                            <div className="roles">
                                <button>Viewer</button>
                                <button>Add roles</button>
                            </div>
                        </div>
                    </div>
                    {/* Insert the InsertRequestLogic and DynamicForm components here */}
                    <div className="add-new-section">
                        <h3>Add new</h3>
                        <InsertRequestLogic />
                        <DynamicForm />
                    </div>
                </section>
            </main>
            <footer>
                <p>&copy; 2024 Basilisk</p>
            </footer>
        </div>
    );
}

export default BasiliskDashboard;
